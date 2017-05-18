package aperture.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * The main configuration class used for execution.
 */
@SpringBootApplication
@EnableAsync
@ComponentScan({"aperture"})
@EnableMongoRepositories("aperture.repository")
public class SpringBootApertureApiConfiguration {

    /**
     * Main method that start the spring-boot app.
     *
     * @param args the entry arguments for the app.
     */
    public static void main(String[] args) {
        SpringApplication.run(
            SpringBootApertureApiConfiguration.class, args
        );
    }
}
