package uet.soa.pastebin.infrastructure.mapper;

import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.paste.URL;
import uet.soa.pastebin.infrastructure.persistence.model.JpaExpirationPolicy;
import uet.soa.pastebin.infrastructure.persistence.model.JpaPaste;

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
}