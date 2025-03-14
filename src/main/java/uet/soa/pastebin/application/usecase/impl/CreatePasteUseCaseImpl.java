package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.CreatePasteRequest;
import uet.soa.pastebin.application.dto.CreatePasteResponse;
import uet.soa.pastebin.application.usecase.CreatePasteUseCase;
import uet.soa.pastebin.domain.factory.ExpirationPolicyFactory;
import uet.soa.pastebin.domain.model.paste.Content;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;
import uet.soa.pastebin.domain.repository.ExpirationPolicyRepository;
import uet.soa.pastebin.domain.repository.PasteRepository;

import java.time.LocalDateTime;
import java.util.Optional;

@AllArgsConstructor
public class CreatePasteUseCaseImpl implements CreatePasteUseCase {
    private final PasteRepository pasteRepository;
    private final ExpirationPolicyRepository expirationPolicyRepository;

    @Override
    public CreatePasteResponse execute(CreatePasteRequest request) {
        Content content = Content.of(request.content());
        ExpirationPolicy.ExpirationPolicyType policyType = ExpirationPolicy.ExpirationPolicyType.valueOf(request.policyType());
        String duration = request.duration();

        Optional<ExpirationPolicy> existPolicyOpt = expirationPolicyRepository.findByPolicyTypeAndDuration(policyType, duration);
        ExpirationPolicy policy;

        if (existPolicyOpt.isPresent()) {
            policy = existPolicyOpt.get();
        } else {
            policy = ExpirationPolicyFactory.create(policyType.name(), duration);
            expirationPolicyRepository.save(policy);
        }

        Paste paste = Paste.create(content, LocalDateTime.now(), policy);
        pasteRepository.save(paste);

        return new CreatePasteResponse(paste.publishUrl().toString());
    }
}
