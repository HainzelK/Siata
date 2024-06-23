package com.data.siata.repository;

import com.data.siata.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    Optional<User> findOneByEmailAndPassword(String email, String password);
    Optional<User> findByEmail(String email);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    boolean existsByNoTelp(String noTelp);
    Optional<User> findByRole(String role);
}
