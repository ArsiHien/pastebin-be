package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.repository.RecordRepository;
import uet.soa.pastebin.infrastructure.mapper.RecordMapper;
import uet.soa.pastebin.infrastructure.persistence.model.JpaRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecordRepositoryImpl implements RecordRepository {
    private final RecordJpaRepository jpaRepository;
    private final PasteRepository pasteRepository;
    private final PasteJpaRepository pasteJpaRepository;
    private final ExpirationPolicyJpaRepository expirationPolicyJpaRepository;

    @Override
    public Optional<Record> findByPasteUrlAndDate(String pasteUrl, LocalDateTime date) {
        return jpaRepository.findByPasteUrlAndViewTime(pasteUrl, date)
                .map(jpaRecord -> RecordMapper.toDomain(jpaRecord, pasteRepository));
    }

    @Override
    public List<Record> findAllByPasteUrl(String pasteUrl) {
        return jpaRepository.findAllByPasteUrl(pasteUrl)
                .stream()
                .map(jpaRecord -> RecordMapper.toDomain(jpaRecord, pasteRepository))
                .collect(Collectors.toList());
    }

    @Override
    public List<Record> findAllInRangeByPasteUrl(String pasteUrl, LocalDateTime startTime, LocalDateTime endTime) {
        return jpaRepository.findAllByPasteUrlAndViewTimeBetween(pasteUrl, startTime, endTime)
                .stream()
                .map(jpaRecord -> RecordMapper.toDomain(jpaRecord, pasteRepository))
                .collect(Collectors.toList());
    }

    @Override
    public void save(Record record) {
        JpaRecord jpaRecord = RecordMapper.toEntity(record, expirationPolicyJpaRepository);
        jpaRepository.save(jpaRecord);
    }

    @Override
    public void update(Record record) {
        JpaRecord jpaRecord = RecordMapper.toEntity(record, expirationPolicyJpaRepository);
        jpaRepository.save(jpaRecord);
    }
}