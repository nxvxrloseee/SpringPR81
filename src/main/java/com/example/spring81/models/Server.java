package com.example.spring81.models;

import jakarta.persistence.*;

@Entity
@Table(name = "servers")
public class Server {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "server_id")
    private Long serverId;

    @Column(nullable = false)
    private String serverName;

    private String country;
    private String city;
    private String ipv4;
    private String ipv6;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "status_id")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "os_id")
    private OS os;

    @ManyToOne
    @JoinColumn(name = "plan_id")
    private Plans plan;

    public Server(Long serverId, String serverName, String country, String city, String ipv4, String ipv6, UserModel user, Status status, OS os, Plans plan) {
        this.serverId = serverId;
        this.serverName = serverName;
        this.country = country;
        this.city = city;
        this.ipv4 = ipv4;
        this.ipv6 = ipv6;
        this.user = user;
        this.status = status;
        this.os = os;
        this.plan = plan;
    }

    public Server() {}

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

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public OS getOs() {
        return os;
    }

    public void setOs(OS os) {
        this.os = os;
    }

    public Plans getPlan() {
        return plan;
    }

    public void setPlan(Plans plan) {
        this.plan = plan;
    }
}