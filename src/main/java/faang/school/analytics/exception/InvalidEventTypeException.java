package faang.school.analytics.exception;

public class InvalidEventTypeException extends RuntimeException {
    public InvalidEventTypeException(String value, Throwable cause) {
        super("Unknown EventType: " + value, cause);
    }
}