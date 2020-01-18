package com.example.study1.domain;

import javax.persistence.*;
import javax.validation.constraints.Email;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 20, unique = true)
    private String userId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Email
    private String email;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public boolean matchPassword(String inPutPassword) {
        if (inPutPassword == null) {
            return false;
        }
        return inPutPassword.equals(password);
    }

    public boolean matchId(Long inputId) {
        if (inputId == null) {
            return false;
        }
        return id.equals(inputId);
    }

    public Long getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void update(User newUser) {
        this.userId = newUser.userId;
        this.password = newUser.password;
        this.name = newUser.name;
        this.email = newUser.email;
    }
}
