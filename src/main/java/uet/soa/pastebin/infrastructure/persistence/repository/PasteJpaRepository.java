package uet.soa.pastebin.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasteJpaRepository extends JpaRepository<JpaPaste, String> {
    Optional<JpaPaste> findByUrl(String url);

    @Query("SELECT p FROM JpaPaste p WHERE p.expirationPolicy.expirationTime " +
            "IS NOT NULL AND p.expirationPolicy.expirationTime < :now")
    List<JpaPaste> findExpiredPastes(LocalDateTime now);
}
