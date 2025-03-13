package uet.soa.pastebin.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import uet.soa.pastebin.application.usecase.impl.CreatePasteUseCaseImpl;
import uet.soa.pastebin.application.usecase.impl.RetrievePasteUseCaseImpl;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.service.AnalyticsService;

@Configuration
public class UseCaseConfig {
    @Bean
    public CreatePasteUseCaseImpl createPasteUseCaseImpl(PasteRepository pasteRepository) {
        return new CreatePasteUseCaseImpl(pasteRepository);
    }

    @Bean
    public RetrievePasteUseCaseImpl retrievePasteUseCaseImpl(PasteRepository pasteRepository, AnalyticsService analyticsService) {
        return new RetrievePasteUseCaseImpl(pasteRepository, analyticsService);
    }
}

