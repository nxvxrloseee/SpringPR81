package com.example.spring81.repositories;

import com.example.spring81.models.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> { }
