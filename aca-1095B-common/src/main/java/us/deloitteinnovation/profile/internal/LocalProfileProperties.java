package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Local;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the Local Profile.
 *
 * @author ltornquist
 * @since 3/26/2015
 */
@Local
@Component("profileProperties")
public class LocalProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(LocalProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/local/config.properties")
    static class properties
    {}

    /**
     * Initialize and load the Local properties
     *
     * @throws IOException
     */
    public LocalProfileProperties() throws IOException {
        super("classpath*:profiles/local/**/*.properties");
        LOG.info("LocalProfileProperties loaded!");
    }
}
