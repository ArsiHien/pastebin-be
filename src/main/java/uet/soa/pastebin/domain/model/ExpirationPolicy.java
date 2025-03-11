package uet.soa.pastebin.domain.model;

public interface ExpirationPolicy {
    boolean isExpired();

    default void markAsRead() {

    }
}
