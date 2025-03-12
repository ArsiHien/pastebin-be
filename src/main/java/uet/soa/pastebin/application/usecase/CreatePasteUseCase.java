package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.paste.URL;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.time.LocalDateTime;
import java.util.Objects;

public class CreatePasteUseCase {
    private final PasteRepository pasteRepository;

    public CreatePasteUseCase(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public URL execute(Content content, LocalDateTime createdAt,
                       ExpirationPolicy.ExpirationPolicyType policyType,
                       ExpirationPolicy.ExpirationDuration duration) {
        Objects.requireNonNull(createdAt, "Creation time cannot be null");
        ExpirationPolicy policy = ExpirationPolicyFactory.create(policyType, createdAt, duration);
        Paste paste = Paste.create(content, createdAt, policy);
        pasteRepository.save(paste);
        return paste.publishUrl();
    }
}
