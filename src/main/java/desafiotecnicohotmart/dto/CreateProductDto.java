package desafiotecnicohotmart.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import desafiotecnicohotmart.model.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class CreateProductDto {

	@JsonUnwrapped
	private final Product product;
	
	@Positive(message = "Category id must be a positive integer")
	@NotNull(message = "Category id is mandatory")
	private final Long categoryId;
	
}
