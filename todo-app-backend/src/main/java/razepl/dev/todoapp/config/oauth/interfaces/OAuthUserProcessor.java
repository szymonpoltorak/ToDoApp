package razepl.dev.todoapp.config.oauth.interfaces;

import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.user.OAuth2User;

@FunctionalInterface
public interface OAuthUserProcessor<R extends OAuth2UserRequest, U extends OAuth2User> {
    OAuthUser processOAuthUserRegistration(R userRequest, U oAuth2User);
}
