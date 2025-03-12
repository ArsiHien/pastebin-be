package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.paste.URL;
import uet.soa.pastebin.domain.model.policy.BurnAfterReadExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class PasteRepositoryImpl implements PasteRepository {
    private final PasteJpaRepository pasteJpaRepository;
    private final ExpirationPolicyJpaRepository expirationPolicyJpaRepository;

    @Override
    public void save(Paste paste) {
        Paste.PasteMemento memento = paste.createSnapshot();
        JpaExpirationPolicy policy = saveExpirationPolicy(memento);
        JpaPaste jpaPaste = toJpaPaste(memento, policy);
        pasteJpaRepository.save(jpaPaste);
    }

    @Override
    public Optional<Paste> findById(String id) {
        return pasteJpaRepository.findById(id)
                .map(this::toDomainPaste);
    }

    @Override
    public Optional<Paste> findByUrl(String url) {
        return pasteJpaRepository.findByUrl(url)
                .map(this::toDomainPaste);
    }

    @Override
    public List<Paste> findExpiredPastes() {
        return findAllExpired(); // Delegate for consistency
    }

    @Override
    public Optional<Paste> findByUrl(URL url) {
        return findByUrl(url.toString());
    }

    @Override
    public List<Paste> findAllExpired() {
        return pasteJpaRepository.findExpiredPastes(LocalDateTime.now())
                .stream()
                .map(this::toDomainPaste)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Paste paste) {
        Paste.PasteMemento memento = paste.createSnapshot();
        JpaExpirationPolicy policy = saveExpirationPolicy(memento);
        JpaPaste jpaPaste = toJpaPaste(memento, policy);
        pasteJpaRepository.save(jpaPaste);
    }

    @Override
    public void delete(Paste paste) {
        Paste.PasteMemento memento = paste.createSnapshot();
        pasteJpaRepository.findById(memento.getId())
                .ifPresent(pasteJpaRepository::delete);
    }

    private JpaExpirationPolicy saveExpirationPolicy(Paste.PasteMemento memento) {
        JpaExpirationPolicy policy = JpaExpirationPolicy.builder()
                .policyType(JpaExpirationPolicy.PolicyType.valueOf(memento.getExpirationPolicy().type().name()))
                .duration(memento.getExpirationPolicy().durationAsString())
                .expirationTime(calculateExpirationTime(memento))
                .isRead(memento.getExpirationPolicy() instanceof BurnAfterReadExpirationPolicy &&
                        ((BurnAfterReadExpirationPolicy) memento.getExpirationPolicy()).isRead())
                .build();
        return expirationPolicyJpaRepository.save(policy);
    }

    private JpaPaste toJpaPaste(Paste.PasteMemento memento, JpaExpirationPolicy policy) {
        return JpaPaste.builder()
                .id(memento.getId())
                .content(memento.getContent().reveal())
                .url(memento.getUrl().toString())
                .createdAt(memento.getCreatedAt())
                .viewCount(memento.getViewCount())
                .expirationPolicy(policy)
                .build();
    }

    private Paste toDomainPaste(JpaPaste jpaPaste) {
        Paste.PasteMemento memento = new Paste.PasteMemento(
                Content.of(jpaPaste.getContent()),
                jpaPaste.getCreatedAt(),
                ExpirationPolicyFactory.create(jpaPaste.getExpirationPolicy(), jpaPaste.getCreatedAt()),
                jpaPaste.getId(),
                URL.of(jpaPaste.getUrl()),
                jpaPaste.getViewCount()
        );
        return memento.restore();
    }

    private LocalDateTime calculateExpirationTime(Paste.PasteMemento memento) {
        String duration = memento.getExpirationPolicy().durationAsString();
        if (duration == null || memento.getExpirationPolicy().type() == ExpirationPolicy.ExpirationPolicyType.NEVER ||
                memento.getExpirationPolicy().type() == ExpirationPolicy.ExpirationPolicyType.BURN_AFTER_READ) {
            return null;
        }
        ExpirationPolicy.ExpirationDuration expDuration = ExpirationPolicy.ExpirationDuration.fromString(duration);
        return memento.getCreatedAt().plus(expDuration.toDuration());
    }
}