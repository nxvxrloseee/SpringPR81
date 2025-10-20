package com.example.spring81.repositories;

import com.example.spring81.models.Roles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long> {
    Roles findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
