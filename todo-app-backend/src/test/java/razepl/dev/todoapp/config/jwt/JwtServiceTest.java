package razepl.dev.todoapp.config.jwt;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import razepl.dev.todoapp.entities.user.User;

import java.time.LocalDate;
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
                .dateOfBirth(LocalDate.now())
                .build());
        String expectedUsername = "john.doe@gmail.com";

        // when
        Optional<String> username = jwtService.getUsernameFromToken(token).orElseThrow().describeConstable();

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
                .dateOfBirth(LocalDate.now())
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
                .dateOfBirth(LocalDate.now())
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
                .dateOfBirth(LocalDate.now())
                .build();
        String token = jwtService.generateToken(userDetails);

        // when
        boolean isValid = jwtService.isTokenValid(token, userDetails);

        // then
        assertTrue(isValid);
    }

    @Test
    final void test_getJwtRefreshToken_ValidHttpServletRequest_ReturnsToken() {
        // given
        String authHeader = "Bearer valid-refresh-token";

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(authHeader);

        String refreshToken = jwtService.getJwtRefreshToken(mockRequest).orElse("");

        // then
        assertEquals("valid-refresh-token", refreshToken);
    }

    @Test
    final void test_getJwtRefreshToken_InvalidHttpServletRequest_ReturnsNull() {
        // given

        // when
        when(mockRequest.getHeader("Authorization")).thenReturn(null);

        String refreshToken = jwtService.getJwtRefreshToken(mockRequest).orElse(null);

        // then
        assertNull(refreshToken);
    }
}
