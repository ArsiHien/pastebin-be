package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.AnalyticsRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface AnalyticsRepository {
    Optional<AnalyticsRecord> findByPasteIdAndDate(String pasteId, LocalDate date);

    void save(AnalyticsRecord record);

    void update(AnalyticsRecord record);
}
