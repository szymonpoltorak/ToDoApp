package razepl.dev.todoapp.config.oauth.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import razepl.dev.todoapp.config.oauth.interfaces.OAuthUser;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

@Data
@Builder
public class OAuthUserImpl implements OAuthUser {
    @Serial
    private static final long serialVersionUID = 1863879628396822003L;
    private String password;
    private String username;
    private Map<String, Object> claims;
    private String id;
    private OidcUserInfo userInfo;
    private OidcIdToken idToken;
    private Map<String, Object> attributes;
    private Collection<? extends GrantedAuthority> authorities;
    private String name;
    private String familyName;
    private LocalDate birthDate;

    @Override
    public final Map<String, Object> getAttributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public final boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public final boolean isEnabled() {
        return true;
    }

    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        throw new NotSerializableException("razepl.dev.socialappbackend.config.oauth.data.OAuthUserPrincipal");
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        throw new NotSerializableException("razepl.dev.socialappbackend.config.oauth.data.OAuthUserPrincipal");
    }
}
