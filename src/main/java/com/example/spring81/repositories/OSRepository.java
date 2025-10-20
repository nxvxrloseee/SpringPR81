package com.example.spring81.repositories;

import com.example.spring81.models.OS;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OSRepository extends JpaRepository<OS, Long> { }
