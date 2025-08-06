package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.LikeEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("like-event")
@Service
public class LikeEventListener extends AbstractListener<LikeEvent> {

    public LikeEventListener(AnalyticsEventMapper analyticsEventMapper,
                             ObjectMapper objectMapper,
                             AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                LikeEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(LikeEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}