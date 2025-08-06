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
public class ProfileAppearedInSearchEvent {

    @NotNull
    private Long viewedUserId;
    @NotNull
    private Long searchingUserId;
    @NotNull
    @PastOrPresent
    private ZonedDateTime appearedAt;

}