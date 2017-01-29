package config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan("controllers, model")
@EnableMongoRepositories("repositories")
public class SpringBootApertureTestingConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApertureTestingConfiguration.class, args);
    }
}
