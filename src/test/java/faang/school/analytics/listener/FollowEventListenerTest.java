package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.event.FollowerEvent;
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

import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FollowerEventListenerTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private AnalyticsEventMapper analyticsEventMapper;
    @Mock
    private AnalyticsEventService analyticsEventService;

    @InjectMocks
    private FollowerEventListener followerEventListener;

    @Test
    @DisplayName("Successful processing of the followerEvent")
    void testHandleMessage_success() throws IOException {
        String json = "{\"followerId\": 123, \"followeeId\": 456}";
        FollowerEvent mockEvent = new FollowerEvent(); // заполни, если нужно
        AnalyticsEvent mappedEvent = new AnalyticsEvent(); // тоже можно заполнить

        when(objectMapper.readValue(json, FollowerEvent.class)).thenReturn(mockEvent);
        when(analyticsEventMapper.toAnalyticsEvent(mockEvent)).thenReturn(mappedEvent);

        followerEventListener.handleMessage(json);

        verify(analyticsEventService).saveEvent(mappedEvent);
    }

    @Test
    @DisplayName("Unsuccessful processing: error during parsing JSON")
    void testHandleMessage_failure_dueToParsing() throws IOException {
        String json = "invalid-json";

        when(objectMapper.readValue(json, FollowerEvent.class))
                .thenAnswer(invocation -> { throw new IOException("Bad JSON"); });

        assertThrows(EventDeserializationException.class, () -> followerEventListener.handleMessage(json));

        verifyNoInteractions(analyticsEventMapper);
        verifyNoInteractions(analyticsEventService);
    }
}