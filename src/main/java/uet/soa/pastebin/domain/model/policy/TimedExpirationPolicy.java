package uet.soa.pastebin.domain.model.policy;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.Objects;

public class TimedExpirationPolicy implements ExpirationPolicy {
    private final TemporalAmount expirationDuration;
    private final LocalDateTime createdAt;

    public TimedExpirationPolicy(TemporalAmount expirationDuration, LocalDateTime createdAt) {
        this.expirationDuration = Objects.requireNonNull(expirationDuration,
                "Expiration duration cannot be null");
        this.createdAt = Objects.requireNonNull(createdAt,
                "Creation time cannot be null");
    }

    public TimedExpirationPolicy(ExpirationDuration duration, LocalDateTime createdAt) {
        this(duration.getDuration(), createdAt);
    }

    @Override
    public boolean isExpired() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = createdAt.plus(expirationDuration);
        return now.isAfter(expirationTime);
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.TIMED;
    }
}
