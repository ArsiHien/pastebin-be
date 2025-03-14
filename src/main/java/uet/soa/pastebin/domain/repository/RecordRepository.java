package uet.soa.pastebin.domain.repository;

import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface RecordRepository {
    Optional<Record> findByPasteUrlAndDate(String pasteUrl, LocalDateTime date);
    List<Record> findAllByPasteUrl(String pasteUrl);
    List<Record> findAllInRangeByPasteUrl(String pasteUrl, LocalDateTime startTime, LocalDateTime endTime);
    void save(Record record);
    void update(Record record);
}
