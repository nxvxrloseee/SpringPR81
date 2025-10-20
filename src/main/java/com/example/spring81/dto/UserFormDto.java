package com.example.spring81.dto;

import jakarta.validation.constraints.*;

public class UserFormDto {

    private Long userId;

    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Size(min = 3, max = 30, message = "Имя пользователя должно содержать от 3 до 30 символов")
    private String username;

    @NotNull(message = "Необходимо выбрать роль")
    private Long roleId;

    @NotBlank(message = "Email не может быть пустым")
    @Email(message = "Email должен быть действительным")
    private String email;


    @Size(min = 6, max = 100, message = "Пароль должен содержать от 6 до 100 символов")
    private String password;

    @PositiveOrZero(message = "Баланс не может быть отрицательным")
    private Double balance = 0.0;

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

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
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
}