package com.example.spring81.repositories;

import com.example.spring81.models.Server;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ServerModelRepository extends JpaRepository<Server, Long> {
    List<Server> findByUserUserId(Long userId);
    List<Server> findByStatusStatusId(Long statusId);
}
