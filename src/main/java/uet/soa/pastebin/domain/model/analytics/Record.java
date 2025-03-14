package uet.soa.pastebin.domain.model.analytics;

import uet.soa.pastebin.domain.model.paste.Paste;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class Record {
    private final Paste paste;
    private final LocalDateTime viewTime;

    public Record(Paste paste, LocalDateTime recordDate) {
        this.paste = Objects.requireNonNull(paste, "Paste cannot be null");
        this.viewTime = Objects.requireNonNull(recordDate, "Record date cannot be null");
    }

    public boolean belongsToTimeSlot(LocalDateTime slotStart, LocalDateTime slotEnd) {
        return !viewTime.isBefore(slotStart) && viewTime.isBefore(slotEnd);
    }

    public LocalDateTime truncateToTimeSlot(ChronoUnit granularity, int interval, LocalDateTime startTime) {
        long slotsSinceStart;
        switch (granularity) {
            case MINUTES:
                long minutesSinceStart = ChronoUnit.MINUTES.between(startTime, viewTime);
                slotsSinceStart = minutesSinceStart / interval;
                return startTime.plusMinutes(slotsSinceStart * interval);
            case DAYS:
                long daysSinceStart = ChronoUnit.DAYS.between(startTime, viewTime);
                slotsSinceStart = daysSinceStart / interval;
                return startTime.plusDays(slotsSinceStart * interval);
            case MONTHS:
                long monthsSinceStart = ChronoUnit.MONTHS.between(startTime, viewTime);
                slotsSinceStart = monthsSinceStart / interval;
                return startTime.plusMonths(slotsSinceStart * interval);
            default:
                throw new IllegalArgumentException("Unsupported granularity: " + granularity);
        }
    }

    public RecordMemento createSnapshot() {
        return new RecordMemento(paste, viewTime);
    }

    public record RecordMemento(Paste paste, LocalDateTime viewTime) {
        public Record restore() {
            return new Record(paste, viewTime);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Record record = (Record) o;
        return paste.equals(record.paste) && viewTime.equals(record.viewTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(paste, viewTime);
    }
}