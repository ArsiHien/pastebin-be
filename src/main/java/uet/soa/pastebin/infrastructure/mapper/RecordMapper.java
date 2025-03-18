package uet.soa.pastebin.infrastructure.mapper;

import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;
import uet.soa.pastebin.infrastructure.persistence.model.JpaRecord;
import uet.soa.pastebin.infrastructure.persistence.repository.ExpirationPolicyJpaRepository;


public class RecordMapper {
    public static Record toDomain(JpaRecord jpaRecord, PasteRepository pasteRepository) {
        Paste paste = pasteRepository.findByUrl(jpaRecord.getPaste().getUrl()
                        , true)
                .orElseThrow(() -> new IllegalStateException("Paste not found"));
        return new Record(paste, jpaRecord.getViewTime());
    }

    public static JpaRecord toEntity(Record record,
                                     ExpirationPolicyJpaRepository expirationPolicyJpaRepository) {
        Record.RecordMemento memento = record.createSnapshot();
        JpaPaste jpaPaste = PasteMapper.toEntity(memento.paste().createSnapshot(),
                ExpirationPolicyMapper.toEntity(memento.paste().createSnapshot().getExpirationPolicy(), expirationPolicyJpaRepository));

        return JpaRecord.builder()
                .viewTime(memento.viewTime())
                .paste(jpaPaste)
                .build();
    }
}