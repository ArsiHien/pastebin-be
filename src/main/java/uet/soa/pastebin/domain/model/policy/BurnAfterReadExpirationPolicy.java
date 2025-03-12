package uet.soa.pastebin.domain.model.policy;

public class BurnAfterReadExpirationPolicy implements ExpirationPolicy {
    private boolean isRead;

    public BurnAfterReadExpirationPolicy() {
        isRead = false;
    }

    @Override
    public boolean isExpired() {
        return isRead;
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.BURN_AFTER_READ;
    }

    public void markAsRead() {
        isRead = true;
    }
}
