package games.crusader.bugwars.aicrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class BugWarsAiCrudApplication {
	public static void main(String[] args) {
		SpringApplication.run(BugWarsAiCrudApplication.class, args);
	}
}
