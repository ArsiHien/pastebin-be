package uet.soa.pastebin.domain.model;

import lombok.Getter;

import java.time.LocalDate;

public class AnalyticsRecord {
    private String pasteId;
    private LocalDate date;
    @Getter
    private int viewCount;

    public void incrementViewCount() {
        viewCount++;
    }

}
