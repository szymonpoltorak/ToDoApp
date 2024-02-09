package razepl.dev.todoapp.config.oauth;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.config.oauth.constants.AuthProvider;
import razepl.dev.todoapp.config.oauth.data.OAuthUserImpl;
import razepl.dev.todoapp.config.oauth.interfaces.OAuthUser;
import razepl.dev.todoapp.config.oauth.interfaces.OAuthUserService;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GITHUB_FULL_NAME;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GITHUB_LOGIN;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GITHUB_TOKEN;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GOOGLE_FAMILY_NAME;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GOOGLE_LOGIN;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GOOGLE_NAME;
import static razepl.dev.todoapp.config.oauth.constants.ProvidersAttributes.GOOGLE_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuthUserServiceImpl implements OAuthUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public final <T extends OAuth2User> OAuthUser getOAuthUser(String registrationId, T oauthUser) {
        Set<String> providers = Set.of(AuthProvider.GOOGLE, AuthProvider.GITHUB);

        String newRegistrationId = registrationId.toUpperCase(Locale.ROOT);

        if (!providers.contains(newRegistrationId)) {
            throw new IllegalStateException(String.format("Sorry! Login with %s is not supported yet.", newRegistrationId));
        }
        return returnProperProviderObject(newRegistrationId, oauthUser);
    }

    @Override
    public final void updateExistingUser(User user, OAuthUser oidcUser) {
        user.setName(oidcUser.getName());
        user.setUsername(oidcUser.getUsername());

        userRepository.save(user);
    }

    @Override
    public void registerOAuthUser(OAuthUser oAuthUser) {
        User user = User
                .builder()
                .name(oAuthUser.getName())
                .surname(oAuthUser.getFamilyName())
                .username(oAuthUser.getUsername())
                .password(passwordEncoder.encode(oAuthUser.getPassword()))
                .build();
        log.info("User to be saved : {}", user);

        userRepository.save(user);
    }

    private OAuthUser returnProperProviderObject(String provider, OAuth2AuthenticatedPrincipal oAuth2User) {
        return provider.equals(AuthProvider.GOOGLE) ? buildGoogleOidcUser((OidcUser) oAuth2User) : buildGithubOAuthUser(oAuth2User);
    }

    private OAuthUser buildGithubOAuthUser(OAuth2AuthenticatedPrincipal oAuth2User) {
        Map<String, Object> attributes = oAuth2User.getAttributes();
        String[] value = attributes.get(GITHUB_FULL_NAME).toString().split(" ");
        String name = value[0];
        String familyName = value[1];
        String login = String.format("%s@github.com", attributes.get(GITHUB_LOGIN).toString());

        log.info("Full name : {}", Arrays.toString(value));
        log.error("Name : {}", name);
        log.error("FamilyName : {}", familyName);

        return OAuthUserImpl
                .builder()
                .name(name)
                .authorities(oAuth2User.getAuthorities())
                .attributes(attributes)
                .username(login)
                .birthDate(LocalDate.now())
                .password(attributes.get(GITHUB_TOKEN).toString())
                .familyName(familyName)
                .build();
    }

    private OAuthUser buildGoogleOidcUser(OidcUser oidcUser) {
        Map<String, Object> attributes = oidcUser.getAttributes();
        Optional<LocalDate> birthDate = Optional.ofNullable(oidcUser.getBirthdate()).map(LocalDate::parse);
        log.error(attributes.toString());

        return OAuthUserImpl
                .builder()
                .name(attributes.get(GOOGLE_NAME).toString())
                .userInfo(oidcUser.getUserInfo())
                .claims(oidcUser.getClaims())
                .idToken(oidcUser.getIdToken())
                .authorities(oidcUser.getAuthorities())
                .password(attributes.get(GOOGLE_TOKEN).toString())
                .attributes(attributes)
                .username(attributes.get(GOOGLE_LOGIN).toString())
                .birthDate(birthDate.orElse(LocalDate.now()))
                .familyName(attributes.get(GOOGLE_FAMILY_NAME).toString())
                .build();
    }
}
