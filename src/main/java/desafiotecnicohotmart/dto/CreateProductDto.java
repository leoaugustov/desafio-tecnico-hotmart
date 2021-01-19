package desafiotecnicohotmart.dto;

import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import desafiotecnicohotmart.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateProductDto {

	@JsonUnwrapped
	private final Product product;
	
	@NotEmpty(message = "The product must have at least 1 category")
	private final Set<Long> categoriesIds;
	
}
