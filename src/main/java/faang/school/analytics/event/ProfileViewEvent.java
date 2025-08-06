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
    private long observedId;
    @NotNull
    private long observerId;
    @NotNull
    private ZonedDateTime viewedAt;

}