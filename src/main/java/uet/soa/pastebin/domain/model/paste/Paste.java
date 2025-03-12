package uet.soa.pastebin.domain.model.paste;

import lombok.Getter;
import uet.soa.pastebin.domain.model.policy.BurnAfterReadExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;

import java.time.LocalDateTime;
import java.util.UUID;

public class Paste {
    private String id;
    private Content content;
    private LocalDateTime createdAt;
    private ExpirationPolicy expirationPolicy;
    private URL url;
    private int viewCount;

    private Paste(String id, Content content, LocalDateTime createdAt,
                  ExpirationPolicy expirationPolicy, URL url,
                  int viewCount) {
        this.id = id;
        this.content = content;
        this.createdAt = createdAt;
        this.expirationPolicy = expirationPolicy;
        this.url = url;
        this.viewCount = viewCount;
    }

    public static Paste create(Content content, LocalDateTime createdAt,
                               ExpirationPolicy expirationPolicy) {
        String id = UUID.randomUUID().toString();
        URL generatedUrl = URL.generate();
        return new Paste(id, content, createdAt, expirationPolicy, generatedUrl, 0);
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public boolean isExpired() {
        return expirationPolicy != null && expirationPolicy.isExpired(createdAt);
    }

    public void onAccess() {
        incrementViewCount();
        if (expirationPolicy instanceof BurnAfterReadExpirationPolicy policy) {
            policy.markAsRead();
        }
    }

    public URL publishUrl() {
        return url;
    }

    public String provideContent() {
        return content.reveal();
    }

    public long totalViews() {
        return viewCount;
    }

    @Getter
    public static class PasteMemento {
        private String id;
        private Content content;
        private LocalDateTime createdAt;
        private ExpirationPolicy expirationPolicy;
        private URL url;
        private int viewCount;

        public PasteMemento(Content content, LocalDateTime createdAt,
                            ExpirationPolicy expirationPolicy, String id, URL url, int viewCount) {
            this.content = content;
            this.createdAt = createdAt;
            this.expirationPolicy = expirationPolicy;
            this.id = id;
            this.url = url;
            this.viewCount = viewCount;
        }

        public Paste restore() {
            return new Paste(id, content, createdAt, expirationPolicy, url, viewCount);
        }
    }

    public PasteMemento createSnapshot() {
        return new PasteMemento(this.content, this.createdAt,
                this.expirationPolicy, this.id, this.url, this.viewCount);
    }
}
