package faang.school.analytics.event;

import faang.school.analytics.model.enums.LikeTargetType;
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
public class LikeEvent {

    @NotNull
    private Long entityId;

    @NotNull
    private LikeTargetType targetType;

    @NotNull
    private Long userId;

    @NotNull
    private Long likeId;

    @NotNull
    @PastOrPresent
    private ZonedDateTime likedAt;

}