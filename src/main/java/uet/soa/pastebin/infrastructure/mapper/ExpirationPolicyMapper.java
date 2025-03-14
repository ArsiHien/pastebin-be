package uet.soa.pastebin.infrastructure.mapper;

import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.repository.ExpirationPolicyJpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class ExpirationPolicyMapper {
    public static ExpirationPolicy toDomain(JpaExpirationPolicy jpaPolicy, LocalDateTime createdAt) {
        return ExpirationPolicyFactory.create(jpaPolicy.getPolicyType().toString(),
                jpaPolicy.getDuration());
    }

    public static JpaExpirationPolicy toEntity(ExpirationPolicy domainPolicy,
                                               ExpirationPolicyJpaRepository jpaRepository) {
        Optional<JpaExpirationPolicy> existingPolicy = jpaRepository.findByPolicyTypeAndDuration(
                JpaExpirationPolicy.PolicyType.valueOf(domainPolicy.type().name()),
                domainPolicy.durationAsString()
        );

        return existingPolicy.orElseGet(() -> JpaExpirationPolicy.builder()
                .policyType(JpaExpirationPolicy.PolicyType.valueOf(domainPolicy.type().name()))
                .duration(domainPolicy.type() == ExpirationPolicy.ExpirationPolicyType.TIMED ? domainPolicy.durationAsString() : null)
                .build());
    }
}