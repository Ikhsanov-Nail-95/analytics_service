package faang.school.analytics.validator;

import faang.school.analytics.dto.AnalyticsEventRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class TimeRangeValidator implements ConstraintValidator<ValidTimeRange, AnalyticsEventRequest> {
    @Override
    public boolean isValid(AnalyticsEventRequest req, ConstraintValidatorContext ctx) {
        if (req.getInterval() != null) return true;
        return req.getFrom() != null && req.getTo() != null;
    }
}