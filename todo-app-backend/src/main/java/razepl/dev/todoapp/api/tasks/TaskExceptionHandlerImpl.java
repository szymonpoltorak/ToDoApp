package razepl.dev.todoapp.api.tasks;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import razepl.dev.todoapp.api.auth.data.ExceptionResponse;
import razepl.dev.todoapp.api.tasks.interfaces.TaskExceptionHandler;
import razepl.dev.todoapp.exceptions.tasks.TaskDoesNotExistException;

@Slf4j
@ControllerAdvice
public class TaskExceptionHandlerImpl implements TaskExceptionHandler {
    @Override
    @ExceptionHandler(TaskDoesNotExistException.class)
    public final ResponseEntity<ExceptionResponse> handleTaskDoesNotExistException(RuntimeException exception) {
        String errorMessage = exception.getMessage();
        String className = exception.getClass().getSimpleName();
        ExceptionResponse exceptionResponse = ExceptionResponse
                .builder()
                .exceptionClassName(className)
                .errorMessage(errorMessage)
                .build();

        log.error("Exception occurred with className : {}", className);
        log.error("Exception occurred with errorMessage : {}", errorMessage);

        return new ResponseEntity<>(exceptionResponse, HttpStatus.NOT_FOUND);
    }
}
