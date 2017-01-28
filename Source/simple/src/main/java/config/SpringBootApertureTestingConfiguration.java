package config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("controllers")
public class SpringBootApertureTestingConfiguration {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootApertureTestingConfiguration.class, args);
  }
}
