package faang.school.analytics.mapper;

import faang.school.analytics.dto.AnalyticsEventResponse;
import faang.school.analytics.event.*;
import faang.school.analytics.model.AnalyticsEvent;
import faang.school.analytics.model.enums.EventType;
import faang.school.analytics.model.enums.LikeTargetType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AnalyticsEventMapper {

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "postId",                 target = "receiverId")
    @Mapping(source = "commentId",              target = "actorId")
    @Mapping(source = "commentedAt",            target = "eventTime")
    @Mapping(target = "eventType",              constant = "POST_COMMENT")
    AnalyticsEvent toAnalyticsEvent(CommentEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "followerId",             target = "receiverId")
    @Mapping(source = "followeeId",             target = "actorId")
    @Mapping(source = "subscriptionDateTime",   target = "eventTime")
    @Mapping(target = "eventType",              constant = "FOLLOWER")
    AnalyticsEvent toAnalyticsEvent(FollowerEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "entityId",               target = "receiverId")
    @Mapping(source = "likeId",                 target = "actorId")
    @Mapping(source = "likedAt",                target = "eventTime")
    @Mapping(source = "targetType",             target = "eventType")
    AnalyticsEvent toAnalyticsEvent(LikeEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "postId",                 target = "receiverId")
    @Mapping(source = "viewerUserId",           target = "actorId")
    @Mapping(source = "viewedAt",               target = "eventTime")
    @Mapping(target = "eventType",              constant = "POST_VIEW")
    AnalyticsEvent toAnalyticsEvent(PostViewEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "userId",                 target = "receiverId")
    @Mapping(source = "userId",                 target = "actorId")
    @Mapping(source = "purchasedAt",            target = "eventTime")
    @Mapping(target = "eventType",              constant = "PREMIUM_BOUGHT")
    AnalyticsEvent toAnalyticsEvent(PremiumBoughtEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "viewedUserId",           target = "receiverId")
    @Mapping(source = "searchingUserId",        target = "actorId")
    @Mapping(source = "appearedAt",             target = "eventTime")
    @Mapping(target = "eventType",              constant = "PROFILE_APPEARED_IN_SEARCH")
    AnalyticsEvent toAnalyticsEvent(ProfileAppearedInSearchEvent dto);

    @Mapping(target = "id",                     ignore = true)
    @Mapping(target = "receivedAt",             ignore = true)
    @Mapping(source = "observedId",             target = "actorId")
    @Mapping(source = "observerId",             target = "receiverId")
    @Mapping(source = "viewedAt",               target = "eventTime")
    @Mapping(target = "eventType",              constant = "PROFILE_VIEW")
    AnalyticsEvent toAnalyticsEvent(ProfileViewEvent dto);

    AnalyticsEventResponse toDto(AnalyticsEvent event);

    default EventType map(LikeTargetType targetType) {
        return switch (targetType) {
            case POST -> EventType.POST_LIKE;
            case COMMENT -> EventType.COMMENT_LIKE;
            default -> throw new IllegalArgumentException("Unknown LikeTargetType: " + targetType);
        };
    }

}