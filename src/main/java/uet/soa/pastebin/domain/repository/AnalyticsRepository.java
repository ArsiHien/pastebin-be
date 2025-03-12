package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.analytics.AnalyticsRecord;
import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AnalyticsRepository {
    Optional<AnalyticsRecord> findByPasteIdAndDate(Paste paste,
                                                   LocalDateTime date);

    Optional<AnalyticsRecord> findByPasteIdAndDate(String pasteId,
                                                   LocalDateTime dateTime);

    void save(AnalyticsRecord record);

    void update(AnalyticsRecord record);

    List<AnalyticsRecord> findAllByPasteId(String pasteId);

}
