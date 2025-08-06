package faang.school.analytics.service;

import faang.school.analytics.dto.AnalyticsEventResponse;
import faang.school.analytics.mapper.AnalyticsEventMapper;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.enums.EventType;
import faang.school.analytics.model.enums.Interval;
import faang.school.analytics.repository.AnalyticsEventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Service
public class AnalyticsEventService {

    private final AnalyticsEventRepository repository;
    private final AnalyticsEventMapper mapper;

    @Transactional
    public void saveEvent(AnalyticsEvent analyticsEvent) {
        repository.save(analyticsEvent);
    }

    @Transactional(readOnly = true)
    public List<AnalyticsEventResponse> getAnalytics(
            long receiverId,
            EventType eventType,
            Interval interval,
            ZonedDateTime from,
            ZonedDateTime to
    ) {
        ZonedDateTime now = ZonedDateTime.now();
        ZonedDateTime start;
        ZonedDateTime end;

        if (interval != null) {
            start = switch (interval) {
                case LAST_HOUR -> now.minusHours(1);
                case LAST_24_HOURS -> now.minusHours(24);
                case LAST_WEEK -> now.minusWeeks(1);
                case LAST_MONTH -> now.minusMonths(1);
                case TODAY -> now.toLocalDate().atStartOfDay(now.getZone());
                case YESTERDAY -> now.toLocalDate().atStartOfDay(now.getZone()).minusDays(1);
            };
            end = now;
        } else {
            start = from;
            end = to;
        }

        try (Stream<AnalyticsEvent> stream =
                     repository.findByReceiverIdAndEventTypeAndEventTimeBetweenOrderByEventTimeDesc(
                             receiverId, eventType, start, end)) {
            return stream
                    .map(mapper::toDto)
                    .toList();
        }
    }

}