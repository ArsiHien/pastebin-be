package uet.soa.pastebin.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Paste {
    private String id;
    private String content;
    private LocalDate createdAt;
    private ExpirationPolicy expirationPolicy;
    private URL url;
    private int viewCount;

    private Paste(String id, String content, LocalDate createdAt, ExpirationPolicy expirationPolicy, URL url,
                  int viewCount) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.expirationPolicy = expirationPolicy;
        this.url = url;
        this.viewCount = viewCount;
    }

    public static Paste create(String content, LocalDate createdAt, ExpirationPolicy expirationPolicy) {
        String id = UUID.randomUUID().toString();
        URL generatedUrl = URL.generate();
        return new Paste(id, content, createdAt, expirationPolicy, generatedUrl, 0);
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public boolean isExpired() {
        return expirationPolicy != null && expirationPolicy.isExpired();
    }

    public void markAsRead() {
        expirationPolicy.markAsRead();
    }
}
