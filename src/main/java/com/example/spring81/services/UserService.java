package com.example.spring81.services;

import com.example.spring81.config.ResourceNotFoundException;
import com.example.spring81.models.Roles;
import com.example.spring81.models.UserModel;
import com.example.spring81.dto.UserFormDto; // Импорт DTO
import com.example.spring81.repositories.RolesRepository;
import com.example.spring81.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserModelRepository userRepo;

    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private BCryptPasswordEncoder encoder;


    public void registerUser(UserModel user) {
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        if (user.getBalance() == null) {
            user.setBalance(0.0);
        }
        user.setCreatedAt(LocalDateTime.now());
        userRepo.save(user);
    }


    public UserFormDto getFormDtoById(Long id) {
        UserModel user = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        UserFormDto dto = new UserFormDto();
        dto.setUserId(user.getUserId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setBalance(user.getBalance());
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getRoleId());
        }
        dto.setPassword("");

        return dto;
    }


    @Transactional
    public void adminSaveFromDto(UserFormDto dto) {
        Roles role = rolesRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + dto.getRoleId()));

        UserModel user = new UserModel();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setBalance(dto.getBalance() != null ? dto.getBalance() : 0.0);
        user.setRole(role);


        if (dto.getPassword() == null || dto.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty for a new user.");
        }
        user.setPassword(encoder.encode(dto.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        userRepo.save(user);
    }


    @Transactional
    public void adminUpdateFromDto(Long id, UserFormDto dto) {
        UserModel existingUser = findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        Roles role = rolesRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found: " + dto.getRoleId()));

        existingUser.setUsername(dto.getUsername());
        existingUser.setEmail(dto.getEmail());
        existingUser.setBalance(dto.getBalance());
        existingUser.setRole(role);


        String newPassword = dto.getPassword();
        if (newPassword != null && !newPassword.isEmpty()) {
            existingUser.setPassword(encoder.encode(newPassword));
        }


        userRepo.save(existingUser);
    }



    public List<UserModel> findAll() {
        return userRepo.findAll();
    }

    public Optional<UserModel> findById(Long id) {
        return userRepo.findById(id);
    }

    public void save(UserModel user) {
        userRepo.save(user);
    }

    public void deleteById(Long id) {
        if (!userRepo.existsById(id)) {
            throw new ResourceNotFoundException("User not found: " + id);
        }
        userRepo.deleteById(id);
    }
}