package es.sogeti.wiseqarest.rest;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.sogeti.wiseqarest.security.JWTUtil;

@RestController
@RequestMapping("/rest")
public class AuthController {
	@Value("Authorization")
	private String tokenHeader;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private UserDetailsService customUserDetailsService;

	@PostMapping("/auth")
	public ResponseEntity<?> createAuthenticationToken(@RequestParam("username") String username,
			@RequestParam("password") String password) {
		final Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(username, password));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		// Reload password post-security so we can generate token
		final UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
		final String token = jwtUtil.generateToken(userDetails);

		// Return the token
		JSONObject json = new JSONObject();
		try {
			json.put("Authorization", "Bearer " + token);
			return ResponseEntity.ok(json.toString());
		} catch (JSONException e) {
			return (ResponseEntity<?>) ResponseEntity.notFound();
		}
	}

	@GetMapping("/refresh")
	public ResponseEntity<?> refreshAuthenticationToken(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
		String refreshedToken = jwtUtil.refreshToken(token);
		return ResponseEntity.ok(refreshedToken);
	}
}
