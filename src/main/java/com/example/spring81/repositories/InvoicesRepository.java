package com.example.spring81.repositories;

import com.example.spring81.models.Invoices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoicesRepository extends JpaRepository<Invoices, Long> {
    List<Invoices> findByUserUserId(Long userId);
    List<Invoices> findByStatusStatusId(Long statusId);
}
