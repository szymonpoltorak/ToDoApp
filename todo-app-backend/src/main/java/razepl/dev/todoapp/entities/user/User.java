package razepl.dev.todoapp.entities.user;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import razepl.dev.todoapp.entities.attempts.LoginAttempt;
import razepl.dev.todoapp.entities.user.interfaces.ServiceUser;

import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serial;
import java.util.Collection;
import java.util.Collections;

import static razepl.dev.todoapp.entities.user.constants.Constants.USERS_TABLE_NAME;
import static razepl.dev.todoapp.entities.user.constants.Constants.USER_PACKAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidation.EMAIL_MAX_LENGTH;
import static razepl.dev.todoapp.entities.user.constants.UserValidation.EMAIL_MIN_LENGTH;
import static razepl.dev.todoapp.entities.user.constants.UserValidation.NAME_MAX_LENGTH;
import static razepl.dev.todoapp.entities.user.constants.UserValidation.NAME_MIN_LENGTH;
import static razepl.dev.todoapp.entities.user.constants.UserValidation.NAME_PATTERN;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.EMAIL_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.EMAIL_NULL_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.NAME_NULL_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.NAME_PATTERN_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.NAME_SIZE_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.PASSWORD_NULL_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.SURNAME_NULL_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.SURNAME_PATTERN_MESSAGE;
import static razepl.dev.todoapp.entities.user.constants.UserValidationMessages.SURNAME_SIZE_MESSAGE;


@Slf4j
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = USERS_TABLE_NAME)
public class User implements ServiceUser {
    @Serial
    private static final long serialVersionUID = 884980275324187578L;

    @NotNull(message = NAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = NAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = NAME_PATTERN_MESSAGE)
    private String name;

    @NotNull(message = SURNAME_NULL_MESSAGE)
    @Size(min = NAME_MIN_LENGTH, max = NAME_MAX_LENGTH, message = SURNAME_SIZE_MESSAGE)
    @Pattern(regexp = NAME_PATTERN, message = SURNAME_PATTERN_MESSAGE)
    private String surname;

    @NotNull(message = EMAIL_NULL_MESSAGE)
    @Column(unique = true)
    @Size(min = EMAIL_MIN_LENGTH, max = EMAIL_MAX_LENGTH)
    @Email(message = EMAIL_MESSAGE)
    private String username;

    @JsonIgnore
    @NotNull(message = PASSWORD_NULL_MESSAGE)
    private String password;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long userId;

    @OneToOne
    private LoginAttempt loginAttempt;

    @Override
    public final String getFullName() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public final Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public final boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public final boolean isAccountNonLocked() {
        return loginAttempt.isAccountNonLocked();
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
    private void readObject(ObjectInputStream in) throws ClassNotFoundException, NotSerializableException {
        throw new NotSerializableException(USER_PACKAGE);
    }

    @Serial
    private void writeObject(ObjectOutputStream out) throws NotSerializableException {
        throw new NotSerializableException(USER_PACKAGE);
    }
}
