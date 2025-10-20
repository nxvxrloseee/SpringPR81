package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.OS;
import com.example.spring81.repositories.OSRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OSService {

    @Autowired
    private OSRepository osRepository;

    public List<OS> findAll() {
        return osRepository.findAll();
    }

    public OS findById(Long id) {
        return osRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("OS not found with id: " + id));
    }

    public OS save(OS os) {
        return osRepository.save(os);
    }

    public void deleteById(Long id) {
        if (!osRepository.existsById(id)) {
            throw new ResourceNotFoundException("OS not found with id: " + id);
        }
        osRepository.deleteById(id);
    }
}