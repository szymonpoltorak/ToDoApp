package razepl.dev.todoapp.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.ResetPasswordRequest;
import razepl.dev.todoapp.api.auth.data.SimpleStringResponse;

public interface AuthController {
    AuthResponse registerUser(RegisterRequest registerRequest, HttpServletRequest request);

    AuthResponse loginUser(LoginRequest loginRequest, HttpServletRequest request);

    AuthResponse refreshUserToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);
}
