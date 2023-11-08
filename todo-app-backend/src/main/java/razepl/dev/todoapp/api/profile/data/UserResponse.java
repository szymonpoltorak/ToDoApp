package razepl.dev.todoapp.api.profile.data;

import lombok.Builder;

@Builder
public record UserResponse(String name, String surname, String username) {
}
