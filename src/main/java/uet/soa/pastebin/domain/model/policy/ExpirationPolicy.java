package uet.soa.pastebin.domain.model.policy;

import java.time.Duration;
import java.time.Period;
import java.time.temporal.TemporalAmount;

public interface ExpirationPolicy {
    boolean isExpired();

    ExpirationPolicyType type();

    enum ExpirationPolicyType {
        TIMED, NEVER, BURN_AFTER_READ
    }

    enum ExpirationDuration {
        TEN_MINUTES(Duration.ofMinutes(10)),
        ONE_HOUR(Duration.ofHours(1)),
        ONE_DAY(Duration.ofDays(1)),
        ONE_WEEK(Period.ofWeeks(1)),
        TWO_WEEKS(Period.ofWeeks(2)),
        ONE_MONTH(Period.ofMonths(1)),
        SIX_MONTHS(Period.ofMonths(6)),
        ONE_YEAR(Period.ofYears(1));

        private final TemporalAmount duration;

        ExpirationDuration(TemporalAmount duration) {
            this.duration = duration;
        }

        public TemporalAmount getDuration() {
            return duration;
        }
    }
}
