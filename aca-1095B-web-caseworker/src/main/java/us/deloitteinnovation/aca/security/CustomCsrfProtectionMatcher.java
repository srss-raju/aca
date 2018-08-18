package us.deloitteinnovation.aca.security;

import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by yaojia on 11/3/2016.
 */
public class CustomCsrfProtectionMatcher implements RequestMatcher {
    @Override
    public boolean matches(HttpServletRequest httpServletRequest) {

        // For any POST/PUT/DELETE request, require XSRF token if not sending to saml/ or siteminderagent/
        if ("POST".equals(httpServletRequest.getMethod())
                || "PUT".equals(httpServletRequest.getMethod())
                || "DELETE".equals(httpServletRequest.getMethod())) {
            return !httpServletRequest.getRequestURI().contains("saml/")
                    && !httpServletRequest.getRequestURI().contains("siteminderagent/");
        }
        return false;
    }
}
