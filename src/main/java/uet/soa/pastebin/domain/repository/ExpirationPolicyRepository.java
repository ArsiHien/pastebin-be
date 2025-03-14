package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;

import java.util.Optional;

public interface ExpirationPolicyRepository {
    Optional<ExpirationPolicy> findByPolicyTypeAndDuration(ExpirationPolicy.ExpirationPolicyType policyType, String duration);

    void save(ExpirationPolicy expirationPolicy);
}
