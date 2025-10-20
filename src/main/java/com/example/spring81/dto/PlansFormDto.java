package com.example.spring81.dto;

import jakarta.validation.constraints.*;

public class PlansFormDto {
    private Long planId; // Важно для update
    @NotBlank(message = "Название плана не может быть пустым")
    @Size(max = 100, message = "Название плана не должно превышать 100 символов")
    private String planName;

    @NotNull(message = "Количество ядер CPU обязательно")
    @Min(value = 1, message = "Ядер CPU должно быть не менее 1")
    private Integer cpuCores;

    @NotNull(message = "Объем RAM обязателен")
    @Min(value = 1, message = "Объем RAM должен быть не менее 1 ГБ")
    private Integer ramGb;

    @NotNull(message = "Объем SSD обязателен")
    @Min(value = 10, message = "Объем SSD должен быть не менее 10 ГБ")
    private Integer ssdGb;

    @NotNull(message = "Цена обязательна")
    @DecimalMin(value = "0.01", message = "Цена должна быть положительной")
    private Double pricePerDay;

    @NotNull(message = "Статус обязателен")
    private Long statusId; // Связь

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Integer getCpuCores() {
        return cpuCores;
    }

    public void setCpuCores(Integer cpuCores) {
        this.cpuCores = cpuCores;
    }

    public Integer getRamGb() {
        return ramGb;
    }

    public void setRamGb(Integer ramGb) {
        this.ramGb = ramGb;
    }

    public Integer getSsdGb() {
        return ssdGb;
    }

    public void setSsdGb(Integer ssdGb) {
        this.ssdGb = ssdGb;
    }

    public Double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(Double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }
}