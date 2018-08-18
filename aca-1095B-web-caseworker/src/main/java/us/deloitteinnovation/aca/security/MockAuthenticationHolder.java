package us.deloitteinnovation.aca.security;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

/**
 * Created by yaojia on 11/9/2016.
 */
@Component
@Profile({"test", "local"})
public class MockAuthenticationHolder implements IAuthenticationHolder {
    @Override
    public UserSession getUserSession() {
        UserSession userSession = new UserSession();
        userSession.setState("AR");
        userSession.setEmail("mock_email_address@example.com");
        userSession.setRole("1095B_Caseworker ReadWrite");
        return userSession;
    }
}
