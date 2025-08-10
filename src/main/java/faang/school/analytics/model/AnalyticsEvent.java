package faang.school.analytics.model;

import faang.school.analytics.model.enums.EventType;
import jakarta.persistence.*;
import lombok.*;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "analytics_event")
public class AnalyticsEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "receiver_id", nullable = false)
    private long receiverId;

    @Column(name = "actor_id", nullable = false)
    private long actorId;

    @Column(name = "subject_id")
    private Long subjectId;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type", nullable = false)
    private EventType eventType;

    @Column(name = "event_time", nullable = false)
    private ZonedDateTime eventTime;

    @Column(name = "received_at", nullable = false, insertable = false, updatable = false)
    private ZonedDateTime receivedAt;

}