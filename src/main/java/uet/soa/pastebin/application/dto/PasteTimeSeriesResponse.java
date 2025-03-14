package uet.soa.pastebin.application.dto;

import java.util.List;

public record PasteTimeSeriesResponse(
        String pasteUrl,
        int totalViews,
        List<TimeSeriesPoint> timeSeries
) {
}
