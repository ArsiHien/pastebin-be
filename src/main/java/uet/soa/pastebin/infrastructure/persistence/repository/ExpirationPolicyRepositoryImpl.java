package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.ExpirationPolicyRepository;
import uet.soa.pastebin.infrastructure.mapper.ExpirationPolicyMapper;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ExpirationPolicyRepositoryImpl implements ExpirationPolicyRepository {
    private final ExpirationPolicyJpaRepository jpaRepository;

    @Override
    public Optional<ExpirationPolicy> findByPolicyTypeAndDuration(ExpirationPolicy.ExpirationPolicyType policyType, String duration) {
        return jpaRepository.findByPolicyTypeAndDuration(
                        JpaExpirationPolicy.PolicyType.valueOf(policyType.name()), duration)
                .map(jpaPolicy -> ExpirationPolicyMapper.toDomain(jpaPolicy, null));
    }

    @Override
    public void save(ExpirationPolicy expirationPolicy) {
        JpaExpirationPolicy jpaPolicy = ExpirationPolicyMapper.toEntity(expirationPolicy, jpaRepository);
        jpaRepository.save(jpaPolicy);
    }
}