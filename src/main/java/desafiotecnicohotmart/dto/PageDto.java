package desafiotecnicohotmart.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageDto<T> {

	private final List<T> content;
	private final int currentPage;
	private final int totalItems;
	private final int totalPages;
	
	public PageDto(Page<T> page) {
		content = page.getContent();
		currentPage = page.getNumber();
		totalItems = page.getNumberOfElements();
		totalPages = page.getTotalPages();
	}
	
}
