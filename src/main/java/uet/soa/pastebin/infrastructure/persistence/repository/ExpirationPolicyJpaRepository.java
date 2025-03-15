package uet.soa.pastebin.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;

import java.util.Optional;

public interface ExpirationPolicyJpaRepository
        extends JpaRepository<JpaExpirationPolicy, String> {
    @Query("SELECT ep FROM JpaExpirationPolicy ep " +
            "WHERE ep.policyType = :policyType " +
            "AND (:policyType = 'TIMED' AND ep.duration = :duration " +
            "OR :policyType <> 'TIMED' AND ep.duration IS NULL)")
    Optional<JpaExpirationPolicy> findByPolicyTypeAndDuration(JpaExpirationPolicy.PolicyType policyType, String duration);

}
