package aperture.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Class used to configure the swagger docs and swagger-ui.
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * Main function for swagger conf.
     *
     * @return a docket with all configuration needed.
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.any())
            .paths(PathSelectors.regex("/api/.*"))
            .build()
            .apiInfo(apiInfo());
    }

    /**
     * Sub part of the configuration, just for display in
     * the swagger ui home page.
     *
     * @return an ApiInfo object containing title and description.
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("SpringBoot Aperture simple api")
            .description("This is just a demo rest api using springboot")
            .build();
    }
}
