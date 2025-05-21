package com.ilpalazzo.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "logininfo")
public class LoginInfo {

    @Id
    @Column(name = "user_id", length = 128)
    private String userId;

    @Column(nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 20)
    private String password;

    @Column(nullable = false, length = 30)
    private String role;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LoginInfo() {
    // no-arg constructor needed by JPA
    }

    public LoginInfo(String userId, String username, String password, String role) {
    this.userId = userId;
    this.username = username;
    this.password = password;
    this.role = role;
    }

}
