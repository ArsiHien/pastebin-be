package uet.soa.pastebin.domain.model.policy;

import java.time.LocalDateTime;

public class BurnAfterReadExpirationPolicy implements ExpirationPolicy {
    private boolean isRead;

    public BurnAfterReadExpirationPolicy() {
        this.isRead = false;
    }

    @Override
    public boolean isExpired(LocalDateTime createdAt) {
        return isRead;
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.BURN_AFTER_READ;
    }

    @Override
    public String durationAsString() {
        return null;
    }

    public void markAsRead() {
        isRead = true;
    }

    public boolean isRead() {
        return isRead;
    }
}
