package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Roles;
import com.example.spring81.repositories.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RolesService {

    @Autowired
    private RolesRepository rolesRepository;

    public List<Roles> findAll() {
        return rolesRepository.findAll();
    }

    public Roles findById(Long id) {
        return rolesRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id: " + id));
    }

    public Roles save(Roles role) {
        return rolesRepository.save(role);
    }

    public void deleteById(Long id) {
        // Добавим проверку: нельзя удалить роль, если она используется
        Roles role = findById(id);
        if (role.getUsers() != null && !role.getUsers().isEmpty()) {
            throw new IllegalStateException("Cannot delete role: it is assigned to one or more users.");
        }
        rolesRepository.deleteById(id);
    }
}