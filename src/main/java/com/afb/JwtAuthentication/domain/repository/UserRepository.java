package com.afb.JwtAuthentication.domain.repository;

import com.afb.JwtAuthentication.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Long> {
    User getUserByUsername(String username);
    List<User> findByUsername(String username);
}
