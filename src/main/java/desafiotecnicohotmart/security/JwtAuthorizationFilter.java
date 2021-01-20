package desafiotecnicohotmart.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final HandlerExceptionResolver handlerExceptionresolver;
	private final AuthenticationService authService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
			FilterChain filterChain) throws ServletException, IOException {
	
		try {
			UsernamePasswordAuthenticationToken auth = authService.getAuthFromRequest(request);
	    	if(auth != null) {
	    		SecurityContextHolder.getContext().setAuthentication(auth);
	    	}
			
			filterChain.doFilter(request, response);
		}catch(Exception e) {
			handlerExceptionresolver.resolveException(request, response, null, e);
		}
	}

}
