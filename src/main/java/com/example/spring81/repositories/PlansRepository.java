package com.example.spring81.repositories;

import com.example.spring81.models.Plans;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlansRepository extends JpaRepository<Plans, Long> {
    List<Plans> findByStatusStatusId(Long statusId);
}
