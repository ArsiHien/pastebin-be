package uet.soa.pastebin.domain.model;

public class NeverExpirationPolicy implements ExpirationPolicy {
    @Override
    public boolean isExpired() {
        return false;
    }

    @Override
    public ExpirationPolicyType type() {
        return ExpirationPolicyType.NEVER;
    }
}
