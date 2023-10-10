package razepl.dev.todoapp.exceptions.groups;

public class GroupDoesNotExistException extends RuntimeException {
    public GroupDoesNotExistException(String message) {
        super(message);
    }
}
