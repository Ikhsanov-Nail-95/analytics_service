package faang.school.analytics.converter;

import faang.school.analytics.exception.InvalidEventTypeException;
import faang.school.analytics.model.enums.EventType;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToEventTypeConverter implements Converter<String, EventType> {

    @Override
    public EventType convert(String source) {
        try {
            return EventType.valueOf(source.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidEventTypeException(source, e);
        }
    }

}