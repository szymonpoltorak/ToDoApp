package razepl.dev.todoapp.config.jwt.interfaces;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public interface JwtService {
    <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler);

    String generateToken(UserDetails userDetails);

    String generateToken(Map<String, Object> additionalClaims, UserDetails userDetails, long expiration);

    boolean isTokenValid(String jwtToken, UserDetails userDetails);

    Optional<String> getJwtTokenFromRequest(HttpServletRequest request);

    String generateRefreshToken(UserDetails userDetails);
}
