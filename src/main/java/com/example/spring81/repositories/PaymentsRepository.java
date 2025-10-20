package com.example.spring81.repositories;

import com.example.spring81.models.Payments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payments, Long> {
    List<Payments> findByUserUserId(Long userId);
    List<Payments> findByStatusStatusId(Long statusId);
}
