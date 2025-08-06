package faang.school.analytics.converter;

import faang.school.analytics.exception.InvalidIntervalException;
import faang.school.analytics.model.enums.Interval;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class StringToIntervalConverter implements Converter<String, Interval> {

    @Override
    public Interval convert(@Nullable String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        String s = source.trim().toUpperCase();
        // We try by the name enum (DAY, WEEK, ...)
        try {
            return Interval.valueOf(s);
        } catch (IllegalArgumentException e) {
            // We try by ordinal (0, 1, 2...)
            try {
                int code = Integer.parseInt(s);
                return Interval.fromCode(code);
            } catch (IllegalArgumentException ex) {
                throw new InvalidIntervalException("Invalid Interval: " + source, ex);
            }
        }
    }

}