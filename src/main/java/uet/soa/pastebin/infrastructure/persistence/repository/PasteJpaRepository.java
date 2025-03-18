package uet.soa.pastebin.infrastructure.persistence.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;

import java.util.List;
import java.util.Optional;

@Repository
public interface PasteJpaRepository extends JpaRepository<JpaPaste, String> {
    Optional<JpaPaste> findByUrl(String url);

    @Query(value = """
                SELECT p FROM JpaPaste p
                JOIN FETCH p.expirationPolicy
                WHERE p.expirationPolicy.policyType = 'TIMED'
            """)
    List<JpaPaste> findTimedPastes();

    @Transactional
    @Modifying
    @Query("UPDATE JpaPaste p SET p.viewCount = p.viewCount + 1 WHERE p.url = :url")
    void incrementViewCount(String url);
}
