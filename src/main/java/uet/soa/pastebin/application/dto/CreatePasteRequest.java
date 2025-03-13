package uet.soa.pastebin.application.dto;

import uet.soa.pastebin.domain.model.policy.ExpirationPolicy;

import java.util.Objects;

public record CreatePasteRequest(
        String content,
        String policyType,
        String duration
) {
    public CreatePasteRequest {
        Objects.requireNonNull(content, "Content cannot be null");
        Objects.requireNonNull(policyType, "Policy type cannot be null");
        if (policyType.equals(ExpirationPolicy.ExpirationPolicyType.TIMED.name()) && duration == null) {
            throw new IllegalArgumentException("Duration is required for TIMED policy");
        }
    }
}
