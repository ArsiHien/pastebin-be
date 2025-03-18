package uet.soa.pastebin.infrastructure.event;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import uet.soa.pastebin.domain.event.EventPublisher;
import uet.soa.pastebin.domain.event.PasteAccessedEvent;
import uet.soa.pastebin.domain.event.RecordPasteEvent;
import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.repository.RecordRepository;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class SpringEventPublisher implements EventPublisher {
    private final PasteRepository pasteRepository;
    private final RecordRepository recordRepository;

    @Async
    @Override
    public void publishPasteAccessedEvent(PasteAccessedEvent event) {
        pasteRepository.incrementViewCount(event.getPasteUrl());
    }

    @Async
    @Override
    public void publishRecordPasteEvent(RecordPasteEvent event) {
        recordRepository.save(new Record(event.getPaste(),
                LocalDateTime.now()));
    }
}
