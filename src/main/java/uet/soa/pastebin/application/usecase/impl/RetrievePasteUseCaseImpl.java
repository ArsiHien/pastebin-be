package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.RetrievePasteResponse;
import uet.soa.pastebin.application.dto.StatsResponse;
import uet.soa.pastebin.application.usecase.RetrievePasteUseCase;
import uet.soa.pastebin.domain.event.EventPublisher;
import uet.soa.pastebin.domain.event.PasteAccessedEvent;
import uet.soa.pastebin.domain.event.RecordPasteEvent;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.util.Optional;

@AllArgsConstructor
public class RetrievePasteUseCaseImpl implements RetrievePasteUseCase {
    private final PasteRepository pasteRepository;
    private final EventPublisher eventPublisher;

    @Override
    public String getPolicy(String url) {
        Paste paste = getValidPaste(url, false);
        return paste.calculateTimeUntilExpiration();
    }

    @Override
    public RetrievePasteResponse getPaste(String url) {
        Paste paste = getValidPaste(url, false);

        paste.onAccess();
        eventPublisher.publishPasteAccessedEvent(new PasteAccessedEvent(url));
        eventPublisher.publishRecordPasteEvent(new RecordPasteEvent(paste));

        if (paste.isExpired()) {
            pasteRepository.delete(paste);
        }

        return new RetrievePasteResponse(paste.publishUrl().toString(),
                paste.provideContent(),
                paste.calculateTimeUntilExpiration());
    }

    @Override
    public StatsResponse getStats(String url) {
        Paste paste = getValidPaste(url, true);

        return new StatsResponse(paste.totalViews());
    }

    private Paste getValidPaste(String url, boolean needStats) {
        Optional<Paste> pasteOpt = pasteRepository.findByUrl(url, needStats);
        if (pasteOpt.isEmpty()) {
            throw new RuntimeException("Paste not found for URL: " + url);
        }

        Paste paste = pasteOpt.get();
        if (paste.isExpired()) {
            pasteRepository.delete(paste);
            throw new RuntimeException("Paste has expired for URL: " + url);
        }

        return paste;
    }
}

