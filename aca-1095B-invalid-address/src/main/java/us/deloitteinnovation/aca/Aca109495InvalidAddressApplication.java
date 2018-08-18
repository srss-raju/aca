package us.deloitteinnovation.aca;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import us.deloitteinnovation.aca.batch.invalidaddress.InvalidAddressBatchJobConfiguration;
import us.deloitteinnovation.aca.batch.invalidaddress.InvalidAddressConfiguration;


@SpringBootApplication
@ComponentScan(basePackages = "us.deloitteinnovation.aca.batch", excludeFilters = @ComponentScan.Filter(Configuration.class))
@Import({CommonConfiguration.class, InvalidAddressConfiguration.class, InvalidAddressBatchJobConfiguration.class})
public class Aca109495InvalidAddressApplication {

	public static void main(String[] args) {
		System.exit(SpringApplication.exit(SpringApplication.run(Aca109495InvalidAddressApplication.class, args)));
	}

}
