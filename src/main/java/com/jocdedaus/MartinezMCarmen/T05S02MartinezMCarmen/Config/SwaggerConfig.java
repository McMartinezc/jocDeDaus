package com.jocdedaus.MartinezMCarmen.T05S02MartinezMCarmen.Config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration

//Anotación que habilita el Swagger
@EnableSwagger2
@ConditionalOnProperty(name="app.api.swagger.enable", havingValue = "true", matchIfMissing = false)
//http://localhost:9000/swagger-ui/index.html#/

public class SwaggerConfig {

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiDetails());
    }

    //datos de la documentación de la api
    private ApiInfo apiDetails (){
        return new ApiInfoBuilder().title("Spring Boot Flors API REST")
                .description("Joc de daus")
                .termsOfServiceUrl("http://localhost:9001/players")
                .contact(new Contact("MC", "http://localhost:9001/","jocdedaus@email.com"))
                .version("1").build();
    }
}
