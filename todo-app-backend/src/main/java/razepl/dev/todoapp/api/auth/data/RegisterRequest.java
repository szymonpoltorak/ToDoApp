package razepl.dev.todoapp.api.auth.data;

import lombok.Builder;
import razepl.dev.todoapp.entities.user.interfaces.Password;

@Builder
public record RegisterRequest(String name, String surname, String username, @Password String password) {
}
