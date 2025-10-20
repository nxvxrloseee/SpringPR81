package com.example.spring81.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "os")
public class OS {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "os_id")
    private Long osId;

    @NotBlank(message = "Название операционной системы не может быть пустым")
    @Column(nullable = false)
    private String osName;

    private String version;
    private String family;

    public OS(Long osId, String osName, String version, String family) {
        this.osId = osId;
        this.osName = osName;
        this.version = version;
        this.family = family;
    }

    public OS() {}

    public Long getOsId() {
        return osId;
    }

    public void setOsId(Long osId) {
        this.osId = osId;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }
}