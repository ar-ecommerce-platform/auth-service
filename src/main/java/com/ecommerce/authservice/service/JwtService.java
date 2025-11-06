package com.ecommerce.authservice.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/** Handles creating and checking JWT tokens. Uses a secret key and token expiration from config. */
@Service
public class JwtService {

  private final Key signingKey;
  private final long expirationMs;

  /**
   * Initialize JwtService with secret key and expiration time.
   *
   * @param secretKey The secret key for signing tokens
   * @param expirationMs How long the token is valid (ms)
   */
  public JwtService(
      @Value("${spring.security.jwt.secret}") String secretKey,
      @Value("${spring.security.jwt.expiration-ms}") long expirationMs) {
    this.signingKey = Keys.hmacShaKeyFor(secretKey.getBytes());
    this.expirationMs = expirationMs;
  }

  /**
   * Make a new JWT Token for a user email
   *
   * @param email The user's email
   * @return The signed JWT token
   */
  public String generateToken(String email) {

    return Jwts.builder()
        .subject(email)
        .issuedAt(new Date())
        .expiration(new Date(System.currentTimeMillis() + expirationMs))
        .signWith(signingKey)
        .compact();
  }

  /**
   *  Get the email from a JWT Token
   *
   * @param token The JWT Token
   * @return The email stored in the token
   * */
    public String extractEmail(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }
}
