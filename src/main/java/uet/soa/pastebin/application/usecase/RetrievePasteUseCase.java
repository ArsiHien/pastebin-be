package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.ContentResponse;
import uet.soa.pastebin.application.dto.StatsResponse;

public interface RetrievePasteUseCase {

    ContentResponse getContent(String url);

    StatsResponse getStats(String url);
}
