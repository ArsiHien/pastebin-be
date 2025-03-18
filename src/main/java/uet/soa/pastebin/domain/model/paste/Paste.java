package uet.soa.pastebin.domain.model.paste;

import lombok.Getter;
import lombok.NoArgsConstructor;
import uet.soa.pastebin.domain.model.policy.BurnAfterReadExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.persistence.repository.PasteRepositoryImpl;

import java.time.Duration;
import java.time.LocalDateTime;

@NoArgsConstructor
public class Paste {
    private URL url;
    private Content content;
    private LocalDateTime createdAt;
    private ExpirationPolicy expirationPolicy;
    private int viewCount;

    private Paste(Content content, LocalDateTime createdAt,
                  ExpirationPolicy expirationPolicy, URL url,
                  int viewCount) {
        this.content = content;
        this.createdAt = createdAt;
        this.expirationPolicy = expirationPolicy;
        this.url = url;
        this.viewCount = viewCount;
    }

    public static Paste create(Content content, LocalDateTime createdAt,
                               ExpirationPolicy expirationPolicy) {
        URL generatedUrl = URL.generate();
        return new Paste(content, createdAt, expirationPolicy, generatedUrl, 0);
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

    public String calculateTimeUntilExpiration() {
        if (expirationPolicy.type() == ExpirationPolicy.ExpirationPolicyType.TIMED) {
            ExpirationPolicy.ExpirationDuration duration =
                    ExpirationPolicy.ExpirationDuration.fromString(expirationPolicy.durationAsString());
            LocalDateTime expirationTime = createdAt.plus(duration.toDuration());
            Duration remaining = Duration.between(LocalDateTime.now(), expirationTime);

            if (remaining.isNegative()) {
                return "Expired";
            }

            return formatDuration(remaining);
        }

        return expirationPolicy.type().toString();
    }

    private String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;
        long seconds = duration.getSeconds() % 60;

        StringBuilder result = new StringBuilder();
        if (days > 0) result.append(days).append("d ");
        if (hours > 0) result.append(hours).append("h ");
        if (minutes > 0) result.append(minutes).append("m ");
        if (seconds > 0) result.append(seconds).append("s");

        return result.toString().trim();
    }


    @Getter
    public static class PasteMemento {
        private Content content;
        private LocalDateTime createdAt;
        private ExpirationPolicy expirationPolicy;
        private URL url;
        private int viewCount;

        public PasteMemento(Content content, LocalDateTime createdAt,
                            ExpirationPolicy expirationPolicy, URL url, int viewCount) {
            this.content = content;
            this.createdAt = createdAt;
            this.expirationPolicy = expirationPolicy;
            this.url = url;
            this.viewCount = viewCount;
        }

        public Paste restore() {
            return new Paste(content, createdAt, expirationPolicy, url, viewCount);
        }
    }

    public PasteMemento createSnapshot() {
        return new PasteMemento(this.content, this.createdAt,
                this.expirationPolicy, this.url, this.viewCount);
    }
}
