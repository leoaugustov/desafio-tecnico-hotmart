package desafiotecnicohotmart.security;

import java.util.Collections;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class AuthenticationService {

	private static final String AUTHENTICATION_HEADER_NAME = "Authorization";
	private static final String TOKEN_PREFIX = "Bearer";
	private static final String TIPO_TOKEN = "JWT";
	public static final long EXPIRATION_TIME = 900000; //15 minutes
	
	@Value("${auth.jwt.secret}")
	private String secret;
	
	public void addTokenToResponse(Authentication authResult, HttpServletResponse response) {
		String username = authResult.getName();
		
		String token = Jwts.builder()
    			.signWith(SignatureAlgorithm.HS512, secret.getBytes())
    			.setHeaderParam("typ", TIPO_TOKEN)
    			.setSubject(username)
    			.setIssuedAt(new Date(System.currentTimeMillis()))
    			.setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
    			.compact();
    	
		response.addHeader(AUTHENTICATION_HEADER_NAME, String.join(" ", TOKEN_PREFIX, token));
	}
	
	public UsernamePasswordAuthenticationToken getAuthFromRequest(HttpServletRequest request) {
		String token = request.getHeader(AUTHENTICATION_HEADER_NAME);
		
		if(token != null && token.isEmpty() == false && token.startsWith(TOKEN_PREFIX)) {
			String tokenWithouPrefix = token.replace(TOKEN_PREFIX, "");
			
			Claims claims = Jwts.parser()
					.setSigningKey(secret.getBytes())
					.parseClaimsJws(tokenWithouPrefix)
					.getBody();
			
			String username = claims.getSubject();
			if(username.isEmpty() == false) {
				return new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList());
			}
		}
		return null;
	}
	
}
