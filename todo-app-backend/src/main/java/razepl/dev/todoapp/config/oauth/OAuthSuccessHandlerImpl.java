package razepl.dev.todoapp.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.TokenManagerService;
import razepl.dev.todoapp.config.oauth.constants.RedirectUrls;
import razepl.dev.todoapp.config.oauth.interfaces.OAuthSuccessHandler;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuthSuccessHandlerImpl extends SimpleUrlAuthenticationSuccessHandler implements OAuthSuccessHandler {
    private static final int BUILDER_CAPACITY = 200;
    private final JwtService jwtService;
    private final TokenManagerService tokenManager;
    @Value(RedirectUrls.FRONTEND_URL_VALUE)
    private String frontendUrl;

    @Override
    public final void onAuthenticationSuccess(HttpServletRequest request,
                                              HttpServletResponse response,
                                              Authentication authentication) throws IOException {
        handle(request, response, authentication);

        super.clearAuthenticationAttributes(request);
    }

    @Override
    public final void handle(HttpServletRequest request,
                             HttpServletResponse response,
                             Authentication authentication) throws IOException {
        log.info("Principal : {}", authentication.getPrincipal());

        StringBuilder redirectBuilder = new StringBuilder(BUILDER_CAPACITY);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        String authToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);
        String redirectUrl = String.format("%s%s", frontendUrl, RedirectUrls.SUCCESS_URL);

        redirectBuilder
                .append(redirectUrl)
                .append("?")
                .append("authToken=").append(authToken).append("&")
                .append("refreshToken=").append(refreshToken);

        tokenManager.revokeUserTokens(userDetails.getUsername());
        tokenManager.saveUsersToken(authToken, userDetails.getUsername());

        response.sendRedirect(redirectBuilder.toString());
    }
}
