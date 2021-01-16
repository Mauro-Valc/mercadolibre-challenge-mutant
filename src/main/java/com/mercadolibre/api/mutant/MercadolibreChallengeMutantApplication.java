package com.mercadolibre.api.mutant;

import java.util.Collections;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * MercadoLibre Challenge
 * <p>
 * Project that exposes a REST API to detect mutants from their DNA, admeas a
 * status is exposed to visualize the scores of the DNA request
 * 
 * @author mauro.valc@gmail.com
 *
 */
@EnableSwagger2
@SpringBootApplication
public class MercadolibreChallengeMutantApplication {

	public static void main(String[] args) {
		SpringApplication.run(MercadolibreChallengeMutantApplication.class, args);
	}
	
	@Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.mercadolibre.api.mutant.rest"))
                .build().useDefaultResponseMessages(false).apiInfo(apiInfo());
    }
	
	private ApiInfo apiInfo() {
        return new ApiInfo("Mutant API REST", "Documentation of the API REST to detect mutants ", "1.0",
                "Visita https://example.com/terms", new Contact("MercadoLibre", "www.mercadolibre.com", "mauro.valc@gmail.com"),
                "License", "www.Mercadolibre.com/license", Collections.emptyList());
    }

}
