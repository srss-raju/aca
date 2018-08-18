package us.deloitteinnovation.aca.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Created by yaojia on 11/9/2016.
 */
@Component
@Profile({"dev", "qa", "preprod", "prod", "dryrun"})
public class SecureAuthenticationHolder implements IAuthenticationHolder {
    @Override
    public UserSession getUserSession() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Object principal = context.getAuthentication().getPrincipal();
            if (principal instanceof UserSession) {
                return (UserSession) context.getAuthentication().getPrincipal();
            }
        }
        throw new AuthenticationCredentialsNotFoundException("Cannot obtain authentication principal from security context");
    }
}
