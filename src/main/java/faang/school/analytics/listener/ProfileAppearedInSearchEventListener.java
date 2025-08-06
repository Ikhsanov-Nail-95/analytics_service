package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.ProfileAppearedInSearchEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("profile-appeared-in-search-event")
@Service
public class ProfileAppearedInSearchEventListener extends AbstractListener<ProfileAppearedInSearchEvent> {

    public ProfileAppearedInSearchEventListener(AnalyticsEventMapper analyticsEventMapper,
                                                ObjectMapper objectMapper,
                                                AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                ProfileAppearedInSearchEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(ProfileAppearedInSearchEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}