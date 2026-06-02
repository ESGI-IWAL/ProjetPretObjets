package com.aliw.pretemoica.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

  @Value("${jwt.secret:mySecretKeyForJwtTokenGenerationAndValidation12345}")
  private String jwtSecret;

  @Value("${jwt.expiration:86400000}") // 24 heures par défaut
  private long jwtExpiration;

  private SecretKey getSigningKey() {
    return Keys.hmacShaKeyFor(jwtSecret.getBytes());
  }

  public String generateToken(Long userId, String email, String username) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpiration);

    return Jwts.builder()
        .subject(String.valueOf(userId))
        .claim("email", email)
        .claim("username", username)
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(getSigningKey())
        .compact();
  }

  public Long getUserIdFromToken(String token) {
    return Long.parseLong(
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject());
  }

  public String getEmailFromToken(String token) {
    return (String)
        Jwts.parser()
            .verifyWith(getSigningKey())
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("email");
  }

  public boolean validateToken(String token) {
    try {
      Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token);
      return true;
    } catch (Exception iae) {
      return false;
    }
  }
}
