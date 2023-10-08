package razepl.dev.todoapp.api.auth.interfaces;

import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.TokenRequest;
import razepl.dev.todoapp.api.auth.data.TokenResponse;

public interface AuthController {
    AuthResponse registerUser(RegisterRequest registerRequest);

    AuthResponse loginUser(LoginRequest loginRequest);

    AuthResponse refreshUserToken(String refreshToken);

    TokenResponse authenticateUser(TokenRequest request);
}
