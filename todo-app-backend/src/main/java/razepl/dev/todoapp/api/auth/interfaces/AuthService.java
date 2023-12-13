package razepl.dev.todoapp.api.auth.interfaces;

import jakarta.servlet.http.HttpServletRequest;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.ResetPasswordRequest;
import razepl.dev.todoapp.api.auth.data.SimpleStringResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest userRequest, HttpServletRequest request);

    AuthResponse login(LoginRequest loginRequest, HttpServletRequest request);

    AuthResponse refreshToken(String refreshToken);

    SimpleStringResponse requestResetUsersPassword(String username);

    SimpleStringResponse resetUsersPassword(ResetPasswordRequest request);
}

