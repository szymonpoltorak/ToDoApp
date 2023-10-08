package razepl.dev.todoapp.api.auth.data;

import lombok.Builder;

@Builder
public record LoginRequest(String username, String password) {
}
