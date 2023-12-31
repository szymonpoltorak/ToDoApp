package razepl.dev.todoapp.config.jwt;

import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import razepl.dev.todoapp.entities.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
class JwtServiceTest {
    @Autowired
    private JwtServiceImpl jwtService;

    @Mock
    private HttpServletRequest mockRequest;

    @BeforeEach
    final void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    final void test_getUsernameFromToken_ValidToken_ReturnsUsername() {
        // given
        String token = jwtService.generateToken(User
                .builder()
                .name("john.doe")
                .username("john.doe@gmail.com")
                .password("password")
                .build());
        String expectedUsername = "john.doe@gmail.com";

        // when
        Optional<String> username = jwtService.getClaimFromToken(token, Claims::getSubject)
                .orElseThrow().describeConstable();

        // then
        assertEquals(expectedUsername, username.get());
    }

    @Test
    final void test_generateRefreshToken_ValidUserDetails_ReturnsToken() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .username("john.doe@gmail.com")
                .password("password")
                .build();

        // when
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        // then
        assertNotNull(refreshToken);
    }

    @Test
    final void test_generateToken_ValidUserDetails_ReturnsToken() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .username("john.doe@gmail.com")
                .password("password")
                .build();

        // when
        String token = jwtService.generateToken(userDetails);

        // then
        assertNotNull(token);
    }

    @Test
    final void test_isTokenValid_ValidTokenAndUserDetails_ReturnsTrue() {
        // given
        UserDetails userDetails = User
                .builder()
                .name("john.doe")
                .username("john.doe@gmail.com")
                .password("password")
                .build();
        String token = jwtService.generateToken(userDetails);

        // when
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // then
        assertTrue(isValid);
    }
}
