package riders.bank.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import riders.bank.App;
import riders.bank.dto.RegisterBodyDTO;
import riders.bank.exception.EmailExistsException;
import riders.bank.exception.ObjectMappingException;
import riders.bank.security.AuthUtility;
import riders.bank.service.UserService;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpStatus.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {
    private final UserService userService;
    private final AuthUtility authUtility;

    @PostMapping("/register")
    public ResponseEntity<?> Register(@RequestBody RegisterBodyDTO registerBodyDTO) {
        Map<String, String> responseObject = new HashMap<>();
        try {
            userService.registerClient(registerBodyDTO);
            return new ResponseEntity<>(CREATED);
        } catch (EmailExistsException e) {
            responseObject.put("error", "email address is already taken");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (ObjectMappingException e) {
            responseObject.put("error", "invalid data format, please provide valid data");
            return new ResponseEntity<>(responseObject, BAD_REQUEST);
        } catch (Exception e) {
            App.LOGGER.error(e.getMessage());
            return new ResponseEntity<>(INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/token/refresh")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) {
        try {
            String accessToken = authUtility.createAccessJWTFromRefreshToken(request);
            if (accessToken != null) {
                AuthUtility.setResponseMessage(response, "access_token", accessToken);
            } else {
                response.setStatus(UNAUTHORIZED.value());
                AuthUtility.setResponseMessage(response, "error", "refresh token is missing");
            }
        } catch (Exception e) {
            response.setStatus(INTERNAL_SERVER_ERROR.value());
            AuthUtility.setResponseMessage(response, "error", "unknown error");
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletResponse response) {
        Cookie blankCookie = new Cookie("refresh_token", "");
        blankCookie.setMaxAge(0);
        blankCookie.setHttpOnly(true);
        blankCookie.setDomain("localhost");
        blankCookie.setPath("/");
        response.addCookie(blankCookie);

        return new ResponseEntity<>(OK);
    }
}
