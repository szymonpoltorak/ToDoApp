package razepl.dev.todoapp.api.auth.interfaces;

import razepl.dev.todoapp.api.auth.data.LoginRequest;
import razepl.dev.todoapp.api.auth.data.RegisterRequest;
import razepl.dev.todoapp.api.auth.data.ResetPasswordRequest;
import razepl.dev.todoapp.entities.attempts.LoginAttempt;
import razepl.dev.todoapp.entities.user.User;

public interface AuthHelperService {
    User buildRequestIntoUser(RegisterRequest registerRequest, LoginAttempt loginAttempt);

    void executeUserAuthenticationProcess(LoginAttempt loginAttempt, LoginRequest loginRequest);

    void executePasswordResetProcess(ResetPasswordRequest request, User user);

    void savePasswordResetToken(String passwordRefreshToken, User user);
}
