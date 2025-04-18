package com.epam.capstone.model;

import com.epam.capstone.utill.ValidationGroups;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "person")
public class Person {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    @Size(min = 2, max = 200, message = "name must be between 2 and 200 symbols", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    @NotEmpty(message = "name can not be empty", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private String name;

    @Column(name = "surname")
    @NotEmpty(message = "surname can not be empty", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private String surname;

    @Column(name = "email")
    @Email(groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    @NotEmpty(message = "email can not be empty", groups = {ValidationGroups.CreateGroup.class, ValidationGroups.UpdateGroup.class})
    private String email;

    @Column(name = "username", unique = true)
    @NotEmpty(message = "username can not be empty", groups = {ValidationGroups.CreateGroup.class})
    private String username;

    @Column(name = "password")
    @NotEmpty(message = "password can not be empty", groups = {ValidationGroups.CreateGroup.class})
    private String password;

    @Column(name = "created_at")
    @Temporal(value = TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private Date createdAt;

    @Column(name = "balance")
    private Double balance;

    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "person")
    private List<Purchase> purchases = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<Purchase> getPurchases() {
        return purchases;
    }

    public void setPurchases(List<Purchase> purchases) {
        this.purchases = purchases;
    }
}
