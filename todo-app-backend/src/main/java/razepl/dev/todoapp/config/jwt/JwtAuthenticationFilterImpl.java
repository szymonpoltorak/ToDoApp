package razepl.dev.todoapp.config.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import razepl.dev.todoapp.config.jwt.interfaces.JwtAuthenticationFilter;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.entities.token.interfaces.TokenRepository;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilterImpl extends OncePerRequestFilter implements JwtAuthenticationFilter {
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public final void doFilterInternal(@NonNull HttpServletRequest request,
                                       @NonNull HttpServletResponse response,
                                       @NonNull FilterChain filterChain) throws ServletException, IOException {
        Optional<String> token = jwtService.getJwtToken(request);

        token.ifPresent(authToken -> {
            Optional<String> usernameOptional = jwtService.getUsernameFromToken(authToken);

            usernameOptional.ifPresent(username -> setTokenForNotAuthenticatedUser(authToken, username, request));
        });

        filterChain.doFilter(request, response);
    }

    private void setTokenForNotAuthenticatedUser(String jwtToken, String username, @NonNull HttpServletRequest request) {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            return;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        boolean isTokenValid = tokenRepository.findByToken(jwtToken)
                .map(token -> !token.isExpired() && !token.isRevoked()).orElse(false);

        if (jwtService.isTokenValid(jwtToken, userDetails) && isTokenValid) {
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.getAuthorities()
            );
            authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }
    }
}
