package uet.soa.pastebin.domain.event;

public interface EventPublisher {
    void publishPasteAccessedEvent(PasteAccessedEvent event);

    void publishRecordPasteEvent(RecordPasteEvent event);
}
