package desafiotecnicohotmart.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
@Getter
public class ValidationError {

	private final String message;
    private String field;
    private Object rejectedValue;
	
}
