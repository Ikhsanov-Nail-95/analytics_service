package faang.school.analytics.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FollowerEvent {

    @NotNull
    private long followerId;

    @NotNull
    private long followeeId;

    @NotNull
    @PastOrPresent
    private ZonedDateTime subscriptionDateTime;

}