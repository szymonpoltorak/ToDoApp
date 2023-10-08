package razepl.dev.todoapp.api.tasks.interfaces;

import org.springframework.http.ResponseEntity;
import razepl.dev.todoapp.api.auth.data.ExceptionResponse;

@FunctionalInterface
public interface TaskExceptionHandler {
    ResponseEntity<ExceptionResponse> handleTaskDoesNotExistException(RuntimeException exception);
}
