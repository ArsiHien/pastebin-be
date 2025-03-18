package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.RetrievePasteResponse;
import uet.soa.pastebin.application.dto.StatsResponse;

public interface RetrievePasteUseCase {

    RetrievePasteResponse getPaste(String url);

    StatsResponse getStats(String url);

    String getPolicy(String url);
}
