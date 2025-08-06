package faang.school.analytics.event;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostViewEvent {

    @NotNull
    private Long postId;
    @NotNull
    private Long viewerUserId;
    @NotNull
    @PastOrPresent
    private ZonedDateTime viewedAt;

}