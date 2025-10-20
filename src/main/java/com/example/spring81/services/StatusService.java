package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Status;
import com.example.spring81.repositories.StatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {

    @Autowired
    private StatusRepository statusRepository;

    public List<Status> findAll() {
        return statusRepository.findAll();
    }

    public Status findById(Long id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found with id: " + id));
    }

    public Status save(Status status) {
        return statusRepository.save(status);
    }

    public void deleteById(Long id) {
        if (!statusRepository.existsById(id)) {
            throw new ResourceNotFoundException("Status not found with id: " + id);
        }
        statusRepository.deleteById(id);
    }
}