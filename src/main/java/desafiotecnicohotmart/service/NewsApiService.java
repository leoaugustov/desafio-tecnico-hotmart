package desafiotecnicohotmart.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NewsApiService {

	private final RestTemplate restTemplate;
	
	/**
	 * Count the top headlines for a query string and a minimal published time.
	 * @param query the query string. It will be encoded
	 * @param minPublishedTime the minimal published time. Must be UTC (+000)
	 * @return the number of headlines found
	 */
	public int countTopHeadlines(String query, LocalDateTime minPublishedTime) throws UnsupportedEncodingException {
		String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8.toString());
		String uri = "https://newsapi.org/v2/top-headlines?pageSize=100&q=" + encodedQuery;
		
		ResponseEntity<JsonNode> response = restTemplate.getForEntity(uri, JsonNode.class);
		
		if(response.getStatusCode().is2xxSuccessful()) {
			int numberOfNewsFound = 0;
			for(JsonNode newsObject : response.getBody().get("articles")) {
				LocalDateTime publishedAt = ZonedDateTime.parse(newsObject.get("publishedAt").asText()).toLocalDateTime();
				
				if(query.equals("Gel")) {
					System.out.println("olaa");
				}
				
				if(publishedAt.isEqual(minPublishedTime) || publishedAt.isAfter(minPublishedTime)) {
					numberOfNewsFound++;
				}
			}
			return numberOfNewsFound;
		}
		throw new RuntimeException(buildErrorMessage(response.getStatusCode(), response.getBody()));
	}
	
	private String buildErrorMessage(HttpStatus statusCode, JsonNode errorObject) {
		String apiErrorCode = errorObject.get("code").asText();
		String message = errorObject.get("message").asText();
		return statusCode.value() + " - " + apiErrorCode + ": " + message;
	}
	
}
