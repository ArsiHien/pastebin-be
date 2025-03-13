package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.usecase.CleanupExpiredPastesUseCase;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.util.List;

@AllArgsConstructor
public class CleanupExpiredPastesUseCaseImpl implements CleanupExpiredPastesUseCase {
    private final PasteRepository pasteRepository;

    public void cleanupExpiredPastes() {
        List<Paste> timedPastes = pasteRepository.findTimedPastes();

        List<Paste> expiredPastes = timedPastes.stream()
                .filter(Paste::isExpired)
                .toList();

        expiredPastes.forEach(pasteRepository::delete);
    }
}
