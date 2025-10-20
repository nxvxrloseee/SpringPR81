package com.example.spring81.models;

import jakarta.persistence.*;

@Entity
@Table(name = "plans")
public class Plans {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plan_id")
    private Long planId;

    private String planName;
    private Integer cpuCores;
    private Integer ramGb;
    private Integer ssdGb;
    private Double pricePerDay;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "status_id")
    private Status status;

    public Plans(Long planId, String planName, Integer cpuCores, Integer ramGb, Integer ssdGb, Double pricePerDay, Status status) {
        this.planId = planId;
        this.planName = planName;
        this.cpuCores = cpuCores;
        this.ramGb = ramGb;
        this.ssdGb = ssdGb;
        this.pricePerDay = pricePerDay;
        this.status = status;
    }

    public Plans() {}

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}