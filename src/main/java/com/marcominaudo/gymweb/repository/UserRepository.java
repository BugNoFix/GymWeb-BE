package com.marcominaudo.gymweb.repository;

import com.marcominaudo.gymweb.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String username);
}
