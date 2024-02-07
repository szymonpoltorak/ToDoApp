package razepl.dev.todoapp.exceptions.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SocialAccountDoesNotExistException extends ResponseStatusException {
    public SocialAccountDoesNotExistException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
