package faang.school.analytics.exception;

public class EventPersistenceException extends RuntimeException {
    public EventPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }
}