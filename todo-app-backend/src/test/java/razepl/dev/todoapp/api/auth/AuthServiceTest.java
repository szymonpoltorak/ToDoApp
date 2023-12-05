package razepl.dev.todoapp.api.auth;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.TokenRequest;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.TokenManagerService;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;
import razepl.dev.todoapp.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.todoapp.exceptions.auth.throwable.TokensUserNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class AuthServiceTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenManagerService tokenManager;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;

    private RegisterRequest registerUserRequest;

    private LoginRequest loginUserRequest;

    @BeforeEach
    final void setUp() {
        user = User.builder()
                .name("John")
                .surname("Doe")
                .username("john.doe@example.com")
                .password("hashedPassword")
                .build();

        registerUserRequest = RegisterRequest.builder()
                .name("John")
                .surname("Doe")
                .username("john.doe@example.com")
                .password("plinPword123123#?!")
                .build();

        loginUserRequest = LoginRequest.builder()
                .username("john.doe@example.com")
                .password("plainPassword")
                .build();
    }

    @Test
    final void test_refreshToken_should_return_new_tokens_if_refresh_token_is_valid() {
        // given
        String refreshToken = "refreshToken";
        String authToken = "authToken";

        when(jwtService.getUsernameFromToken(refreshToken)).thenReturn(Optional.of("john.doe@example.com"));

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(user)).thenReturn("authToken");
        when(tokenManager.buildTokensIntoResponse(anyString(), anyString())).thenReturn(AuthResponse.builder().build());

        // when
        AuthResponse authResponse = authService.refreshToken(refreshToken);

        // then
        assertNotNull(authResponse);
        verify(tokenManager).revokeUserTokens(user);
        verify(tokenManager).saveUsersToken(authToken, user);
        verify(tokenManager).buildTokensIntoResponse(authToken, refreshToken);
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_token_is_wrong() {
        // given
        String refreshToken = "refreshToken";

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_user_does_not_exist() {
        // given
        String refreshToken = "refreshToken";

        when(jwtService.getUsernameFromToken(refreshToken)).thenReturn(Optional.of("john.doe@example.com"));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        // when

        // then
        assertThrows(UsernameNotFoundException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }

    @Test
    final void test_refreshToken_should_throw_exception_if_refresh_token_is_invalid() {
        // given
        String refreshToken = "refreshToken";

        when(jwtService.getUsernameFromToken(refreshToken)).thenReturn(Optional.of("john.doe@example.com"));
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
        when(jwtService.isTokenValid(refreshToken, user)).thenReturn(false);

        // when

        // then
        assertThrows(InvalidTokenException.class, () -> authService.refreshToken(refreshToken));
        verify(tokenManager, never()).revokeUserTokens(any(User.class));
        verify(tokenManager, never()).saveUsersToken(anyString(), any(User.class));
        verify(tokenManager, never()).buildTokensIntoResponse(anyString(), anyString());
    }
}
