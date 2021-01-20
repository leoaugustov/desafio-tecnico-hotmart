package desafiotecnicohotmart.error;

import javax.validation.ConstraintViolationException;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import desafiotecnicohotmart.error.exception.EntityNotFoundException;
import io.jsonwebtoken.ExpiredJwtException;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<?> handleUserNotFoundException(EntityNotFoundException e) {
		ApiError error = new ApiError(HttpStatus.NOT_FOUND, e.getMessage());
		return buildResponseEntity(error, null);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	protected ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation error");
		error.addValidationErrors(e.getConstraintViolations());
		
		return buildResponseEntity(error, null);
	}
	
	@ExceptionHandler(ExpiredJwtException.class)
	protected ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException e) {
		ApiError error = new ApiError(HttpStatus.FORBIDDEN, "JWT expired");
		return buildResponseEntity(error, null);
	}
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException e, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Malformed JSON request");
		return buildResponseEntity(error, null);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		ApiError error = new ApiError(HttpStatus.BAD_REQUEST, "Validation error");
		error.addValidationErrors(e.getBindingResult().getFieldErrors());
		
		return buildResponseEntity(error, headers);
	}
	
	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception e, @Nullable Object body, 
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ApiError error = new ApiError(status, e.getMessage());
		return buildResponseEntity(error, headers);
	}
	
	private ResponseEntity<Object> buildResponseEntity(ApiError error, HttpHeaders headers) {
		return new ResponseEntity<>(error, headers, error.getStatus());
	}
	
}
