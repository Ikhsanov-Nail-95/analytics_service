package faang.school.analytics.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import faang.school.analytics.config.redis.EventTopic;
import faang.school.analytics.event.PremiumBoughtEvent;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.service.AnalyticsEventService;
import org.springframework.stereotype.Service;

@EventTopic("premium-bought-event")
@Service
public class PremiumBoughtEventListener extends AbstractListener<PremiumBoughtEvent> {

    public PremiumBoughtEventListener(AnalyticsEventMapper analyticsEventMapper,
                                      ObjectMapper objectMapper,
                                      AnalyticsEventService analyticsEventService) {
        super(analyticsEventMapper,
                PremiumBoughtEvent.class,
                objectMapper,
                analyticsEventService);
    }

    @Override
    protected AnalyticsEvent mapDtoToEvent(PremiumBoughtEvent dto) {
        return analyticsEventMapper.toAnalyticsEvent(dto);
    }

}