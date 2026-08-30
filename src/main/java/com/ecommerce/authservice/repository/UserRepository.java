package com.ecommerce.authservice.repository;

import com.ecommerce.authservice.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/** Provides database access for User entities. */
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByEmail(String email);

  boolean existsByEmail(String email);
}
