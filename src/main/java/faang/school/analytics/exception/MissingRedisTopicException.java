package faang.school.analytics.exception;

public class MissingRedisTopicException extends IllegalStateException {
    public MissingRedisTopicException(String key) {
        super("No Redis topic defined for key: " + key);
    }
}