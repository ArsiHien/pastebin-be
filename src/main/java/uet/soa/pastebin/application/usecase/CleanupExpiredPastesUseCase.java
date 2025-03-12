package uet.soa.pastebin.application.usecase;

import org.springframework.stereotype.Component;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.util.List;

@Component
public class CleanupExpiredPastesUseCase {
    private final PasteRepository pasteRepository;

    public CleanupExpiredPastesUseCase(final PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public void cleanupExpiredPastes() {
        List<Paste> expiredPastes = pasteRepository.findExpiredPastes();
        for (Paste paste : expiredPastes) {
            pasteRepository.delete(paste);
        }
    }
}
