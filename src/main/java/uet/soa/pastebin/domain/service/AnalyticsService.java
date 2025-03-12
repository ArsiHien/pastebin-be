package uet.soa.pastebin.domain.service;

import org.springframework.stereotype.Service;
import uet.soa.pastebin.domain.model.analytics.AnalyticsRecord;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.AnalyticsRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class AnalyticsService {
    private final AnalyticsRepository analyticsRepository;

    public AnalyticsService(AnalyticsRepository analyticsRepository) {
        this.analyticsRepository = analyticsRepository;
    }

    public void recordView(Paste paste) {
        LocalDateTime today = LocalDateTime.now();
        Optional<AnalyticsRecord> recordOpt = analyticsRepository.findByPasteIdAndDate(paste, today);
        if (recordOpt.isPresent()) {
            AnalyticsRecord record = recordOpt.get();
            record.incrementViewCount();
            analyticsRepository.update(record);
        } else {
            AnalyticsRecord record = new AnalyticsRecord(paste, today, 1);
            analyticsRepository.save(record);
        }
    }
}
