package uet.soa.pastebin.infrastructure.service;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uet.soa.pastebin.application.usecase.CleanupExpiredPastesUseCase;

@Service
@AllArgsConstructor
public class CleanupExpiredPastesService {
    private final CleanupExpiredPastesUseCase cleanupExpiredPastesUseCase;

    @Scheduled(fixedRate = 3600000)
    public void cleanup() {
        cleanupExpiredPastesUseCase.cleanupExpiredPastes();
    }
}
