package desafiotecnicohotmart.config;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

	@Value("${newsapi.key}")
	private String newsApiKey;
	
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		ClientHttpRequestInterceptor interceptor = (HttpRequest request, byte[] body, ClientHttpRequestExecution execution) -> {
			request.getHeaders().add("X-Api-Key", newsApiKey);
			
			return execution.execute(request, body);
		};
		return builder.additionalInterceptors(interceptor)
				.setConnectTimeout(Duration.ofSeconds(5))
				.setReadTimeout(Duration.ofSeconds(5))
				.build();
	}
	
}
