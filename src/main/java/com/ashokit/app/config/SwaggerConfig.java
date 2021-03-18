package com.ashokit.app.config;

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
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket restApi() {
		Docket docket = new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.basePackage("com.ashokit.app.controller"))
				.paths(PathSelectors.any()).build().apiInfo(apiInfo());
		return docket;
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("User-Management Api")
				.description(
						"Using this API we can perform Registration,Authentication,Authorization operation on User")
				.version("0.0.1release").termsOfServiceUrl("durgamahadevgroup.service@dmg.com")
				.contact(
						new Contact("DurgamadevGroup", "DurgamadevGroupService.com", "DurgamadevGroup.service@dmg.com"))
				.license("Apache 2.0").licenseUrl("http://www.apache.org/licenses/LICENSE-2.0").build();

	}

}
