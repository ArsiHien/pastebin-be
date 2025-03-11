package uet.soa.pastebin.domain.model;

import java.time.LocalDate;

public class TimedExpirationPolicy implements ExpirationPolicy {
    private final LocalDate expirationTime;

    public TimedExpirationPolicy(LocalDate expirationTime) {
        this.expirationTime = expirationTime;
    }

    @Override
    public boolean isExpired() {
        return LocalDate.now().isAfter(expirationTime);
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.TIMED;
    }
}
