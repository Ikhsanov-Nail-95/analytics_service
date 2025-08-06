package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.CommentEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("comment-event")
@Service
public class CommentEventListener extends AbstractListener<CommentEvent> {

    public CommentEventListener(AnalyticsEventMapper analyticsEventMapper,
                                ObjectMapper objectMapper,
                                AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                CommentEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(CommentEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}