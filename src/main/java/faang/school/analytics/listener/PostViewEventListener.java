package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.PostViewEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("post-view-event")
@Service
public class PostViewEventListener extends AbstractListener<PostViewEvent> {

    public PostViewEventListener(AnalyticsEventMapper analyticsEventMapper,
                                 ObjectMapper objectMapper,
                                 AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                PostViewEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(PostViewEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}