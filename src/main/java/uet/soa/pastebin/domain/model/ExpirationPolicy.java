package uet.soa.pastebin.domain.model;

public interface ExpirationPolicy {
    boolean isExpired();

    ExpirationPolicyType type();

    default void markAsRead() {

    }

    enum ExpirationPolicyType {
        TIMED, NEVER, BURN_AFTER_READ
    }
}
