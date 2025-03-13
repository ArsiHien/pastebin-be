package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.RetrievePasteResponse;
import uet.soa.pastebin.application.usecase.PasteExpiredException;
import uet.soa.pastebin.application.usecase.PasteNotFoundException;
import uet.soa.pastebin.application.usecase.RetrievePasteUseCase;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.service.AnalyticsService;

import java.util.Optional;

@AllArgsConstructor
public class RetrievePasteUseCaseImpl implements RetrievePasteUseCase {
    private final PasteRepository pasteRepository;
    private final AnalyticsService analyticsService;

    @Override
    public RetrievePasteResponse execute(String url) throws PasteNotFoundException, PasteExpiredException {
        Optional<Paste> pasteOpt = pasteRepository.findByUrl(url);
        if (pasteOpt.isEmpty()) {
            throw new PasteNotFoundException("Paste not found for URL: " + url);
        }

        Paste paste = pasteOpt.get();

        if (paste.isExpired()) {
            pasteRepository.delete(paste);
            throw new PasteExpiredException("Paste has expired for URL: " + url);
        }

        paste.onAccess();
        pasteRepository.update(paste);

        analyticsService.recordView(paste);

        String remainingTime = paste.calculateTimeUntilExpiration();

        return new RetrievePasteResponse(paste.provideContent(),
                paste.totalViews(), remainingTime);
    }
}
