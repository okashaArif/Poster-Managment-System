package com.project.project.repository;

import com.project.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query methods can be defined here if needed
    User findByUsername(String username);
    User findByEmail(String email);

}