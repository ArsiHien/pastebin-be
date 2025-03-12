package uet.soa.pastebin.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;

public interface ExpirationPolicyJpaRepository
        extends JpaRepository<JpaExpirationPolicy, String> {
}
