package com.example.spring81.dto;

import jakarta.validation.constraints.*;


public class ServerFormDto {

    private Long serverId;
    @NotBlank(message = "Имя сервера не может быть пустым")
    private String serverName;
    @NotBlank(message = "Страна не может быть пустой")
    private String country;
    @NotBlank(message = "Город не может быть пустым")
    private String city;
    @Pattern(regexp = "^((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$",
            message = "Неверный формат IPv4")
    private String ipv4;
    private String ipv6;
    @NotNull(message = "Необходимо выбрать пользователя")
    private Long userId;
    @NotNull(message = "Необходимо выбрать статус")
    private Long statusId;
    @NotNull(message = "Необходимо выбрать ОС")
    private Long osId;
    @NotNull(message = "Необходимо выбрать тариф")
    private Long planId;

    public Long getServerId() {
        return serverId;
    }

    public void setServerId(Long serverId) {
        this.serverId = serverId;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
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

    public Long getOsId() {
        return osId;
    }

    public void setOsId(Long osId) {
        this.osId = osId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }
}