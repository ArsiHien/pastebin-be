package uet.soa.pastebin.domain.model;

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
    public void markAsRead() {
        isRead = true;
    }
}
