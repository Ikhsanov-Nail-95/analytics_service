package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.event.ProfileAppearedInSearchEvent;
import faang.school.analytics.exception.EventDeserializationException;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.ZonedDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProfileAppearedInSearchEventListenerTest {

    @Mock
    private AnalyticsEventMapper analyticsEventMapper;
    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AnalyticsEventService analyticsEventService;

    @InjectMocks
    private ProfileAppearedInSearchEventListener listener;

    @Test
    @DisplayName("Successful processing of ProfileAppearedInSearchEvent")
    void testHandleMessage_success() throws Exception {
        String json = "{\"viewerId\": 42, \"profileId\": 101, \"viewedAt\": \"2025-08-05T10:15:30\"}";
        ProfileAppearedInSearchEvent dto = ProfileAppearedInSearchEvent.builder()
                .viewedUserId(42L)
                .searchingUserId(101L)
                .appearedAt(ZonedDateTime.now())
                .build();

        AnalyticsEvent analyticsEvent = AnalyticsEvent.builder().build();

        when(objectMapper.readValue(json, ProfileAppearedInSearchEvent.class)).thenReturn(dto);
        when(analyticsEventMapper.toAnalyticsEvent(dto)).thenReturn(analyticsEvent);

        listener.handleMessage(json);

        verify(analyticsEventService).saveEvent(analyticsEvent);
    }

    @Test
    @DisplayName("Failure during JSON parsing of ProfileAppearedInSearchEvent")
    void testHandleMessage_failure_dueToParsing() throws Exception {
        String json = "invalid-json";

        when(objectMapper.readValue(json, ProfileAppearedInSearchEvent.class))
                .thenAnswer(invocation -> { throw new IOException("Malformed JSON"); });

        assertThrows(EventDeserializationException.class, () -> listener.handleMessage(json));

        verifyNoInteractions(analyticsEventMapper);
        verifyNoInteractions(analyticsEventService);
    }
}