package razepl.dev.todoapp.exceptions.auth.throwable;

import java.io.Serial;

public class TokenDoesNotExistException extends IllegalArgumentException {
    @Serial
    private static final long serialVersionUID = -3677127531617118398L;

    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
