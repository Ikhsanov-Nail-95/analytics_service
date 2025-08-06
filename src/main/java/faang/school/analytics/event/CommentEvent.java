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
public class CommentEvent {

    @NotNull
    private Long postId;
    @NotNull
    private Long commentId;
    @NotNull
    @PastOrPresent
    private ZonedDateTime commentedAt;

}