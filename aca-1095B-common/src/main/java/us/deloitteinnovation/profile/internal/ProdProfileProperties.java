package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Prod;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the Prod Profile.
 *
 * @author ltornquist
 * @since 12/01/2015
 */
@Prod
@Component("profileProperties")
public class ProdProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(ProdProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/prod/config.properties")
    static class properties
    {}

    /**
     * Initialize and load the Dev properties
     *
     * @throws IOException
     */
    public ProdProfileProperties() throws IOException {
        super("classpath*:profiles/prod/**/*.properties");
        LOG.info("ProdProfileProperties loaded!");
    }
}
