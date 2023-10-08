package razepl.dev.todoapp.api.auth.data;

import lombok.Builder;

@Builder
public record ExceptionResponse(String errorMessage, String exceptionClassName) {
}