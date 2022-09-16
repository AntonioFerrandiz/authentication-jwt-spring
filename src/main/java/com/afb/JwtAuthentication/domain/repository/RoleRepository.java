package com.afb.JwtAuthentication.domain.repository;

import com.afb.JwtAuthentication.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
