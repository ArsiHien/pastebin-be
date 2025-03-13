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
        TEN_MINUTES(Duration.ofMinutes(10), "10minutes"),
        ONE_HOUR(Duration.ofHours(1), "1hour"),
        ONE_DAY(Duration.ofDays(1), "1day"),
        ONE_WEEK(Duration.ofDays(7), "1week"),
        TWO_WEEKS(Duration.ofDays(14), "2weeks"),
        ONE_MONTH(Duration.ofDays(30), "1month"),
        SIX_MONTHS(Duration.ofDays(180), "6months"),
        ONE_YEAR(Duration.ofDays(365), "1year");

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
                    .filter(d -> d.durationString.equalsIgnoreCase(value))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Unknown duration: " + value));
        }

    }
}
