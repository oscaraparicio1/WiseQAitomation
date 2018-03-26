package es.sogeti.wiseqarest.security;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import es.sogeti.wiseqarest.authentication.Role;
import es.sogeti.wiseqarest.managment.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JWTUtil {
	
	static final String CLAIM_ROLE = "role";
    static final String CLAIM_KEY_CREATED = "created";
	
	@Value("thisIsAGoodSecret")
	private String secret;
	 
	@Value("604800")
    private String expiration;
	
    /**
     * Tries to parse specified String as a JWT token. If successful, returns User object with username, id and role prefilled (extracted from token).
     * If unsuccessful (token is invalid or not containing all required user properties), simply returns null.
     * 
     * @param token the JWT token to parse
     * @return the User object extracted from specified token or null if a token is invalid.
     */	 
    public User parseToken(String token) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();

            User u = new User();
            u.setUserName(body.getSubject());
            u.setRole(Role.find((String) body.get(CLAIM_ROLE)));

            return u;

        } catch (JwtException | ClassCastException e) {
//        	Log.error(this.getClass(), "Error parsing JWT token");
            return null;
        }
    }
    
    /**
     * Generates a JWT token containing username as subject, and userId and role as additional claims. These properties are taken from the specified
     * User object. Tokens validity is infinite.
     * 
     * @param u the user for which the token will be generated
     * @return the JWT token
     */
    public String generateToken(UserDetails u) {
        Claims claims = Jwts.claims().setSubject(u.getUsername());

        claims.put(CLAIM_ROLE, u.getAuthorities().iterator().next());
        claims.put(CLAIM_KEY_CREATED, this.generateCurrentDate());
        
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }  
    
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
	            .setClaims(claims)
	            .setExpiration(this.generateExpirationDate())
	            .signWith(SignatureAlgorithm.HS512, this.secret)
	            .compact();
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }
    
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + 60400 * 1000);
    }
    
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
    
    public Date getExpirationDateFromToken(String token) {
        Date expiration;
        try {
            final Claims claims = getClaimsFromToken(token);
            expiration = claims.getExpiration();
        } catch (Exception e) {
            expiration = null;
        }
        return expiration;
    }
    
    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }
    
    public String getUsernameFromToken(String token) {
        String username;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }
    
    public String refreshToken(String token) {
        String refreshedToken;
        try {
            final Claims claims = getClaimsFromToken(token);
            claims.put(CLAIM_KEY_CREATED, new Date());
            refreshedToken = generateToken(claims);
        } catch (Exception e) {
            refreshedToken = null;
        }
        return refreshedToken;
    }
    
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            final Claims claims = this.getClaimsFromToken(token);
            created = new Date((Long) claims.get(CLAIM_KEY_CREATED));
        } catch (Exception e) {
            created = null;
        }
        return created;
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.getUsernameFromToken(token);
        //final Date created = this.getCreatedDateFromToken(token);
        return (username.equals(userDetails.getUsername())
                && !(this.isTokenExpired(token)));
    }
}
