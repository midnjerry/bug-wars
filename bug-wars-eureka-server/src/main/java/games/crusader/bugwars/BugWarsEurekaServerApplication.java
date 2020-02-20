package games.crusader.bugwars;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 *  Eureka Server
 *
 *  1.) Add Eureka Dependency
 *  2.) Add @EnableEurekaServer
 *  3.) Configure
 */

@EnableEurekaServer
@SpringBootApplication
public class BugWarsEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BugWarsEurekaServerApplication.class, args);
	}

}
