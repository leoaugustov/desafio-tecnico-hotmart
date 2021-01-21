package desafiotecnicohotmart.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import desafiotecnicohotmart.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class ProductsSearchDto {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private final LocalDateTime timestamp;
	
	private final String searchedTerm;
	
	@JsonIgnoreProperties("category")
	private final List<Product> products;
	
	@JsonIgnoreProperties("content")
	@JsonUnwrapped
	private final PageDto<Product> productsPage;
	
	public ProductsSearchDto(LocalDateTime timestamp, String searchedTerm, PageDto<Product> productsPage) {
		this.timestamp = timestamp;
		this.searchedTerm = searchedTerm;
		this.productsPage = productsPage;
		products = productsPage.getContent();
	}
	
}
