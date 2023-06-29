package riders.bank.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import riders.bank.App;
import riders.bank.model.User;
import riders.bank.service.UserService;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.net.HttpHeaders.AUTHORIZATION;
import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
@Component
public class AuthUtility {
    private static String secret;
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        AuthUtility.secret = secret;
    }

    private final UserService userService;

    private String getRefreshToken(HttpServletRequest request) {
        if (request.getCookies() == null) {
            App.LOGGER.info("Cookie list is empty");
            return null;
        }

        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("refresh_token")) {
                return cookie.getValue();
            }
        }
        return null;
    }

    public static Algorithm getAlgorithm() {
        return Algorithm.HMAC512(secret.getBytes());
    }

    public static JWTVerifier getVerifier() {
        return JWT.require(getAlgorithm()).build();
    }

    public static String createAccessJWT(String subject, String claim, HttpServletRequest request) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 30 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("role", claim)
                .sign(getAlgorithm());
    }

    public static String createRefreshJWT(String subject, HttpServletRequest request) {
        return JWT.create()
                .withSubject(subject)
                .withExpiresAt(new Date(System.currentTimeMillis() + 14 * 24 * 60 * 60 * 1000))
                .withIssuer(request.getRequestURL().toString())
                .sign(getAlgorithm());
    }

    public String createAccessJWTFromRefreshToken(HttpServletRequest request) {
        String refreshToken = getRefreshToken(request);
        if (refreshToken == null) {
            App.LOGGER.info("Refresh token not provided");
            return null;
        }

        DecodedJWT decodedJWT = getVerifier().verify(refreshToken);
        String email = decodedJWT.getSubject();
        User user = userService.getUserBy(email);

        return createAccessJWT(user.getEmail(), user.getRoleAsString(), request);
    }

    public static String getEmailFromRequest(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String token = authorizationHeader.substring("Bearer ".length());
                DecodedJWT decodedJWT = getVerifier().verify(token);
                return decodedJWT.getSubject();
            } catch (Exception e) {
                App.LOGGER.info("JWT verification failed");
                return null;
            }
        }
        return null;
    }

    public static DecodedJWT getDecodedJWT(String token) {
        return getVerifier().verify(token);
    }

    public static void setResponseMessage(HttpServletResponse response, String messageKey, String messageValue) {
        Map<String, String> responseObject = new HashMap<>();
        responseObject.put(messageKey, messageValue);
        response.setContentType(APPLICATION_JSON_VALUE);

        try {
            new ObjectMapper().writeValue(response.getOutputStream(), responseObject);
        } catch (IOException e) {
            App.LOGGER.error(e.getMessage());
        }
    }
}
