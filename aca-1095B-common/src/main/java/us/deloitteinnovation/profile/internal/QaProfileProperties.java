package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Qa;

import java.io.IOException;

/**
 * Profile Properties class to handle loading the properties for the QA Profile.
 *
 * @author ltornquist
 * @since 3/26/2015
 */
@Qa
@Component("profileProperties")
public class QaProfileProperties extends AbstractProfileProperties implements ProfileProperties {

    private static final Logger LOG = LoggerFactory.getLogger(QaProfileProperties.class);

    @Configuration
    @PropertySource("classpath:profiles/qa/config.properties")
    static class properties
    {}

    /**
     * Initialize and load the QA properties
     *
     * @throws IOException
     */
    public QaProfileProperties() throws IOException {
        super("classpath*:profiles/qa/**/*.properties");
        LOG.info("QaProfileProperties loaded!");
    }
}
