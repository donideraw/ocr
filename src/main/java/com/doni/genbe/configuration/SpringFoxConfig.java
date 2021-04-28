package com.doni.genbe.configuration;

import com.doni.genbe.helper.Constant;
import com.google.common.base.Predicates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {
	@Bean
	public Docket restApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("api-user")
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Collections.singletonList(new ApiKey("JWT", Constant.HEADER_AUTHORIZATION, "header")))
				.select()
				.paths(Predicates.not(PathSelectors.regex("/error")))
				.build();
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.forPaths(PathSelectors.any())
				.securityReferences(Collections.singletonList(new SecurityReference("JWT",
						new AuthorizationScope[]{new AuthorizationScope("global", "accessEverything")})))
				.build();
	}
}
