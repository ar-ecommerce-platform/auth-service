package com.ecommerce.authservice.service;

import com.ecommerce.authservice.dto.LoginRequest;
import com.ecommerce.authservice.dto.RegisterRequest;
import com.ecommerce.authservice.entity.User;
import com.ecommerce.authservice.repository.UserRepository;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/** Handles authentication operations: register and login. */
@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  /** Constructors for userRepository, and passwordEncoder */
  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  /** Registers a new user with hashed password */
  public void register(RegisterRequest registerRequest) {
    if (userRepository.existsByEmail(registerRequest.email())) {
      throw new IllegalArgumentException("Email already exists");
    }

    User newUser =
        User.builder()
            .email(registerRequest.email())
            .password(passwordEncoder.encode(registerRequest.password()))
            .role("USER")
            .build();

    userRepository.save(newUser);
  }

  /**
   * Verifies credentials for login.
   *
   * @return true if authentication succeeds
   */
  public boolean login(LoginRequest loginRequest) {
    var user =
        userRepository
            .findByEmail(loginRequest.email())
            .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials"));

    boolean matches = passwordEncoder.matches(loginRequest.password(), user.getPassword());
    if (!matches) {
      throw new BadCredentialsException("Invalid credentials");
    }

    return true;
  }
}
