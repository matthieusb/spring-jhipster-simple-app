package aperture.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"aperture"})
@EnableMongoRepositories("aperture.repository")
public class SpringBootApertureTestingConfiguration {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootApertureTestingConfiguration.class, args);
    }
}
