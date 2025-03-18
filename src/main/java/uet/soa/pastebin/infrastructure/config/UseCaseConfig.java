package uet.soa.pastebin.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uet.soa.pastebin.application.usecase.impl.AnalyticsUseCaseImpl;
import uet.soa.pastebin.application.usecase.impl.CleanupExpiredPastesUseCaseImpl;
import uet.soa.pastebin.application.usecase.impl.CreatePasteUseCaseImpl;
import uet.soa.pastebin.application.usecase.impl.RetrievePasteUseCaseImpl;
import uet.soa.pastebin.domain.event.EventPublisher;
import uet.soa.pastebin.domain.repository.ExpirationPolicyRepository;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.repository.RecordRepository;
import uet.soa.pastebin.infrastructure.event.SpringEventPublisher;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreatePasteUseCaseImpl createPasteUseCaseImpl(PasteRepository pasteRepository, ExpirationPolicyRepository expirationPolicyRepository) {
        return new CreatePasteUseCaseImpl(pasteRepository, expirationPolicyRepository);
    }

    @Bean
    public RetrievePasteUseCaseImpl retrievePasteUseCaseImpl(PasteRepository pasteRepository, EventPublisher eventPublisher) {
        return new RetrievePasteUseCaseImpl(pasteRepository, eventPublisher);
    }

    @Bean
    public CleanupExpiredPastesUseCaseImpl cleanupExpiredPastesUseCase(PasteRepository pasteRepository) {
        return new CleanupExpiredPastesUseCaseImpl(pasteRepository);
    }

    @Bean
    public AnalyticsUseCaseImpl analyticsUseCaseImpl(RecordRepository recordRepository, PasteRepository pasteRepository) {
        return new AnalyticsUseCaseImpl(recordRepository, pasteRepository);
    }

    @Bean
    public EventPublisher eventPublisher(PasteRepository pasteRepository, RecordRepository recordRepository) {
        return new SpringEventPublisher(pasteRepository, recordRepository);
    }
}

