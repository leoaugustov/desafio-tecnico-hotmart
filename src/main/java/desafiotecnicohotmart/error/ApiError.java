package desafiotecnicohotmart.error;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ApiError {

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private final LocalDateTime timestamp;
	
	private final HttpStatus status;
	private final String message;
	private List<ValidationError> validationErrors = new ArrayList<>();
	
	public ApiError(HttpStatus status, String message) {
		this.timestamp = LocalDateTime.now();
		this.status = status;
		this.message = message;
	}
	
	public void addValidationErrors(Set<ConstraintViolation<?>> constraintViolations) {
		constraintViolations.forEach(constraintViolation -> {
			String field = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().asString();
			
			ValidationError validationError = new ValidationError(constraintViolation.getMessage(), field, 
					constraintViolation.getInvalidValue());
			
			validationErrors.add(validationError);
		});
	}
	
	public void addValidationErrors(List<FieldError> fieldErrors) {
		fieldErrors.forEach(fieldError -> {
			ValidationError validationError = new ValidationError(fieldError.getDefaultMessage(), 
					fieldError.getField(), fieldError.getRejectedValue());
			
			validationErrors.add(validationError);
		});
	}
	
}
