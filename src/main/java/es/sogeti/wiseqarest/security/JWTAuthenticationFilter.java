package es.sogeti.wiseqarest.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTAuthenticationFilter extends OncePerRequestFilter  {
	
    @Value("Authorization")
    private String tokenHeader;
    
    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserDetailsService customUserDetailsService;
    
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
	    HttpServletRequest httpRequest = (HttpServletRequest) request;
	    String authToken = httpRequest.getHeader("Authorization");
	    if (authToken == null || !authToken.startsWith("Bearer"))
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	    else{
	    	authToken = authToken.substring(7).trim();
		    String username = this.jwtUtil.getUsernameFromToken(authToken);
		    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
		    	UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);
		    	if (this.jwtUtil.validateToken(authToken, userDetails)) {
		    		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
		    		authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpRequest));
		    		SecurityContextHolder.getContext().setAuthentication(authentication);
		    	}
		    }
	    }
		chain.doFilter(request, response);
	}
}
