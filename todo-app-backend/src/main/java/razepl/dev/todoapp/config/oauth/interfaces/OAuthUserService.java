package razepl.dev.todoapp.config.oauth.interfaces;

import org.springframework.security.oauth2.core.user.OAuth2User;
import razepl.dev.todoapp.entities.user.User;

public interface OAuthUserService {
    <T extends OAuth2User> OAuthUser getOAuthUser(String registrationId, T oauthUser);

    void updateExistingUser(User user, OAuthUser oidcUser);

    void registerOAuthUser(OAuthUser oidcUser);
}

