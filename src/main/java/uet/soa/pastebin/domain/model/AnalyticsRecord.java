package uet.soa.pastebin.domain.model;

import lombok.Getter;

import java.time.LocalDate;

public class AnalyticsRecord {
    private String pasteId;
    private LocalDate recordDate;
    @Getter
    private int viewCount;

    public AnalyticsRecord(String pasteId, LocalDate recordDate, int viewCount) {
        this.pasteId = pasteId;
        this.recordDate = recordDate;
        this.viewCount = viewCount;
    }

    public void incrementViewCount() {
        viewCount++;
    }

}
