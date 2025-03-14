package uet.soa.pastebin.application.dto;

import java.time.LocalDateTime;

public record TimeSeriesPoint(LocalDateTime timestamp, int viewCount) {
}