package uet.soa.pastebin.infrastructure.mapper;

import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.paste.URL;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;
import uet.soa.pastebin.infrastructure.persistence.model.RedisPaste;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PasteMapper {
    public static Paste toDomain(JpaPaste jpaPaste) {
        Paste.PasteMemento memento = new Paste.PasteMemento(
                Content.of(jpaPaste.getContent()),
                jpaPaste.getCreatedAt(),
                ExpirationPolicyFactory.create(jpaPaste.getExpirationPolicy().getPolicyType().toString(),
                        jpaPaste.getExpirationPolicy().getDuration()),
                URL.of(jpaPaste.getUrl()),
                jpaPaste.getViewCount()
        );
        return memento.restore();
    }

    public static JpaPaste toEntity(Paste.PasteMemento memento, JpaExpirationPolicy policy) {
        return JpaPaste.builder()
                .content(memento.getContent().reveal())
                .url(memento.getUrl().toString())
                .createdAt(memento.getCreatedAt())
                .viewCount(memento.getViewCount())
                .expirationPolicy(policy)
                .build();
    }

    public static Paste toDomain(RedisPaste redisPaste) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Paste.PasteMemento memento = new Paste.PasteMemento(
                Content.of(redisPaste.getContent()),
                LocalDateTime.parse(redisPaste.getCreatedAt(), formatter),
                ExpirationPolicyFactory.create(redisPaste.getExpirationPolicyType(), redisPaste.getExpirationDuration()),
                URL.of(redisPaste.getUrl()),
                0
        );
        return memento.restore();
    }

    public static RedisPaste toCache(Paste.PasteMemento memento) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return RedisPaste.builder()
                .content(memento.getContent().reveal())
                .url(memento.getUrl().toString())
                .createdAt(memento.getCreatedAt().format(formatter))
                .expirationPolicyType(memento.getExpirationPolicy().type().toString())
                .expirationDuration(memento.getExpirationPolicy().durationAsString())
                .build();
    }

}