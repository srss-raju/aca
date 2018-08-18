package us.deloitteinnovation.profile.internal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import us.deloitteinnovation.profile.AbstractProfileProperties;
import us.deloitteinnovation.profile.ProfileProperties;
import us.deloitteinnovation.profile.annotation.Dryrun;

import java.io.IOException;

/**
 * Created by yaojia on 11/16/2016.
 */

@Dryrun
@Component("profileProperties")
public class DryrunProfileProperties extends AbstractProfileProperties implements ProfileProperties {
    private static final Logger LOGGER = LoggerFactory.getLogger(DryrunProfileProperties.class);

    public DryrunProfileProperties() throws IOException {
        super("classpath*:profiles/dryrun/**/*.properties");
        LOGGER.info("DryrunProfileProperties loaded!");
    }

    @Configuration
    @PropertySource({"classpath:profiles/dryrun/config.properties"})
    static class properties {
        properties() {}
    }
}
