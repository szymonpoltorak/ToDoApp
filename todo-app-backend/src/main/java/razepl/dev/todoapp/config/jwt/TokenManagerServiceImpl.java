package razepl.dev.todoapp.config.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.config.constants.TokenRevokeStatus;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.TokenManagerService;
import razepl.dev.todoapp.entities.token.JwtToken;
import razepl.dev.todoapp.entities.token.TokenType;
import razepl.dev.todoapp.entities.token.interfaces.TokenRepository;
import razepl.dev.todoapp.entities.user.User;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TokenManagerServiceImpl implements TokenManagerService {
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;

    @Override
    public final void saveUsersToken(String jwtToken, User user) {
        JwtToken newJwtToken = buildToken(jwtToken, user);

        tokenRepository
                .findByUser(user)
                .ifPresentOrElse(
                        token -> {
                            tokenRepository.deleteById(token.getTokenId());

                            tokenRepository.save(newJwtToken);
                        },
                        () -> tokenRepository.save(newJwtToken)
                );
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(String authToken, String refreshToken) {
        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final AuthResponse buildTokensIntoResponse(User user, TokenRevokeStatus shouldBeRevoked) {
        String authToken = jwtService.generateToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        if (shouldBeRevoked == TokenRevokeStatus.TO_REVOKE) {
            revokeUserTokens(user);
        }
        saveUsersToken(authToken, user);

        return buildResponse(authToken, refreshToken);
    }

    @Override
    public final void revokeUserTokens(User user) {
        List<JwtToken> userTokens = tokenRepository.findAllValidTokensByUserId(user.getUserId());

        if (userTokens.size() > 1) {
            throw new IllegalStateException(String.format("User '%s' has more than one valid token!", user.getUsername()));
        }
        JwtToken jwtToken = userTokens.get(0);

        jwtToken.setRevoked(true);
        jwtToken.setExpired(true);

        tokenRepository.save(jwtToken);
    }

    private AuthResponse buildResponse(String authToken, String refreshToken) {
        return AuthResponse
                .builder()
                .authToken(authToken)
                .refreshToken(refreshToken)
                .build();
    }

    private JwtToken buildToken(String jwtToken, User user) {
        return JwtToken
                .builder()
                .token(jwtToken)
                .tokenType(TokenType.JWT_BEARER_TOKEN)
                .user(user)
                .build();
    }
}
