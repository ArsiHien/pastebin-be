package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.mapper.ExpirationPolicyMapper;
import uet.soa.pastebin.infrastructure.mapper.PasteMapper;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class PasteRepositoryImpl implements PasteRepository {
    private final PasteJpaRepository jpaRepository;
    private final ExpirationPolicyJpaRepository expirationPolicyJpaRepository;

    @Override
    public void save(Paste paste) {
        JpaPaste jpaPaste = PasteMapper.toEntity(paste.createSnapshot(),
                ExpirationPolicyMapper.toEntity(paste.createSnapshot().getExpirationPolicy(),
                        expirationPolicyJpaRepository));
        jpaRepository.save(jpaPaste);
    }

    @Override
    public Optional<Paste> findByUrl(String url) {
        return jpaRepository.findByUrl(url)
                .map(PasteMapper::toDomain);
    }

    @Override
    public List<Paste> findTimedPastes() {
        return jpaRepository.findTimedPastes()
                .stream()
                .map(PasteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Paste paste) {
        JpaPaste jpaPaste = PasteMapper.toEntity(paste.createSnapshot(),
                ExpirationPolicyMapper.toEntity(paste.createSnapshot().getExpirationPolicy(),
                        expirationPolicyJpaRepository));
        jpaRepository.save(jpaPaste);
    }

    @Override
    public void delete(Paste paste) {
        jpaRepository.deleteById(paste.publishUrl().toString());
    }
}