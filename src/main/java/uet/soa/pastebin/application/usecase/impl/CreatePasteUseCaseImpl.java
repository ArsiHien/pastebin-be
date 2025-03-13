package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.CreatePasteRequest;
import uet.soa.pastebin.application.dto.CreatePasteResponse;
import uet.soa.pastebin.application.usecase.CreatePasteUseCase;
import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.PasteRepository;

@AllArgsConstructor
public class CreatePasteUseCaseImpl implements CreatePasteUseCase {
    private final PasteRepository pasteRepository;

    @Override
    public CreatePasteResponse execute(CreatePasteRequest request) {
        {
            Content content = Content.of(request.content());
            ExpirationPolicy policy = ExpirationPolicyFactory.create(
                    request.policyType(),
                    request.duration()
            );

            Paste paste = Paste.create(content, request.createdAt(), policy);
            pasteRepository.save(paste);

            return new CreatePasteResponse(paste.publishUrl().toString());
        }
    }
}
