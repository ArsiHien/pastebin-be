package uet.soa.pastebin.application.usecase;

import uet.soa.pastebin.application.dto.PasteTimeSeriesResponse;

public interface AnalyticsUseCase {
    PasteTimeSeriesResponse getHourlyStatistics(String pasteUrl);

    PasteTimeSeriesResponse getWeeklyStatistics(String pasteUrl);

    PasteTimeSeriesResponse getMonthlyStatistics(String pasteUrl);
}
