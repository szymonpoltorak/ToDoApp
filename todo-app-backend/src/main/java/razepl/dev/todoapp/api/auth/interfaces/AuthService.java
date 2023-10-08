package razepl.dev.todoapp.api.auth.interfaces;

import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.TokenRequest;
import razepl.dev.todoapp.api.auth.data.TokenResponse;

public interface AuthService {
    AuthResponse register(RegisterRequest userRequest);

    AuthResponse login(LoginRequest loginRequest);

    AuthResponse refreshToken(String refreshToken);

    TokenResponse validateUsersTokens(TokenRequest request);

}

