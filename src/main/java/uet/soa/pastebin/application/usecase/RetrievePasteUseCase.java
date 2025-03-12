package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.service.AnalyticsService;

import java.util.Optional;

public class RetrievePasteUseCase {
    private final PasteRepository pasteRepository;
    private final AnalyticsService analyticsService;

    public RetrievePasteUseCase(PasteRepository pasteRepository, AnalyticsService analyticsService) {
        this.pasteRepository = pasteRepository;
        this.analyticsService = analyticsService;
    }

    public String execute(String url) throws PasteNotFoundException {
        Optional<Paste> pasteOpt = pasteRepository.findByUrl(url);
        if (pasteOpt.isEmpty()) {
            throw new PasteNotFoundException("Paste not found for URL: " + url);
        }

        Paste paste = pasteOpt.get();

        String contentValue = paste.provideContent();

        if (!paste.isExpired()) {
            pasteRepository.delete(paste);
        }

        analyticsService.recordView(paste);

        return contentValue;
    }
}
