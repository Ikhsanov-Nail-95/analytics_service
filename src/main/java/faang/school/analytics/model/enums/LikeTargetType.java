package faang.school.analytics.model.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Target type of the like (POST or COMMENT)")
public enum LikeTargetType {
    POST,
    COMMENT
}