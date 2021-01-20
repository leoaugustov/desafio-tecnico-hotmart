package desafiotecnicohotmart.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@Getter
public class AuthAttemptDto {

	private final String username;
	private final String password;
	
	@JsonCreator
	public AuthAttemptDto(@JsonProperty("username") String username, @JsonProperty("password") String password) {
		this.username = username;
		this.password = password;
	}
	
}
