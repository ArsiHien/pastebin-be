package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uet.soa.pastebin.domain.model.analytics.AnalyticsRecord;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.AnalyticsRepository;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPasteView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class AnalyticsRepositoryImpl implements AnalyticsRepository {
    private final PasteViewJpaRepository pasteViewJpaRepository;
    private final PasteRepository pasteRepository;
    private final PasteJpaRepository pasteJpaRepository;

    @Override
    public Optional<AnalyticsRecord> findByPasteIdAndDate(Paste paste, LocalDateTime date) {
        return findByPasteIdAndDate(paste.createSnapshot().getId(), date);
    }

    @Override
    public Optional<AnalyticsRecord> findByPasteIdAndDate(String pasteId, LocalDateTime dateTime) {
        return pasteViewJpaRepository.findByPasteIdAndViewTime(pasteId, dateTime)
                .map(this::toDomainAnalyticsRecord);
    }

    @Override
    public void save(AnalyticsRecord record) {
        JpaPasteView jpaView = toJpaPasteView(record);
        pasteViewJpaRepository.save(jpaView);
    }

    @Override
    public void update(AnalyticsRecord record) {
        JpaPasteView jpaView = toJpaPasteView(record);
        pasteViewJpaRepository.save(jpaView);
    }

    @Override
    public List<AnalyticsRecord> findAllByPasteId(String pasteId) {
        return pasteViewJpaRepository.findAllByPasteId(pasteId)
                .stream()
                .map(this::toDomainAnalyticsRecord)
                .collect(Collectors.toList());
    }

    private JpaPasteView toJpaPasteView(AnalyticsRecord record) {
        AnalyticsRecord.AnalyticsMemento memento = record.createSnapshot();
        JpaPaste jpaPaste =
                pasteJpaRepository.findById(memento.paste().createSnapshot().getId())
                        .orElseThrow(() -> new IllegalStateException("Paste not " +
                                "found: " + memento.paste().createSnapshot().getId()));
        return JpaPasteView.builder()
                .viewTime(memento.recordDate())
                .paste(jpaPaste)
                .build();
    }

    private AnalyticsRecord toDomainAnalyticsRecord(JpaPasteView jpaView) {
        Paste paste = pasteRepository.findById(jpaView.getPaste().getId())
                .orElseThrow(() -> new IllegalStateException("Paste not found: " + jpaView.getPaste().getId()));
        return new AnalyticsRecord(paste, jpaView.getViewTime(), 1);
    }
}