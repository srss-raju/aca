package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Dev;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the Dev Profile.
 *
 * @author ltornquist
 * @since 3/26/2015
 */
@Dev
@Component("profileProperties")
public class DevProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(DevProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/dev/config.properties")
    static class properties
    {}

    /**
     * Initialize and load the Dev properties
     *
     * @throws IOException
     */
    public DevProfileProperties() throws IOException {
        super("classpath*:profiles/dev/**/*.properties");
        LOG.info("DevProfileProperties loaded!");
    }
}
