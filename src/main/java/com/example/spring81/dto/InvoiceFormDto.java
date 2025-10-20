package com.example.spring81.dto;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

public class InvoiceFormDto {
    private Long invoiceId;
    @NotNull(message = "ID пользователя обязателен")
    private Long userId;

    @NotNull(message = "ID статуса обязателен")
    private Long statusId;

    @NotNull(message = "Сумма обязательна")
    @DecimalMin(value = "0.01", message = "Сумма должна быть положительной")
    private Double amount;

    @NotNull(message = "Дата начала периода обязательна")
    private LocalDate periodStart;

    @NotNull(message = "Дата окончания периода обязательна")
    private LocalDate periodEnd;


    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
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

    public LocalDate getPeriodStart() {
        return periodStart;
    }

    public void setPeriodStart(LocalDate periodStart) {
        this.periodStart = periodStart;
    }

    public LocalDate getPeriodEnd() {
        return periodEnd;
    }

    public void setPeriodEnd(LocalDate periodEnd) {
        this.periodEnd = periodEnd;
    }
}