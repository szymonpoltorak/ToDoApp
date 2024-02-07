package razepl.dev.todoapp.exceptions.profile;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SocialLinkDoesNotMatchPlatformException extends ResponseStatusException {
    public SocialLinkDoesNotMatchPlatformException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
