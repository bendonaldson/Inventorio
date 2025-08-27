package dev.bendonaldson.inventorio.security;

import dev.bendonaldson.inventorio.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

/**
 * Service for handling JSON Web Token (JWT) operations, including creation,
 * validation, and extraction of claims.
 */
@Service
public class JwtService {

    private final SecretKey secretKey;
    private final long jwtExpiration;

    /**
     * Constructs the JwtService using configuration properties.
     * @param jwtProperties The type-safe properties for JWT configuration.
     */
    public JwtService(JwtProperties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.secretKey()));
        this.jwtExpiration = jwtProperties.expirationMs();
    }

    /**
     * Extracts the username (subject) from a JWT.
     * @param token The JWT string.
     * @return The username.
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * A generic method to extract a specific claim from a JWT.
     * @param token The JWT string.
     * @param claimsResolver A function to resolve the desired claim from the claims body.
     * @return The extracted claim.
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Generates a new JWT for a given user.
     * @param userDetails The user details object for whom the token is being generated.
     * @return The JWT string.
     */
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Validates a JWT against a user's details.
     * @param token The JWT string.
     * @param userDetails The user to validate against.
     * @return True if the token is valid, false otherwise.
     */
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
