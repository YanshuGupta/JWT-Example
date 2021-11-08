package com.example.authserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
//@EnableEurekaClient
@Controller
public class AuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServerApplication.class, args);
	}

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.example.authserver.controller"))
				.paths(PathSelectors.any()).build();
	}

	@GetMapping("/")
	public String index(@AuthenticationPrincipal OAuth2User principal) {
		System.out.println(principal.getAttribute("name").toString());
		return "redirect:swagger-ui.html";
	}

//	@GetMapping("/token")
//	public ResponseEntity<String> token(@RegisteredOAuth2AuthorizedClient("github") OAuth2AuthorizedClient client) {
//		return ResponseEntity.ok(client.getAccessToken().getTokenValue());
//	}
}
