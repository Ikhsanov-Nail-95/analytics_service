package faang.school.analytics.exception;

public class InvalidIntervalException extends RuntimeException {
    public InvalidIntervalException(String value, Throwable cause) {
        super("Invalid Interval: " + value, cause);
    }
}