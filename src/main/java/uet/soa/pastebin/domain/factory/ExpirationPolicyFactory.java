package uet.soa.pastebin.domain.factory;

import uet.soa.pastebin.domain.model.policy.BurnAfterReadExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.NeverExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.TimedExpirationPolicy;

import java.time.LocalDateTime;

public class ExpirationPolicyFactory {
    public static ExpirationPolicy create(ExpirationPolicy.ExpirationPolicyType type,
                                          LocalDateTime createdAt,
                                          ExpirationPolicy.ExpirationDuration duration) {
        return switch (type) {
            case TIMED -> {
                if (duration == null) {
                    throw new IllegalArgumentException("Expiration duration required for TIMED policy");
                }
                yield new TimedExpirationPolicy(duration, createdAt);
            }
            case NEVER -> new NeverExpirationPolicy();
            case BURN_AFTER_READ -> new BurnAfterReadExpirationPolicy();
        };
    }
}
