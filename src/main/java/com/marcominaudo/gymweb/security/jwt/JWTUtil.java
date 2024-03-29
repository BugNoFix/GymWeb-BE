package com.marcominaudo.gymweb.security.jwt;

import com.marcominaudo.gymweb.exception.exceptions.JWTException;
import com.marcominaudo.gymweb.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@AllArgsConstructor
@NoArgsConstructor
public class JWTUtil {
    @Value("${application.security.jwt.secret-key}")
    private String secretKey;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpiration;

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // Extract specific claim
    public <T> T extractClaim(String token, Function<Claims, T> extractFunction) {
        final Claims claims = extractAllClaims(token);
        return extractFunction.apply(claims);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /*
    private boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

     */

    public String generateToken(User user) {
        Map<String, Object> extraClaims = new HashMap<>();
        extraClaims.put("uuid", user.getUuid());
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(user.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public void isTokenValid(String jwt) throws JWTException {
        try {
            Jwts.parser().setSigningKey(getSignInKey()).parseClaimsJws(jwt);
        }
        catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            throw new JWTException(JWTException.ExceptionCodes.INVALID_JWT_SIGNATURE);
        }
        catch (ExpiredJwtException e) {
            throw new JWTException(JWTException.ExceptionCodes.EXPIRED_JWT_TOKEN);
        }
        catch (UnsupportedJwtException e) {
            throw new JWTException(JWTException.ExceptionCodes.UNSUPPORTED_JWT_TOKEN);
        }
    }
    private Key getSignInKey() {
        // Encrypt secret key with sha256
        return Keys.hmacShaKeyFor(secretKey.getBytes());
    }
}
