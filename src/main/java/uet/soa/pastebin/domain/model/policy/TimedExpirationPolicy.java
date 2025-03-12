package uet.soa.pastebin.domain.model.policy;

import java.time.LocalDateTime;

public class TimedExpirationPolicy implements ExpirationPolicy {
    private final ExpirationDuration expirationDuration;

    public TimedExpirationPolicy(ExpirationDuration duration) {
        this.expirationDuration = duration;
    }

    @Override
    public boolean isExpired(LocalDateTime createdAt) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expirationTime = createdAt.plus(expirationDuration.toDuration());
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
