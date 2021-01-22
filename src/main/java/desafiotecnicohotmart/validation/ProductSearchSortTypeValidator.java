package desafiotecnicohotmart.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import desafiotecnicohotmart.model.ProductsSearchSortType;

public class ProductSearchSortTypeValidator implements ConstraintValidator<ProductSearchSortTypeConstraint, String> {

	@Override
	public boolean isValid(String queryParam, ConstraintValidatorContext context) {
		return ProductsSearchSortType.valueByQueryParam(queryParam).isPresent();
	}

}
