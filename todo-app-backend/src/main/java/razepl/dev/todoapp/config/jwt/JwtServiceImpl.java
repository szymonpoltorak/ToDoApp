package razepl.dev.todoapp.config.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import razepl.dev.todoapp.config.constants.Headers;
import razepl.dev.todoapp.config.constants.Matchers;
import razepl.dev.todoapp.config.constants.Properties;
import razepl.dev.todoapp.config.jwt.interfaces.JwtService;
import razepl.dev.todoapp.config.jwt.interfaces.RsaKeyService;
import razepl.dev.todoapp.entities.token.TokenType;
import razepl.dev.todoapp.exceptions.auth.throwable.TokenDoesNotExistException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    @Value(Properties.EXPIRATION_PROPERTY)
    private long expirationTime;

    @Value(Properties.REFRESH_PROPERTY)
    private long refreshTime;

    private final RsaKeyService rsaKeyService;

    @Override
    public final Optional<String> getUsernameFromToken(String jwtToken) {
        return getClaimFromToken(jwtToken, Claims::getSubject);
    }

    @Override
    public final <T> Optional<T> getClaimFromToken(String jwtToken, Function<Claims, T> claimsHandler) {
        Claims claims = getAllClaims(jwtToken);

        return Optional.ofNullable(claimsHandler.apply(claims));
    }

    @Override
    public final String generateRefreshToken(UserDetails userDetails) {
        return buildToken(Collections.emptyMap(), userDetails, refreshTime);
    }

    @Override
    public final String generateToken(UserDetails userDetails) {
        return generateToken(Collections.emptyMap(), userDetails, expirationTime);
    }

    @Override
    public final String generateToken(Map<String, Object> additionalClaims, UserDetails userDetails, long expiration) {
        return buildToken(additionalClaims, userDetails, expiration);
    }

    @Override
    public final boolean isTokenValid(String jwtToken, UserDetails userDetails) {
        if (!isSignatureValid(jwtToken)) {
            return false;
        }
        Optional<String> username = getUsernameFromToken(jwtToken);

        return username
                .filter(s -> s.equals(userDetails.getUsername()) && !isTokenExpired(jwtToken))
                .isPresent();
    }

    @Override
    public final Optional<String> getJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader(Headers.AUTH_HEADER);

        if (request.getServletPath().contains(Matchers.AUTH_MAPPING) || authHeader == null || !authHeader.startsWith(Headers.TOKEN_HEADER)) {
            return Optional.empty();
        }
        return Optional.of(authHeader.substring(Headers.TOKEN_START_INDEX));
    }

    private boolean isSignatureValid(String jwtToken) {
        Jws<Claims> claimsJws = parseJwsClaims(jwtToken);

        if (!claimsJws.getHeader().get("type").equals(TokenType.JWT_BEARER_TOKEN.toString())) {
            return false;
        }
        return claimsJws.getHeader().getAlgorithm().equals(SignatureAlgorithm.RS512.getValue());
    }

    private Claims getAllClaims(String token) {
        return parseJwsClaims(token).getBody();
    }

    private Jws<Claims> parseJwsClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(rsaKeyService.buildVerifyKey())
                .build()
                .parseClaimsJws(token);
    }

    private String buildToken(Map<String, Object> additionalClaims, UserDetails userDetails, long expiration) {
        long time = System.currentTimeMillis();

        return Jwts
                .builder()
                .setClaims(additionalClaims)
                .setSubject(userDetails.getUsername())
                .setHeader(Map.of("type", TokenType.JWT_BEARER_TOKEN))
                .setIssuedAt(new Date(time))
                .setExpiration(new Date(time + expiration))
                .signWith(rsaKeyService.buildSignInKey(), SignatureAlgorithm.RS512)
                .compact();
    }

    private boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    private Date getExpirationDateFromToken(String token) {
        Optional<Date> optionalDate = getClaimFromToken(token, Claims::getExpiration);

        if (optionalDate.isEmpty()) {
            throw new TokenDoesNotExistException("Token without expiration date does not exists!");
        }
        return optionalDate.get();
    }
}
