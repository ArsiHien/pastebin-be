package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.RetrievePasteResponse;

public interface RetrievePasteUseCase {

    public RetrievePasteResponse execute(String url) throws PasteNotFoundException, PasteExpiredException;
}
