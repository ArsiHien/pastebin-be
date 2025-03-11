package uet.soa.pastebin.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Paste {
    private String id;
    private String content;
    private LocalDate createdAt;
    private ExpirationPolicy expirationPolicy;
    private String url;
    private int viewCount;

    public Paste(String content, LocalDate createdAt, ExpirationPolicy expirationPolicy, String id, String url, int viewCount) {
        this.content = content;
        this.createdAt = createdAt;
        this.expirationPolicy = expirationPolicy;
        this.id = id;
        this.url = url;
        this.viewCount = viewCount;
    }

    public void updateViewCount() {
        viewCount++;
    }

    public boolean isExpired() {
        return expirationPolicy != null && expirationPolicy.isExpired();
    }

    public void markAsRead() {
        expirationPolicy.markAsRead();
    }
}
