package razepl.dev.todoapp.api.auth;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.ResetPasswordRequest;
import razepl.dev.todoapp.api.auth.interfaces.AuthService;
import razepl.dev.todoapp.api.auth.interfaces.LoginDeviceHandler;
import razepl.dev.todoapp.config.constants.TokenRevokeStatus;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.TokenManagerService;
import razepl.dev.todoapp.entities.attempts.LoginAttempt;
import razepl.dev.todoapp.entities.attempts.interfaces.LoginAttemptRepository;
import razepl.dev.todoapp.entities.token.JwtToken;
import razepl.dev.todoapp.entities.token.TokenType;
import razepl.dev.todoapp.entities.token.interfaces.TokenRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;
import razepl.dev.todoapp.exceptions.auth.throwable.InvalidTokenException;
import razepl.dev.todoapp.exceptions.auth.throwable.TokenDoesNotExistException;
import razepl.dev.todoapp.exceptions.auth.throwable.UserAlreadyExistsException;

import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final TokenRepository tokenRepository;
    private static final String USER_NOT_EXIST_MESSAGE = "Such user does not exist!";
    private static final String BUILDING_TOKEN_RESPONSE_MESSAGE = "Building token response for user : {}";
    private static final long PASSWORD_REFRESH_TOKEN_TIME = 600_000L;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenManagerService tokenManager;
    private final LoginAttemptRepository loginAttemptRepository;
    private final LoginDeviceHandler loginDeviceFilter;
    private final JwtService jwtService;

    @Override
    public final AuthResponse register(RegisterRequest registerRequest, HttpServletRequest request) {
        log.info("Registering user with data: \n{}", registerRequest);

        String password = validateUserRegisterData(registerRequest);

        LoginAttempt loginAttempt = LoginAttempt
                .builder()
                .attempts(0L)
                .dateOfLock(LocalTime.MIN)
                .build();
        loginAttemptRepository.save(loginAttempt);

        User user = User
                .builder()
                .name(registerRequest.name())
                .username(registerRequest.username())
                .surname(registerRequest.surname())
                .password(passwordEncoder.encode(password))
                .loginAttempt(loginAttempt)
                .build();
        User newUser = userRepository.save(user);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(newUser, request);

        log.info(BUILDING_TOKEN_RESPONSE_MESSAGE, newUser);

        return tokenManager.buildTokensIntoResponse(newUser, TokenRevokeStatus.NOT_TO_REVOKE);
    }

    @Override
    public final AuthResponse login(LoginRequest loginRequest, HttpServletRequest request) {
        log.info("Logging user with data: \n{}", loginRequest);

        String username = loginRequest.username();

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        if (!user.isAccountNonLocked()) {
            log.error("User is locked! User : {}", user);

            throw new UsernameNotFoundException("User is locked!");
        }
        LoginAttempt loginAttempt = user.getLoginAttempt();

        executeUserAuthenticationProcess(loginAttempt, loginRequest);

        loginDeviceFilter.addNewDeviceToUserLoggedInDevices(user, request);

        log.info(BUILDING_TOKEN_RESPONSE_MESSAGE, user);

        return tokenManager.buildTokensIntoResponse(user, TokenRevokeStatus.TO_REVOKE);
    }

    @Override
    public final AuthResponse refreshToken(String refreshToken) {
        log.info("Refresh token : {}", refreshToken);

        User user = validateRefreshTokenData(refreshToken);
        String authToken = jwtService.generateToken(user);

        log.info("New auth token : {}\nFor user : {}", authToken, user);

        tokenManager.revokeUserTokens(user);

        tokenManager.saveUsersToken(authToken, user);

        return tokenManager.buildTokensIntoResponse(authToken, refreshToken);
    }

    @Override
    public final String requestResetUsersPassword(String username) {
        log.info("Resetting password for user : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to gain refresh link : {}", user);

        String passwordRefreshToken = jwtService.generateToken(Collections.emptyMap(),
                user, PASSWORD_REFRESH_TOKEN_TIME);

        log.info("Password refresh link : https://localhost:4200/auth/resetPassword?token={}", passwordRefreshToken);

        JwtToken jwtToken = JwtToken
                .builder()
                .token(passwordRefreshToken)
                .user(user)
                .tokenType(TokenType.RESET_PASSWORD_TOKEN)
                .build();
        log.info("Token to save : {}", jwtToken);

        tokenRepository.save(jwtToken);

        return username;
    }

    @Override
    public final String resetUsersPassword(ResetPasswordRequest request) {
        log.info("Resetting password for user with token : {}", request.resetPasswordToken());

        User user = userRepository.findByUsername(request.username()).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );
        log.info("User to reset password : {}", user);

        JwtToken jwtToken = tokenRepository.findByTokenAndUser(request.resetPasswordToken(), user)
                .orElseThrow(
                        () -> new TokenDoesNotExistException("Token does not exist!")
                );
        log.info("Token to reset password : {}", jwtToken);

        String encodedPassword = passwordEncoder.encode(request.newPassword());

        log.info("Setting new password : {}", encodedPassword);

        user.setPassword(encodedPassword);

        log.info("Saving new user data and deleting token : {}", jwtToken);

        userRepository.save(user);

        tokenRepository.deleteById(jwtToken.getTokenId());

        return request.username();
    }

    private void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest) {
        try {
            loginAttempt.incrementAttempts();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    loginRequest.username(), loginRequest.password())
            );
            loginAttempt.resetAttempts();

        } catch (AuthenticationException e) {
            log.error("User login failed! User : {}", loginRequest.username());

            throw new UsernameNotFoundException("User login failed!", e);
        } finally {
            loginAttemptRepository.save(loginAttempt);
        }
    }

    private String validateUserRegisterData(RegisterRequest registerRequest) {
        String password = registerRequest.password();

        Optional<User> existingUser = userRepository.findByUsername(registerRequest.username());

        existingUser.ifPresent(user -> {
            log.error("User already exists! Found user: {}", user);

            throw new UserAlreadyExistsException("User already exists!");
        });
        return password;
    }

    private User validateRefreshTokenData(String refreshToken) {
        if (refreshToken == null) {
            throw new TokenDoesNotExistException("Token does not exist!");
        }
        Optional<String> usernameOptional = jwtService.getClaimFromToken(refreshToken, Claims::getSubject);

        if (usernameOptional.isEmpty()) {
            throw new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE);
        }
        String username = usernameOptional.get();

        log.info("User of username : {}", username);

        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException(USER_NOT_EXIST_MESSAGE)
        );

        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Token is not valid!");
        }
        return user;
    }
}
