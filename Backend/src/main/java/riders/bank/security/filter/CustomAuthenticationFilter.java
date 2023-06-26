package riders.bank.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import riders.bank.App;
import riders.bank.dto.LoginBodyDTO;
import riders.bank.security.AuthUtility;

import java.io.IOException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        ObjectMapper objectMapper = new ObjectMapper();
        LoginBodyDTO loginBodyDTO = new LoginBodyDTO();
        try {
            loginBodyDTO = objectMapper.readValue(request.getReader(), LoginBodyDTO.class);
        } catch (IOException e) {
            App.LOGGER.error(e.getMessage());
        }

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginBodyDTO.getEmail(), loginBodyDTO.getPassword());

        return authenticationManager.authenticate(authenticationToken);
    }



    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) {
        User user = (User)authResult.getPrincipal();
        String role =  user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst()
                .orElse(null);

        String refreshToken = AuthUtility.createRefreshJWT(user.getUsername(), request);
        Cookie refreshTokenCookie = new Cookie("refresh_token", refreshToken);
        refreshTokenCookie.setMaxAge(14 * 24 * 60 * 60);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setDomain("localhost");
        refreshTokenCookie.setPath("/");

        String accessToken = AuthUtility.createAccessJWT(user.getUsername(), role, request);

        response.addCookie(refreshTokenCookie);
        AuthUtility.setResponseMessage(response, "access_token", accessToken);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) {
        response.setStatus(UNAUTHORIZED.value());
        AuthUtility.setResponseMessage(response, "error", "incorrect email or password");
    }
}
