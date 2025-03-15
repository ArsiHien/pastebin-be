package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.RetrievePasteResponse;
import uet.soa.pastebin.application.usecase.RetrievePasteUseCase;
import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.repository.RecordRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class RetrievePasteUseCaseImpl implements RetrievePasteUseCase {
    private final PasteRepository pasteRepository;
    private final RecordRepository recordRepository;

    @Override
    public RetrievePasteResponse execute(String url) {
        Optional<Paste> pasteOpt = pasteRepository.findByUrl(url);
        if (pasteOpt.isEmpty()) {
            throw new RuntimeException("Paste not found for URL: " + url);
        }

        Paste paste = pasteOpt.get();

        if (paste.isExpired()) {
            pasteRepository.delete(paste);
            throw new RuntimeException("Paste has expired for URL: " + url);
        }

        paste.onAccess();
        pasteRepository.update(paste);

        recordRepository.save(new Record(paste, LocalDateTime.now()));

        String remainingTime = paste.calculateTimeUntilExpiration();

        if(paste.isExpired()){
            pasteRepository.delete(paste);
        }

        return new RetrievePasteResponse(paste.publishUrl().toString(),
                paste.provideContent(),
                paste.totalViews(), remainingTime);
    }
}
