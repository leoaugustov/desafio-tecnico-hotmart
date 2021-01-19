package desafiotecnicohotmart.error;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Collections;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.NodeImpl;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

public class ApiErrorTest {

	@Test
	public void testAddValidationErrors_when_listOfFieldError() {
		FieldError fieldError = mock(FieldError.class);
		when(fieldError.getDefaultMessage()).thenReturn("message");
		when(fieldError.getField()).thenReturn("field");
		when(fieldError.getRejectedValue()).thenReturn(2);
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "title");
		apiError.addValidationErrors(Arrays.asList(fieldError));
		
		assertThat(apiError.getValidationErrors())
				.usingFieldByFieldElementComparator()
				.containsExactly(new ValidationError("message", "field", 2));
	}
	
	@Test
	public void testAddValidationErrors_when_setOfConstraintViolation() {
		NodeImpl leafNode = mock(NodeImpl.class);
		when(leafNode.asString()).thenReturn("field");
		
		PathImpl propertyPath = mock(PathImpl.class);
		when(propertyPath.getLeafNode()).thenReturn(leafNode);
		
		ConstraintViolation<?> constrainViolation = mock(ConstraintViolation.class);
		when(constrainViolation.getPropertyPath()).thenReturn(propertyPath);
		when(constrainViolation.getMessage()).thenReturn("message");
		when(constrainViolation.getInvalidValue()).thenReturn(2);
		
		ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "title");
		apiError.addValidationErrors(Collections.singleton(constrainViolation));
		
		assertThat(apiError.getValidationErrors())
			.usingFieldByFieldElementComparator()
			.containsExactly(new ValidationError("message", "field", 2));
	}
	
}
