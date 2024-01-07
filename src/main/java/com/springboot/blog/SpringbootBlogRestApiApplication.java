package com.springboot.blog;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "SpringBoot - Blog API REST",
				description = "SpringBoot Blog API documentation",
				version = "v1.0",
				contact = @Contact(
						name = "William",
						url = "https://github.com/william-wtr92"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://github.com/william-wtr92"
				)
		),
		externalDocs = @ExternalDocumentation(
				description = "Spring Boot Blog API documentation",
				url = "https://github.com/william-wtr92/blog-spring-api"
		)
)
public class SpringbootBlogRestApiApplication {
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringbootBlogRestApiApplication.class, args);
	}
}
