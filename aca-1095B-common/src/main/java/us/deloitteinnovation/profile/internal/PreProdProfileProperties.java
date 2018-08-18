package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.PreProd;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the PreProd Profile.
 *
 * @author ltornquist
 * @since 12/01/2015
 */
@PreProd
@Component("profileProperties")
public class PreProdProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(PreProdProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/preprod/config.properties")
    static class properties
    {}

    /**
     * Initialize and load the PreProd properties
     *
     * @throws IOException
     */
    public PreProdProfileProperties() throws IOException {
        super("classpath*:profiles/preprod/**/*.properties");
        LOG.info("PreProdProfileProperties loaded!");
    }
}
