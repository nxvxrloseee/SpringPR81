package com.example.spring81.dto;

import jakarta.validation.constraints.*;

public class PaymentFormDto {
    private Long paymentId;
    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    @NotNull(message = "ID статуса обязателен")
    private Long statusId;

    @NotNull(message = "Сумма обязательна")
    @DecimalMin(value = "0.01", message = "Сумма должна быть положительной")
    private Double amount;


    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }
}