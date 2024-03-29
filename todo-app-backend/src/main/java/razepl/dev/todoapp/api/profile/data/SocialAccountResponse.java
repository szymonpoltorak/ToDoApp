package razepl.dev.todoapp.api.profile.data;

import lombok.Builder;

@Builder
public record SocialAccountResponse(String socialName, String socialLink, String socialPlatform, long socialAccountId) {
}
