package aperture.config;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * The main configuration class used for testing.
 * Does not enable async tasks.
 */
@SpringBootApplication
@ComponentScan({"aperture"})
@EnableMongoRepositories("aperture.repository")
public class SpringBootApertureApiTestConfiguration {

    /**
     * Main method that start the spring-boot app.
     *
     * @param args the entry arguments for the app.
     */
    public static void main(String[] args) {
        SpringApplication.run(
            SpringBootApertureApiTestConfiguration.class, args
        );
    }
}
