package com.example.spring81.repositories;

import com.example.spring81.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserModelRepository extends JpaRepository<UserModel, Long> {
    UserModel findByUsername(String username);
    boolean existsByEmail(String email);
}