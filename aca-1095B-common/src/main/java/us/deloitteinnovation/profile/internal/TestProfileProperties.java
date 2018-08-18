package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Test;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the Test Profile.
 *
 * @author ltornquist
 * @since 3/27/2015
 */
@Test
@Component("profileProperties")
public class TestProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(TestProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/test/config.properties")
    static class properties
    {}

    /**
     * Construct the profile properties.
     *
     * @throws IOException
     */
    public TestProfileProperties() throws IOException {
        super("classpath*:profiles/test/**/*.properties");
        LOG.info("TestProfileProperties loaded!");
    }
}
