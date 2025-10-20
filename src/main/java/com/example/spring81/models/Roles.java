package com.example.spring81.models;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Column(nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
    private List<UserModel> users;

    public Roles(Long roleId, String roleName, List<UserModel> users) {
        this.roleId = roleId;
        this.roleName = roleName;
        this.users = users;
    }

    public Roles() {}


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public List<UserModel> getUsers() {
        return users;
    }

    public void setUsers(List<UserModel> users) {
        this.users = users;
    }
}