package uet.soa.pastebin.domain.model.policy;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;

public interface ExpirationPolicy {
    boolean isExpired(LocalDateTime createdAt);
    ExpirationPolicyType type();
    String durationAsString();

    enum ExpirationPolicyType {
        TIMED, NEVER, BURN_AFTER_READ
    }

    enum ExpirationDuration {
        TEN_MINUTES(Duration.ofMinutes(10), "10m"),
        ONE_HOUR(Duration.ofHours(1), "1h"),
        ONE_DAY(Duration.ofDays(1), "1d"),
        ONE_WEEK(Duration.ofDays(7), "1w"),
        TWO_WEEKS(Duration.ofDays(14), "2w"),
        ONE_MONTH(Duration.ofDays(30), "1m"),  // Approximation
        SIX_MONTHS(Duration.ofDays(180), "6m"),  // Approximation
        ONE_YEAR(Duration.ofDays(365), "1y");  // Approximation

        private final Duration duration;
        private final String durationString;

        ExpirationDuration(Duration duration, String durationString) {
            this.duration = duration;
            this.durationString = durationString;
        }

        public Duration toDuration() {
            return duration;
        }

        public String toString() {
            return durationString;
        }

        public static ExpirationDuration fromString(String value) {
            return Arrays.stream(values())
                    .filter(d -> d.durationString.equals(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown duration: " + value));
        }
    }
}
