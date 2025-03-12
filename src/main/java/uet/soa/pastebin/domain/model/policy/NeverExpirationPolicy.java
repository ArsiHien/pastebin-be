package uet.soa.pastebin.domain.model.policy;

import java.time.LocalDateTime;

public class NeverExpirationPolicy implements ExpirationPolicy {
    @Override
    public boolean isExpired(LocalDateTime createdAt) {
        return false;
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.NEVER;
    }

    @Override
    public String durationAsString() {
        return null;
    }
}
