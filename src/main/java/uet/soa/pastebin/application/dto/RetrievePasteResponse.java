package uet.soa.pastebin.application.dto;

import java.util.Objects;

public record RetrievePasteResponse(
        String content, long totalViews, String remainingTime
) {
    public RetrievePasteResponse {
        Objects.requireNonNull(content, "Content cannot be null");
    }
}
