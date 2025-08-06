package faang.school.analytics.model.enums;

import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.function.BiPredicate;

@RequiredArgsConstructor
public enum Interval {

    LAST_HOUR     (0, (time, now) -> !time.isBefore(now.minusHours(1))  && !time.isAfter(now)),
    LAST_24_HOURS (1, (time, now) -> !time.isBefore(now.minusDays(1))   && !time.isAfter(now)),
    LAST_WEEK     (2, (time, now) -> !time.isBefore(now.minusWeeks(1))  && !time.isAfter(now)),
    LAST_MONTH    (3, (time, now) -> !time.isBefore(now.minusMonths(1)) && !time.isAfter(now)),
    TODAY         (4, (time, now) -> {
        ZonedDateTime startOfDay = now.toLocalDate().atStartOfDay(now.getZone());
        return !time.isBefore(startOfDay) && !time.isAfter(now);
    }),
    YESTERDAY     (5, (time, now) -> {
        ZonedDateTime startOfToday     = now.toLocalDate().atStartOfDay(now.getZone());
        ZonedDateTime startOfYesterday = startOfToday.minusDays(1);
        return !time.isBefore(startOfYesterday) && time.isBefore(startOfToday);
    });

    private final int code;
    private final BiPredicate<ZonedDateTime, ZonedDateTime> contains;

    public int getCode() { return code; }

    public static Interval fromCode(int code) {
        for (Interval i : values()) {
            if (i.code == code) return i;
        }
        throw new IllegalArgumentException("Unknown Interval code: " + code);
    }

    public boolean contains(ZonedDateTime time, ZonedDateTime now) {
        return contains.test(time, now);
    }

}