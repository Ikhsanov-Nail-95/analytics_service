package faang.school.analytics.controller;

import faang.school.analytics.dto.AnalyticsEventRequest;
import faang.school.analytics.dto.AnalyticsEventResponse;
import faang.school.analytics.service.AnalyticsEventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    private final AnalyticsEventService analyticsEventService;

    @GetMapping
    public List<AnalyticsEventResponse> getAnalytics(
            @Valid
            @ModelAttribute AnalyticsEventRequest request
    ) {
        return analyticsEventService.getAnalytics(
                request.getReceiverId(),
                request.getEventType(),
                request.getInterval(),
                request.getFrom(),
                request.getTo()
        );
    }

}