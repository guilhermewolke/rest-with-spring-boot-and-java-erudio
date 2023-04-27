package br.com.zaek.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenAPIConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI().info(new Info().title("RESTFul API with Java 18 and Spring Boot 3").version("v1")
				.description("Documentação do curso de SpringBoot 3").termsOfService("http://www.zaek.com.br/")
				.license(new License().name("Apache 2.0").url("http://www.zaek.com.br/")));
	}

	public OpenAPIConfig() {
		// TODO Auto-generated constructor stub
	}

}
