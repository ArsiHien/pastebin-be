package uet.soa.pastebin.infrastructure.rest;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uet.soa.pastebin.application.dto.PasteTimeSeriesResponse;
import uet.soa.pastebin.application.usecase.AnalyticsUseCase;

@RestController
@RequestMapping("/api/analytics")
@AllArgsConstructor
public class AnalyticsController {
    private final AnalyticsUseCase analyticsUseCase;

    @GetMapping("/hourly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getHourlyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getHourlyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/weekly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getWeeklyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getWeeklyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/monthly/{pasteUrl}")
    public ResponseEntity<PasteTimeSeriesResponse> getMonthlyStatistics(@PathVariable String pasteUrl) {
        PasteTimeSeriesResponse response = analyticsUseCase.getMonthlyStatistics(pasteUrl);
        return ResponseEntity.ok(response);
    }
}