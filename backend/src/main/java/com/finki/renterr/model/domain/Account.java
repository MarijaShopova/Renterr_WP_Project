package com.finki.renterr.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.finki.renterr.api.validation.PasswordMatch;
import com.finki.renterr.api.validation.UniqueEmail;
import com.finki.renterr.api.validation.UniqueUsername;

import javax.persistence.*;
import javax.validation.constraints.*;

@Entity
@Table(name = "account")
@PasswordMatch(message = "Passwords don't match")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username is required")
    @Size(min = 3, message = "Username must be at least 3 characters")
    @Size(max = 15, message = "Username cannot be more than 15 characters")
    @Pattern(regexp = "^[a-z0-9._-]*$", message = "Username can contain lowercase letters, numbers, ., _, -")
    @UniqueUsername(message = "Username is taken")
    private String username;

    @NotNull(message = "Password is required")
    @Pattern(regexp = "^(?=[^A-Z]*[A-Z])(?=[^a-z]*[a-z])(?=\\D*\\d)[A-Za-z\\d!$%@#£€*?&]{8,}$",
            message = "Password must be at least 8 characters and it must contain one lowercase letter," +
                    " uppercase letter and digit")
    private String password;

    @Transient
    private String confirmPassword;

    @NotNull(message = "Email is required")
    @Email(message = "Invalid email format")
    @UniqueEmail(message = "Account with that email already exists")
    private String email;

    @Column(name = "first_name")
    @NotNull(message = "First name is required")
    @NotEmpty(message = "First name is required")
    private String firstName;

    @Column(name = "last_name")
    @NotNull(message = "Last name is required")
    @NotEmpty(message = "Last name is required")
    private String lastName;

    @NotNull(message = "Phone is required")
    @Pattern(regexp = "^[+]*[(]?[0-9]{1,4}[)]?[-\\s./0-9]*$", message = "Invalid phone number format")
    private String phone;

    private String city;

    private String country;

    @Column(name = "image_id")
    @JsonProperty("profilePicture")
    private Long imageId;

    public Account() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageId() {
        if (imageId != null) {
            return "http://localhost:8080/download_image/" + imageId;
        }
        return null;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    @JsonIgnore
    public String getConfirmPassword() {
        return confirmPassword;
    }

    @JsonProperty
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @JsonIgnore
    public Long getJustImageId() {
        return this.imageId;
    }
}
