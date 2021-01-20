package desafiotecnicohotmart.security;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.fasterxml.jackson.databind.ObjectMapper;

import desafiotecnicohotmart.error.ApiError;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private AuthenticationService authenticationService;
	
	@Autowired
	@Qualifier("handlerExceptionResolver")
	private HandlerExceptionResolver handlerExceptionresolver;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		httpSecurity.cors()
		.and()
		.csrf().disable()
		.exceptionHandling()
		.authenticationEntryPoint(authenticationEntryPoint())
		.and()
		.authorizeRequests()
		.mvcMatchers(HttpMethod.POST, "/products").authenticated()
		.antMatchers(HttpMethod.PUT, "/products/*").authenticated()
		.antMatchers(HttpMethod.DELETE, "/products/*").authenticated()
		.anyRequest().permitAll()
		.and()
		.addFilter(new JwtAuthenticationFilter(authenticationManager(), failureHandler(), authenticationService, objectMapper))
		.addFilterAfter(new JwtAuthorizationFilter(handlerExceptionresolver, authenticationService), JwtAuthenticationFilter.class)
		.sessionManagement()
		.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}
	
	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
				.withUser("theonlyone")
				.password(passwordEncoder().encode("1234"))
				.authorities(new String[] {});
	}

	@Bean
	public AuthenticationEntryPoint authenticationEntryPoint() {
	    return (request, response, ex) -> {
	        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

	        ApiError error = new ApiError(HttpStatus.valueOf(HttpServletResponse.SC_FORBIDDEN), "Access Denied");
	        objectMapper.writeValue(response.getWriter(), error);
	    };
	}
	
	@Bean
	public AuthenticationFailureHandler failureHandler() {
	    return (request, response, ex) -> {
	        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	    	
	    	ApiError error = new ApiError(HttpStatus.valueOf(HttpServletResponse.SC_UNAUTHORIZED), "Unauthorized access attempt");
	    	objectMapper.writeValue(response.getWriter(), error);
	    };
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());

        return source;
    }
    
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
}
