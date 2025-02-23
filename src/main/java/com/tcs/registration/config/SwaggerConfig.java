package com.tcs.registration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * SwaggerConfig is a configuration class for setting up Swagger 2 for the application.
 * 
 * This class is annotated with @EnableSwagger2 to enable Swagger support in the application.
 * It also uses @Configuration to indicate that it is a Spring configuration class.
 * 
 * The productApi() method is a Spring bean that configures the Docket bean for Swagger.
 * It specifies the base package "com.tcs.registration" to scan for API documentation.
 * 
 * Annotations:
 * - @EnableSwagger2: Enables Swagger 2 for the application.
 * - @Configuration: Indicates that this is a Spring configuration class.
 * - @Bean: Indicates that the method returns a bean to be managed by the Spring container.
 * 
 * Methods:
 * - productApi(): Configures and returns a Docket bean for Swagger 2 documentation.
 * 
 * @return Docket bean configured for Swagger 2 documentation.
 */
@EnableSwagger2
@Configuration
//@EnableWebMvc
public class SwaggerConfig {

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.tcs.registration")).build();
    }

}
