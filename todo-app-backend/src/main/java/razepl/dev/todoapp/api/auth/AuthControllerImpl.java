package razepl.dev.todoapp.api.auth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import razepl.dev.todoapp.api.auth.constants.AuthMappings;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.ResetPasswordRequest;
import razepl.dev.todoapp.api.auth.interfaces.AuthController;
import razepl.dev.todoapp.api.auth.interfaces.AuthService;

import static razepl.dev.todoapp.config.constants.Matchers.AUTH_MAPPING;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = AUTH_MAPPING)
public class AuthControllerImpl implements AuthController {
    private final AuthService authService;

    @Override
    @PostMapping(value = AuthMappings.REGISTER_MAPPING)
    @ResponseStatus(value = HttpStatus.CREATED)
    public final AuthResponse registerUser(@Valid @RequestBody RegisterRequest registerRequest,
                                           HttpServletRequest request) {
        return authService.register(registerRequest, request);
    }

    @Override
    @PostMapping(value = AuthMappings.LOGIN_MAPPING)
    public final AuthResponse loginUser(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        return authService.login(loginRequest, request);
    }

    @Override
    @PostMapping(value = AuthMappings.REFRESH_MAPPING)
    public final AuthResponse refreshUserToken(@RequestParam String refreshToken) {
        return authService.refreshToken(refreshToken);
    }

    @Override
    @PostMapping(value = AuthMappings.REQUEST_RESET_PASSWORD_MAPPING)
    public final String requestResetUsersPassword(@RequestParam String username) {
        return authService.requestResetUsersPassword(username);
    }

    @Override
    @PostMapping(value = AuthMappings.RESET_PASSWORD_MAPPING)
    public final String resetUsersPassword(@Valid @RequestBody ResetPasswordRequest request) {
        return authService.resetUsersPassword(request);
    }
}
