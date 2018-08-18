package us.deloitteinnovation.aca.web.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import us.deloitteinnovation.aca.security.UserSession;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by sdalavi on 11/16/2015.
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    public static final int SESSION_TIMEOUT = 60 * 60;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/getUserSession", method = RequestMethod.GET)
    public HttpEntity<UserSession> getSessionUserDetails(final HttpServletRequest request) {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Object principal = context.getAuthentication().getPrincipal();
            if (principal instanceof UserSession) {
                UserSession userSession = (UserSession) context.getAuthentication().getPrincipal();
                request.getSession(false).setMaxInactiveInterval(SESSION_TIMEOUT);
                logger.info("logged in user: " + userSession);
                return ResponseEntity.ok(userSession);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}