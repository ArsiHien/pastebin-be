package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.CreatePasteRequest;
import uet.soa.pastebin.application.dto.CreatePasteResponse;
import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.time.LocalDateTime;

public class CreatePasteUseCase {
    private final PasteRepository pasteRepository;

    public CreatePasteUseCase(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    public CreatePasteResponse execute(CreatePasteRequest request, LocalDateTime createdAt) {
        Content content = Content.of(request.content());
        ExpirationPolicy policy = ExpirationPolicyFactory.create(
                request.policyType(),
                createdAt,
                request.duration()
        );

        Paste paste = Paste.create(content, createdAt, policy);
        pasteRepository.save(paste);

        return new CreatePasteResponse(paste.publishUrl().toString());
    }
}
