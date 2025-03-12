package uet.soa.pastebin.domain.model.policy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Objects;

public class TimedExpirationPolicy implements ExpirationPolicy {
    private final Duration expirationDuration;

    public TimedExpirationPolicy(Duration expirationDuration) {
        this.expirationDuration = Objects.requireNonNull(expirationDuration,
                "Expiration duration cannot be null");
    }

    public TimedExpirationPolicy(ExpirationDuration duration) {
        this(duration.toDuration());
    }

    @Override
    public boolean isExpired(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = createdAt.plus(expirationDuration);
        return now.isAfter(expirationTime);
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.TIMED;
    }

    @Override
    public String durationAsString() {
        return ExpirationDuration.fromString(expirationDuration.toString()).toString();
    }
}
