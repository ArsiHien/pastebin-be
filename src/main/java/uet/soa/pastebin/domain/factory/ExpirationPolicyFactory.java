package uet.soa.pastebin.domain.factory;

import uet.soa.pastebin.domain.model.policy.BurnAfterReadExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.NeverExpirationPolicy;
import uet.soa.pastebin.domain.model.policy.TimedExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;

import java.time.LocalDateTime;

public class ExpirationPolicyFactory {
    public static ExpirationPolicy create(String type, String duration) {
        ExpirationPolicy.ExpirationPolicyType policyType = ExpirationPolicy.ExpirationPolicyType.valueOf(type);
        return switch (policyType) {
            case TIMED -> new TimedExpirationPolicy(ExpirationPolicy.ExpirationDuration.fromString(duration));
            case NEVER -> new NeverExpirationPolicy();
            case BURN_AFTER_READ -> new BurnAfterReadExpirationPolicy();
        };
    }

    public static ExpirationPolicy create(JpaExpirationPolicy jpaPolicy, LocalDateTime createdAt) {
        return switch (jpaPolicy.getPolicyType()) {
            case TIMED -> new TimedExpirationPolicy(ExpirationPolicy.ExpirationDuration.fromString(jpaPolicy.getDuration()));
            case NEVER -> new NeverExpirationPolicy();
            case BURN_AFTER_READ -> {
                BurnAfterReadExpirationPolicy policy = new BurnAfterReadExpirationPolicy();
                if (jpaPolicy.isRead()) policy.markAsRead();
                yield policy;
            }
        };
    }
}
