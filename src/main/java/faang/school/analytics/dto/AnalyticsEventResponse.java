package faang.school.analytics.dto;

import faang.school.analytics.model.enums.EventType;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AnalyticsEventResponse {
    private long id;
    private long receiverId;
    private long actorId;
    private EventType eventType;
    private ZonedDateTime eventTime;
}