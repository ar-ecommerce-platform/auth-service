package com.ecommerce.authservice.controller;

import com.ecommerce.authservice.dto.LoginRequest;
import com.ecommerce.authservice.dto.RegisterRequest;
import com.ecommerce.authservice.dto.TokenResponse;
import com.ecommerce.authservice.service.AuthService;
import com.ecommerce.authservice.service.JwtService;
import io.jsonwebtoken.Claims;
import jakarta.validation.Valid;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/** Authentication endpoints: register, login, and token inspection. */
@RestController
@RequestMapping("/auth")
public class AuthController {

  private static final String BEARER_PREFIX = "Bearer ";

  private final AuthService authService;
  private final JwtService jwtService;

  public AuthController(AuthService authService, JwtService jwtService) {
    this.authService = authService;
    this.jwtService = jwtService;
  }

  /** Registers a new account. */
  @PostMapping("/register")
  @ResponseStatus(HttpStatus.CREATED)
  public void register(@Valid @RequestBody RegisterRequest request) {
    authService.register(request);
  }

  /** Exchanges credentials for a signed token. */
  @PostMapping("/login")
  public TokenResponse login(@Valid @RequestBody LoginRequest request) {
    return authService.login(request);
  }

  /** Validates a bearer token and echoes its subject and roles. */
  @GetMapping("/validate")
  public ResponseEntity<Map<String, Object>> validate(
      @RequestHeader("Authorization") String authorization) {
    Claims claims = jwtService.parse(stripBearer(authorization));
    return ResponseEntity.ok(
        Map.of("subject", claims.getSubject(), "roles", claims.getOrDefault("roles", "[]")));
  }

  private static String stripBearer(String header) {
    if (header != null && header.startsWith(BEARER_PREFIX)) {
      return header.substring(BEARER_PREFIX.length());
    }
    return header;
  }
}
