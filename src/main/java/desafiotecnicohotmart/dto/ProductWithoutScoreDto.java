package desafiotecnicohotmart.dto;

import desafiotecnicohotmart.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

@RequiredArgsConstructor
@Getter
public class ProductWithoutScoreDto {

	@JsonUnwrapped
	@JsonIgnoreProperties("score")
	private final Product product;
	
}
