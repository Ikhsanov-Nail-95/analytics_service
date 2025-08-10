package faang.school.analytics.event;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileViewEvent {

    @NotNull
    private Long observedId;

    @NotNull
    private Long observerId;

    @NotNull
    private ZonedDateTime viewedAt;

}