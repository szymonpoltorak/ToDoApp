package razepl.dev.todoapp.config.oauth.interfaces;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import java.time.LocalDate;

public interface OAuthUser extends OidcUser, UserDetails {
    String getId();

    @Override
    String getFamilyName();

    LocalDate getBirthDate();
}

