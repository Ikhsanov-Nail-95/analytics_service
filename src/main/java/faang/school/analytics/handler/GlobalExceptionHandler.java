package faang.school.analytics.handler;

import faang.school.analytics.exception.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.net.URI;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String EXCEPTION = "exception";
    private static final String PATH = "path";

    @ExceptionHandler(EventDeserializationException.class)
    public ProblemDetail handleEventDeserializationException(EventDeserializationException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Event Deserialization Error",
                ex.getMessage(),
                "/api/v1/errors/event-deserialization",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EventMappingException.class)
    public ProblemDetail handleEventMappingException(EventMappingException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.UNPROCESSABLE_ENTITY,
                "Event Mapping Error",
                ex.getMessage(),
                "/api/v1/errors/event-mapping",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(EventPersistenceException.class)
    public ProblemDetail handleEventPersistenceException(EventPersistenceException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Analytics Event Persistence Error",
                ex.getMessage(),
                "/api/v1/errors/event-persistence",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MissingRedisTopicException.class)
    public ProblemDetail handleMissingRedisTopicException(MissingRedisTopicException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Missing Redis Topic",
                ex.getMessage(),
                "/api/v1/errors/missing-redis-topic",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidEventTypeException.class)
    public ProblemDetail handleInvalidEventTypeException(InvalidEventTypeException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid Event Type",
                ex.getMessage(),
                "/api/v1/errors/event-type",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(InvalidIntervalException.class)
    public ProblemDetail handleInvalidIntervalException(InvalidIntervalException ex, HttpServletRequest request) {
        return buildProblemDetail(
                HttpStatus.BAD_REQUEST,
                "Invalid Interval",
                ex.getMessage(),
                "/api/v1/errors/interval",
                ex.getClass().getSimpleName(),
                request.getRequestURI()
        );
    }

        @ExceptionHandler(Exception.class)
    public ProblemDetail handleGenericException(Exception ex, HttpServletRequest request) {
        ProblemDetail problem = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);

        problem.setTitle("Internal Server Error");
        problem.setDetail(ex.getMessage());
        problem.setProperty(EXCEPTION, ex.getClass().getSimpleName());
        problem.setProperty(PATH, request.getRequestURI());

        return problem;
    }

    private ProblemDetail buildProblemDetail(
            HttpStatus status,
            String title,
            String detail,
            String instanceUri,
            String exceptionName,
            String path
    ) {
        ProblemDetail problem = ProblemDetail.forStatus(status);

        problem.setTitle(title);
        problem.setDetail(detail);
        problem.setProperty(EXCEPTION, exceptionName);
        problem.setInstance(URI.create(instanceUri));
        problem.setProperty(PATH, path);

        return problem;
    }

}