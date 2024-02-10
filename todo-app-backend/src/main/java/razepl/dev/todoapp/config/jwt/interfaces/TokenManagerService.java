package razepl.dev.todoapp.config.jwt.interfaces;


import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.config.constants.TokenRevokeStatus;
import razepl.dev.todoapp.entities.user.User;

public interface TokenManagerService {
    AuthResponse buildTokensIntoResponse(String authToken, String refreshToken);

    AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked);

    void revokeUserTokens(User user);

    void revokeUserTokens(String username);

    void saveUsersToken(String jwtToken, String username);

    void saveUsersToken(String jwtToken, User user);
}
