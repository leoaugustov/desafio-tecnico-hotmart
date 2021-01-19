package desafiotecnicohotmart.dto;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.springframework.data.domain.Page;

public class PageDtoTest {

	@Test
	public void testConstructorMethod() {
		List<String> content = Arrays.asList("1", "2", "3");
		int totalPages = 4;
		int currentPage = 1;
		int totalItems = 14;
		
		@SuppressWarnings("unchecked")
		Page<String> page = (Page<String>) mock(Page.class);
		when(page.getContent()).thenReturn(content);
		when(page.getNumber()).thenReturn(currentPage);
		when(page.getNumberOfElements()).thenReturn(totalItems);
		when(page.getTotalPages()).thenReturn(totalPages);
		
		PageDto<String> pageDto = new PageDto<>(page);
		
		assertEquals(content, pageDto.getContent());
		assertEquals(totalPages, pageDto.getTotalPages());
		assertEquals(currentPage, pageDto.getCurrentPage());
		assertEquals(totalItems, pageDto.getTotalItems());
	}
	
}
