package uet.soa.pastebin.domain.model.analytics;

import lombok.Getter;
import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDateTime;

public class AnalyticsRecord {
    private final Paste paste;
    private final LocalDateTime recordDate;
    private int viewCount;

    public AnalyticsRecord(Paste paste, LocalDateTime recordDate, int viewCount) {
        this.paste = paste;
        this.recordDate = recordDate;
        this.viewCount = viewCount;
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public AnalyticsMemento createSnapshot() {
        return new AnalyticsMemento(paste, recordDate, viewCount);
    }

    /**
     * @param paste Accessor methods for mapping
     */
    public record AnalyticsMemento(Paste paste, LocalDateTime recordDate,
                                   int viewCount) {

        public AnalyticsRecord restore() {
            return new AnalyticsRecord(paste, recordDate, viewCount);
        }
    }
}
