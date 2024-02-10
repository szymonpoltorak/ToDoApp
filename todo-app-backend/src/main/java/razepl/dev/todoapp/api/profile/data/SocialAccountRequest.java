package razepl.dev.todoapp.api.profile.data;

import razepl.dev.todoapp.entities.social.SocialPlatform;

public record SocialAccountRequest(SocialPlatform socialPlatform, String socialName, String socialLink) {
}
