package faang.school.analytics.repository;

import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.enums.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

@Repository
public interface AnalyticsEventRepository extends JpaRepository<AnalyticsEvent, Long> {

    Stream<AnalyticsEvent> findByReceiverIdAndEventTypeAndEventTimeBetweenOrderByEventTimeDesc(
            long receiverId,
            EventType eventType,
            ZonedDateTime start,
            ZonedDateTime end
    );

}