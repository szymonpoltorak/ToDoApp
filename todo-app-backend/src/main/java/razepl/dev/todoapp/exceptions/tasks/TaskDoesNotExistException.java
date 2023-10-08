package razepl.dev.todoapp.exceptions.tasks;

import java.io.Serial;

public class TaskDoesNotExistException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -2951426835248142714L;

    public TaskDoesNotExistException(String message) {
        super(message);
    }
}
