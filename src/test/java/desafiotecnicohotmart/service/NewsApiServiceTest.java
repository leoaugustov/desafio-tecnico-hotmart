package desafiotecnicohotmart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;

@RunWith(MockitoJUnitRunner.class)
public class NewsApiServiceTest {

	@Mock
	private RestTemplate restTemplate;
	
	@Test
	public void testCountTopHeadlines_when_queryHasCharactersToBeEncoded() throws UnsupportedEncodingException {
		NewsApiService newApi = new NewsApiService(restTemplate);
		
		JsonNode articlesArray = mock(JsonNode.class);
		when(articlesArray.iterator()).thenReturn(new ArrayList<JsonNode>().iterator());
		
		JsonNode responseObject = mock(JsonNode.class);
		when(responseObject.get("articles")).thenReturn(articlesArray);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) mock(ResponseEntity.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK);
		when(response.getBody()).thenReturn(responseObject);
		
		when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(response);
		
		newApi.countTopHeadlines("video games", null);
		
		verify(restTemplate).getForEntity(endsWith("video+games"), eq(JsonNode.class));
	}
	
	@Test
	public void testCountTopHeadlines_when_statusCodeNot200() throws UnsupportedEncodingException {
		HttpStatus statusCode = HttpStatus.BAD_REQUEST;
		String apiErrorCode = "apiErrorCode";
		String apiErrorMessage = "message";
		
		NewsApiService newApi = new NewsApiService(restTemplate);
		
		JsonNode codeField = mock(JsonNode.class);
		when(codeField.asText()).thenReturn(apiErrorCode);
		
		JsonNode messageField = mock(JsonNode.class);
		when(messageField.asText()).thenReturn(apiErrorMessage);
		
		JsonNode errorObject = mock(JsonNode.class);
		when(errorObject.get("code")).thenReturn(codeField);
		when(errorObject.get("message")).thenReturn(messageField);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) mock(ResponseEntity.class);
		when(response.getStatusCode()).thenReturn(statusCode);
		when(response.getBody()).thenReturn(errorObject);
		
		when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(response);
		
		try {
			newApi.countTopHeadlines("query", null);
			fail("the exception was not thrown");
		}catch(RuntimeException e) {
			assertThat(e.getMessage())
					.contains(String.valueOf(statusCode.value()), apiErrorCode, apiErrorMessage);
		}
	}
	
	@Test
	public void testCountTopHeadlines_when_statusCode200_withContent() throws UnsupportedEncodingException {
		LocalDateTime minPublishedTime = LocalDateTime.parse("2021-01-20T10:15:30");
		
		NewsApiService newApi = new NewsApiService(restTemplate);

		JsonNode firstArticleObject = mockArticleObject(minPublishedTime.minusMinutes(3).toString() + "Z");
		JsonNode secondArticleObject = mockArticleObject(minPublishedTime.plusMinutes(3).toString() + "Z");
		JsonNode thirdArticleObject = mockArticleObject(minPublishedTime.toString() + "Z");
		
		JsonNode articlesArray = mock(JsonNode.class);
		when(articlesArray.iterator())
				.thenReturn(Arrays.asList(firstArticleObject, secondArticleObject, thirdArticleObject).iterator());
		
		JsonNode responseObject = mock(JsonNode.class);
		when(responseObject.get("articles")).thenReturn(articlesArray);
		
		@SuppressWarnings("unchecked")
		ResponseEntity<JsonNode> response = (ResponseEntity<JsonNode>) mock(ResponseEntity.class);
		when(response.getStatusCode()).thenReturn(HttpStatus.OK);
		when(response.getBody()).thenReturn(responseObject);
		
		when(restTemplate.getForEntity(anyString(), eq(JsonNode.class))).thenReturn(response);
		
		assertThat(newApi.countTopHeadlines("query", minPublishedTime)).isEqualTo(2);
	}
	
	private JsonNode mockArticleObject(String publishedAt) {
		JsonNode publishedAtField = mock(JsonNode.class);
		when(publishedAtField.asText()).thenReturn(publishedAt);
		
		JsonNode articleObject = mock(JsonNode.class);
		when(articleObject.get("publishedAt")).thenReturn(publishedAtField);
		return articleObject;
	}
	
}
