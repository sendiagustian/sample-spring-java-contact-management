package id.sendistudio.spring.base.app.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import id.sendistudio.spring.base.app.configs.properties.DatabaseProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@AllArgsConstructor
public class JwtTokenUtil {

    @Autowired
    DatabaseProperties databaseProperties;

    public SecretKey getSigningKey() {
        String SECRET_KEY = databaseProperties.getScreet();
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // Generate JWT token
    public String generateToken(String username, Boolean isNotExpired) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username, isNotExpired);
    }

    // Create JWT token
    public String createToken(Map<String, Object> claims, String subject, Boolean isNotExpired) {
        JwtBuilder builder = Jwts.builder();

        builder.setClaims(claims);
        builder.setSubject(subject);
        builder.setIssuedAt(new Date(System.currentTimeMillis()));

        if (!isNotExpired) {
            builder.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24));
        }

        builder.signWith(getSigningKey(), SignatureAlgorithm.HS256);

        return builder.compact();

    }

    // Retrieve username from JWT token
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // Retrieve expiration date from JWT token
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // Retrieve all claims from JWT token
    public Claims extractAllClaims(String token) {
        JwtParser parserBuilder = Jwts.parserBuilder().setSigningKey(getSigningKey()).build();
        return parserBuilder.parseClaimsJws(token).getBody();
    }

    // Check if the token has expired
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    // Validate token
    public Boolean validateToken(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }
}
