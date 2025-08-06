package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.exception.EventDeserializationException;
import faang.school.analytics.exception.EventMappingException;
import faang.school.analytics.exception.EventPersistenceException;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractListener<T> {

    protected final AnalyticsEventMapper analyticsEventMapper;
    private final Class<T> eventTypeClass;
    private final ObjectMapper objectMapper;
    private final AnalyticsEventService analyticsEventService;

    public void handleMessage(String json) {
        T dto;
        try {
            dto = objectMapper.readValue(json, eventTypeClass);
        } catch (IOException e) {
            log.error("Failed to deserialize JSON for {}: {}", eventTypeClass.getSimpleName(), json, e);
            throw new EventDeserializationException(
                    "Unable to deserialize incoming JSON for " + eventTypeClass.getSimpleName(), e);
        }

        AnalyticsEvent event;
        try {
            event = mapDtoToEvent(dto);
        } catch (Exception e) {
            log.error("Failed to map DTO to AnalyticsEvent, dto={}", dto, e);
            throw new EventMappingException(
                    "Error mapping " + dto.getClass().getSimpleName() + " to AnalyticsEvent", e);
        }

        try {
            analyticsEventService.saveEvent(event);
            log.info("Successfully saved AnalyticsEvent: {}", event);
        } catch (DataAccessException e) {
            log.error("Failed to persist AnalyticsEvent {} to database", event, e);
            throw new EventPersistenceException(
                    "Failed to persist AnalyticsEvent for " + dto.getClass().getSimpleName(), e);
        }
    }

    protected abstract AnalyticsEvent mapDtoToEvent(T dto);

}