package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.CreatePasteRequest;
import uet.soa.pastebin.application.dto.CreatePasteResponse;

public interface CreatePasteUseCase {
    CreatePasteResponse execute(CreatePasteRequest request);
}
