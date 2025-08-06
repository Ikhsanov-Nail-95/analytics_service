package faang.school.analytics.config;

import faang.school.analytics.converter.StringToEventTypeConverter;
import faang.school.analytics.converter.StringToIntervalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final StringToEventTypeConverter eventTypeConverter;
    private final StringToIntervalConverter intervalConverter;

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(eventTypeConverter);
        registry.addConverter(intervalConverter);
    }

}