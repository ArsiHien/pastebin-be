package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.analytics.AnalyticsRecord;
import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDate;
import java.util.Optional;

public interface AnalyticsRepository {
    Optional<AnalyticsRecord> findByPasteIdAndDate(Paste paste, LocalDate date);

    void save(AnalyticsRecord record);

    void update(AnalyticsRecord record);
}
