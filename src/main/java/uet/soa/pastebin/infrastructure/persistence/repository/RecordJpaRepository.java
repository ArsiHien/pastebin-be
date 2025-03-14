package uet.soa.pastebin.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uet.soa.pastebin.infrastructure.persistence.model.JpaRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordJpaRepository extends JpaRepository<JpaRecord, String> {
    @Query("SELECT r FROM JpaRecord r WHERE r.paste.url = :pasteUrl AND r.viewTime = :viewTime")
    Optional<JpaRecord> findByPasteUrlAndViewTime(String pasteUrl, LocalDateTime viewTime);

    @Query("SELECT r FROM JpaRecord r WHERE r.paste.url = :pasteUrl")
    List<JpaRecord> findAllByPasteUrl(String pasteUrl);

    @Query("SELECT r FROM JpaRecord r " +
            "WHERE r.paste.url = :pasteUrl AND r.viewTime >= :startTime AND r.viewTime <= :endTime")
    List<JpaRecord> findAllByPasteUrlAndViewTimeBetween(String pasteUrl, LocalDateTime startTime, LocalDateTime endTime);
}
