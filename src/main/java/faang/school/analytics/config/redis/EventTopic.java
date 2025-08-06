package faang.school.analytics.config.redis;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EventTopic {
    String value();
}