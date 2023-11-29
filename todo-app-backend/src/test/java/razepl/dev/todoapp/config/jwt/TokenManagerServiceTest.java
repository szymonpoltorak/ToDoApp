package razepl.dev.todoapp.config.jwt;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import razepl.dev.todoapp.api.auth.data.AuthResponse;
import razepl.dev.todoapp.config.constants.TokenRevokeStatus;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.TokenManagerService;
import razepl.dev.todoapp.entities.token.JwtToken;
import razepl.dev.todoapp.entities.token.interfaces.TokenRepository;
import razepl.dev.todoapp.entities.user.User;
import razepl.dev.todoapp.entities.user.interfaces.UserRepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
class TokenManagerServiceTest {
    @Mock
    private TokenRepository mockTokenRepository;

    @Mock
    private JwtService mockJwtService;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private TokenManagerService tokenManagerService;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenManagerService = new TokenManagerServiceImpl(mockTokenRepository, mockJwtService);
    }

    @Test
    final void saveUsersToken_ValidTokenAndUser_TokenSavedToRepository() {
        // given
        String jwtToken = "valid-token";
        User user = new User();

        // when
        tokenManagerService.saveUsersToken(jwtToken, user);

        // then
        verify(mockTokenRepository).save(any(JwtToken.class));
    }

    @Test
    final void buildTokensIntoResponse_AuthTokenAndRefreshToken_ReturnsAuthResponse() {
        // given
        String authToken = "auth-token";
        String refreshToken = "refresh-token";

        // when
        AuthResponse authResponse = tokenManagerService.buildTokensIntoResponse(authToken, refreshToken);

        // then
        assertNotNull(authResponse);
        assertEquals(authToken, authResponse.authToken());
        assertEquals(refreshToken, authResponse.refreshToken());
    }

    @Test
    final void buildTokensIntoResponse_UserAndShouldRevokeTokens_ReturnsAuthResponse() {
        // given
        User user = new User();
        String authToken = "auth-token";
        String refreshToken = "refresh-token";

        // when
        when(mockJwtService.generateToken(user)).thenReturn(authToken);
        when(mockJwtService.generateRefreshToken(user)).thenReturn(refreshToken);

        AuthResponse authResponse = tokenManagerService.buildTokensIntoResponse(user, TokenRevokeStatus.TO_REVOKE);

        // then
        assertNotNull(authResponse);
        assertEquals(authToken, authResponse.authToken());
        assertEquals(refreshToken, authResponse.refreshToken());

        verify(mockJwtService).generateToken(user);
        verify(mockJwtService).generateRefreshToken(user);
        verify(mockTokenRepository).save(any(JwtToken.class));
    }

    @Test
    final void revokeUserTokens_ValidUser_TokensRevokedAndSavedToRepository() {
        // given
        User user = new User();
        JwtToken token1 = new JwtToken();
        JwtToken token2 = new JwtToken();
        List<JwtToken> userTokens = new ArrayList<>(100);

        userTokens.add(token1);
        userTokens.add(token2);

        // when
        when(mockTokenRepository.findAllValidTokensByUserId(user.getUserId())).thenReturn(userTokens);

        tokenManagerService.revokeUserTokens(user);

        // then
        assertTrue(token1.isRevoked());
        assertTrue(token1.isExpired());
        assertTrue(token2.isRevoked());
        assertTrue(token2.isExpired());

        verify(mockTokenRepository).findAllValidTokensByUserId(user.getUserId());
        verify(mockTokenRepository).saveAll(userTokens);
    }

    @Test
    final void revokeUserTokens_ValidUser_NoTokensToRevoke() {
        // given
        User user = new User();

        // when
        when(mockTokenRepository.findAllValidTokensByUserId(user.getUserId()))
                .thenReturn(new ArrayList<>(100));

        tokenManagerService.revokeUserTokens(user);

        // then
        verify(mockTokenRepository).findAllValidTokensByUserId(user.getUserId());
        verify(mockTokenRepository, never()).saveAll(anyList());
    }
}
