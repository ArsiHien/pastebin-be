package uet.soa.pastebin.infrastructure.persistence.repository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.infrastructure.mapper.ExpirationPolicyMapper;
import uet.soa.pastebin.infrastructure.mapper.PasteMapper;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;
import uet.soa.pastebin.infrastructure.persistence.model.RedisPaste;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class PasteRepositoryImpl implements PasteRepository {
    private final PasteJpaRepository jpaRepository;
    private final ExpirationPolicyJpaRepository expirationPolicyJpaRepository;
    private final RedisTemplate<String, RedisPaste> redisTemplate;

    @Override
    public void save(Paste paste) {
        JpaPaste jpaPaste = PasteMapper.toEntity(paste.createSnapshot(),
                ExpirationPolicyMapper.toEntity(paste.createSnapshot().getExpirationPolicy(),
                        expirationPolicyJpaRepository));
        jpaRepository.save(jpaPaste);
        cachePaste(PasteMapper.toCache(paste.createSnapshot()));
    }

    @Override
    public Optional<Paste> findByUrl(String url, boolean needStats) {
        if(needStats){
            return jpaRepository.findByUrl(url)
                    .map(PasteMapper::toDomain);
        }
        ValueOperations<String, RedisPaste> ops = redisTemplate.opsForValue();

        RedisPaste cachedPaste = ops.get(url);
        if (cachedPaste != null) {
            return Optional.of(PasteMapper.toDomain(cachedPaste));
        }

        return jpaRepository.findByUrl(url)
                .map(PasteMapper::toDomain)
                .map(paste -> {
                    cachePaste(PasteMapper.toCache(paste.createSnapshot()));
                    return paste;
                });
    }

    @Override
    public List<Paste> findTimedPastes() {
        return jpaRepository.findTimedPastes()
                .stream()
                .map(PasteMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void update(Paste paste) {
        JpaPaste jpaPaste = PasteMapper.toEntity(paste.createSnapshot(),
                ExpirationPolicyMapper.toEntity(paste.createSnapshot().getExpirationPolicy(),
                        expirationPolicyJpaRepository));
        jpaRepository.save(jpaPaste);
        cachePaste(PasteMapper.toCache(paste.createSnapshot()));
    }

    @Override
    public void delete(Paste paste) {
        jpaRepository.deleteById(paste.publishUrl().toString());
        redisTemplate.delete(paste.publishUrl().toString());
    }

    @Override
    public void incrementViewCount(String url) {
        jpaRepository.incrementViewCount(url);
    }

    private void cachePaste(RedisPaste redisPaste) {
        ValueOperations<String, RedisPaste> ops = redisTemplate.opsForValue();
        long ttl = 3600;
        ops.set(redisPaste.getUrl(), redisPaste, ttl, TimeUnit.SECONDS);
    }
}