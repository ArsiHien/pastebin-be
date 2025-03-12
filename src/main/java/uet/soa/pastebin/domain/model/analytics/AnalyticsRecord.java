package uet.soa.pastebin.domain.model.analytics;

import lombok.Getter;
import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDate;

public class AnalyticsRecord {
    private final Paste paste;
    private final LocalDate recordDate;
    @Getter
    private int viewCount;

    public AnalyticsRecord(Paste paste, LocalDate recordDate, int viewCount) {
        this.paste = paste;
        this.recordDate = recordDate;
        this.viewCount = viewCount;
    }

    public void incrementViewCount() {
        viewCount++;
    }

}
