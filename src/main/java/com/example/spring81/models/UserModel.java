package com.example.spring81.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @NotBlank(message = "Username cannot be blank")
    @Size(min = 3, max = 30, message = "Username must be from 3 to 30 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull(message = "Role must be selected")
    private Roles role;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    @Column(nullable = false, unique = true)
    private String email;


    @Size(min = 6, max = 100, message = "Password must be from 6 to 100 characters")
    @Column(nullable = false)
    private String password;

    @PositiveOrZero(message = "Balance cannot be negative")
    private Double balance = 0.0;

    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public UserModel() {}

    public UserModel(Long userId, String username, Roles role, String email, String password, Double balance, LocalDateTime createdAt) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.email = email;
        this.password = password;
        this.balance = balance;
        this.createdAt = createdAt;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
