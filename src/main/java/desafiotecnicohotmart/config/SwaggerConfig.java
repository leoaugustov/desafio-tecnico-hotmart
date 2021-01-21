package desafiotecnicohotmart.config;

import static java.util.Arrays.asList;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {

	@Value("${project.version}")
	private String version;
	
    @Bean
    public Docket api() {
    	return new Docket(DocumentationType.SWAGGER_2)
    			.securityContexts(asList(securityContext()))
    			.securitySchemes(asList(apiKey()))
    			.select()
    			.apis(RequestHandlerSelectors.basePackage("desafiotecnicohotmart"))
    			.paths(PathSelectors.any())
    			.build()
    			.apiInfo(apiInfo());
    }
	
    private SecurityContext securityContext() {
        return SecurityContext.builder()
        		.securityReferences(defaultAuth())
        		.operationSelector(operation -> ! operation.httpMethod().equals(HttpMethod.GET))
        		.build();
    } 

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        return asList(new SecurityReference("JWT", new AuthorizationScope[] { authorizationScope }));
    }
    
    private ApiKey apiKey() {
        return new ApiKey("JWT", HttpHeaders.AUTHORIZATION, "header");
    }
    
    private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("Products API Documentation")
				.version(version)
				.build();
    }
    
}
