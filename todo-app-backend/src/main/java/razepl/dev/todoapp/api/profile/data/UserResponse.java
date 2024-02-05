package razepl.dev.todoapp.api.profile.data;

import lombok.Builder;

import java.util.List;

@Builder
public record UserResponse(String name, String surname, String username, List<SocialAccountResponse> socialAccounts) {
}
