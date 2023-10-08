package razepl.dev.todoapp.config.jwt.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    Optional<String> getUsernameFromToken(String jwtToken);

    <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> additionalClaims, UserDetails userDetails, long expiration);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);

    Optional<String> getJwtToken(HttpServletRequest request);

    String generateRefreshToken(UserDetails userDetails);

    Optional<String> getJwtRefreshToken(HttpServletRequest request);
}
