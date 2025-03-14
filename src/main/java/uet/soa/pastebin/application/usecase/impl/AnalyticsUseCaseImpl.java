package uet.soa.pastebin.application.usecase.impl;

import lombok.AllArgsConstructor;
import uet.soa.pastebin.application.dto.PasteTimeSeriesResponse;
import uet.soa.pastebin.application.dto.TimeSeriesPoint;
import uet.soa.pastebin.application.usecase.AnalyticsUseCase;
import uet.soa.pastebin.domain.model.analytics.Record;
import uet.soa.pastebin.domain.model.paste.Paste;
import uet.soa.pastebin.domain.repository.PasteRepository;
import uet.soa.pastebin.domain.repository.RecordRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class AnalyticsUseCaseImpl implements AnalyticsUseCase {
    private final RecordRepository recordRepository;
    private final PasteRepository pasteRepository;

    @Override
    public PasteTimeSeriesResponse getHourlyStatistics(String pasteUrl) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusHours(1);
        return getTimeSeriesForPaste(pasteUrl, startTime, endTime, ChronoUnit.MINUTES, 5);
    }

    @Override
    public PasteTimeSeriesResponse getWeeklyStatistics(String pasteUrl) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusWeeks(1);
        return getTimeSeriesForPaste(pasteUrl, startTime, endTime, ChronoUnit.DAYS, 1);
    }

    @Override
    public PasteTimeSeriesResponse getMonthlyStatistics(String pasteUrl) {
        LocalDateTime endTime = LocalDateTime.now();
        LocalDateTime startTime = endTime.minusMonths(1);
        return getTimeSeriesForPaste(pasteUrl, startTime, endTime, ChronoUnit.DAYS, 1);
    }

    private PasteTimeSeriesResponse getTimeSeriesForPaste(
            String pasteUrl,
            LocalDateTime startTime,
            LocalDateTime endTime,
            ChronoUnit granularity,
            int interval
    ) {
        pasteRepository.findByUrl(pasteUrl)
                .orElseThrow(() -> new IllegalArgumentException("Paste not found"));

        List<Record> records = recordRepository.findAllInRangeByPasteUrl(pasteUrl, startTime, endTime);

        int totalViews = records.size();

        Map<LocalDateTime, Long> viewsByTimeSlot = records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.truncateToTimeSlot(granularity, interval, startTime),
                        Collectors.counting()
                ));

        List<TimeSeriesPoint> timeSeries = generateTimeSeriesPoints(startTime, endTime, granularity, interval, viewsByTimeSlot);

        return new PasteTimeSeriesResponse(pasteUrl, totalViews, timeSeries);
    }

    private List<TimeSeriesPoint> generateTimeSeriesPoints(
            LocalDateTime startTime,
            LocalDateTime endTime,
            ChronoUnit granularity,
            int interval,
            Map<LocalDateTime, Long> viewsByTimeSlot
    ) {
        List<TimeSeriesPoint> timeSeries = new ArrayList<>();
        LocalDateTime current = startTime.truncatedTo(granularity);

        while (!current.isAfter(endTime)) {
            int viewCount = viewsByTimeSlot.getOrDefault(current, 0L).intValue();
            timeSeries.add(new TimeSeriesPoint(current, viewCount));
            current = current.plus(interval, granularity);
        }

        return timeSeries;
    }
}