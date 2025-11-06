package com.ecommerce.authservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Provides cryptography-related beans. PasswordEncoder for securely hashing passwords using BCrypt.
 */
@Configuration
public class CryptoConfig {

  /**
   * Returns a BCryptPasswordEncoder with work factor 10. Suitable for hashing and verifying user
   * passwords.
   *
   * @return PasswordEncoder instance
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder(10);
  }
}
