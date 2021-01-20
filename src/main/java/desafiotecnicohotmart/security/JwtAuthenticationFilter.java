package desafiotecnicohotmart.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

import desafiotecnicohotmart.dto.AuthAttemptDto;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private static final String AUTH_PATH = "/authenticate";
	private final AuthenticationManager authenticationManager;
	private final AuthenticationService authService;
	private final ObjectMapper objectMapper;
	
	public JwtAuthenticationFilter(AuthenticationManager authenticationManager, 
			AuthenticationService authService, ObjectMapper objectMapper) {
		
		this.authenticationManager = authenticationManager;
		this.authService = authService;
		this.objectMapper = objectMapper;
		setFilterProcessesUrl(AUTH_PATH);
	}
	
	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) 
			throws AuthenticationException {
		
		try {
			AuthAttemptDto credentials = objectMapper.readValue(request.getInputStream(), AuthAttemptDto.class);
			UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(credentials.getUsername(), 
					credentials.getPassword());
			
			return authenticationManager.authenticate(auth);
		}catch(IOException e) {
			throw new RuntimeException("Problem while trying to authenticate");
		}
	}
	
	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain, Authentication authResult) throws IOException, ServletException {
		
		authService.addTokenToResponse(authResult, response);
	}
	
}
