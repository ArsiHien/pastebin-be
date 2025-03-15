package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.RetrievePasteResponse;

public interface RetrievePasteUseCase {

    RetrievePasteResponse execute(String url);
}
