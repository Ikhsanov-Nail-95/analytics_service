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
public class PremiumBoughtEvent {

    @NotNull
    private Long userId;

    @NotNull
    @PastOrPresent
    private ZonedDateTime purchasedAt;

}