package razepl.dev.todoapp.config.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import razepl.dev.todoapp.config.oauth.constants.RedirectUrls;

import java.io.IOException;

@Slf4j
@Component
public class OAuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Value(RedirectUrls.FRONTEND_URL_VALUE)
    private String frontendUrl;

    @Override
    public final void onAuthenticationFailure(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException exception) throws IOException {
        String redirectUrl = String.format("%s%s", frontendUrl, RedirectUrls.FAILURE_URL);

        log.error("OAuth failure login. Redirecting to : {}", redirectUrl);

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}
