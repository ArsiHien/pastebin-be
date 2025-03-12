package uet.soa.pastebin.infrastructure.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPasteView;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PasteViewJpaRepository
        extends JpaRepository<JpaPasteView, String> {
    Optional<JpaPasteView> findByPasteIdAndViewTime(String pasteId,
                                                    LocalDateTime viewTime);

    List<JpaPasteView> findAllByPasteId(String pasteId);
}
