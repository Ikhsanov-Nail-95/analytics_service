package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.ProfileViewEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("profile-view-event")
@Service
public class ProfileViewEventListener extends AbstractListener<ProfileViewEvent> {

    public ProfileViewEventListener(AnalyticsEventMapper analyticsEventMapper,
                                    ObjectMapper objectMapper,
                                    AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                ProfileViewEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(ProfileViewEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}