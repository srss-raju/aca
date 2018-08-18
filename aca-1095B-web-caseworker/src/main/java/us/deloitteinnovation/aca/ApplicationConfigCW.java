package us.deloitteinnovation.aca;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import us.deloitteinnovation.aca.model.FilerMapper;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * Application Configuration
 *
 * @author yvinogradov
 * @since 6/8/2015
 */
@SpringBootApplication
public class ApplicationConfigCW {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigCW.class);

    @Bean
    public FilerMapper filerMapper(){return new FilerMapper();}

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfigCW.class);
    public static ConfigurableEnvironment env;

    @Autowired
    private org.springframework.core.env.Environment environment;

    public static void main(String[] args) throws UnknownHostException {
        try{
            SpringApplication app = new SpringApplication(ApplicationConfigCW.class);
            env = app.run(args).getEnvironment();
        }
        catch (Exception e)
        {
            logger.error("Error in starting application :",e);
        }
    }

    @PostConstruct
    public void initApplication() {
        if (environment.getActiveProfiles().length == 0) {
            logger.warn("No Spring profile configured, running with default configuration");
        } else {
            logger.warn("Running with Spring profile(s) : {}", Arrays.toString(environment.getActiveProfiles()));
        }
    }
    /**
     * Maps all AngularJS routes to index so that they work with direct linking.
     */
    @Controller
    static class angularRoutes {

        @RequestMapping({
                "/"
        })
        public String index() {
            return "forward:/index.html";
        }
    }

}