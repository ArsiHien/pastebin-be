package uet.soa.pastebin.application.dto;

import java.util.Objects;

public record CreatePasteResponse(String url) {
    public CreatePasteResponse{
        Objects.requireNonNull(url, "URL can not be null");
    }
}
