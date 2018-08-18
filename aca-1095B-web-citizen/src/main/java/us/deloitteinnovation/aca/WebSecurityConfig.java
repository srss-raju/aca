package us.deloitteinnovation.aca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import us.deloitteinnovation.profile.ProfileProperties;

/**
 * Created by sdalavi on 11/9/2015.
 */
@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(securedEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);
    @Autowired
    private ProfileProperties profileProperties;







    /**
     * Defines the web based security configuration.
     *
     * @param http
     *     It allows configuring web based security for specific http requests.
     *
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf()
                .disable();

        if (profileProperties.getProperty("security.enabled") != null &&
                Boolean.valueOf(profileProperties.getProperty("security.enabled"))) {
            http
                    .authorizeRequests()
                    .antMatchers("/swagger-ui.html").denyAll()
                    .anyRequest().permitAll();

        } else {
            http
                    .authorizeRequests()
                    .anyRequest().permitAll();
        }
        http
                .logout()
                .logoutSuccessUrl("/");
    }


    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .antMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**")
                .antMatchers("*.{js,html,css,jpg}");

    }
}