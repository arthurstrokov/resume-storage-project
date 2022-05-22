package com.gmail.arthurstrokov.resume.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO object
 *
 * @author Arthur Strokov
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is required. Enter your first name")
    private String firstName;
    @NotBlank(message = "Last name is required. Enter your last name")
    private String lastName;
    @NotBlank(message = "Phone number is required. Enter your phone number")
    private String phone;
    @NotBlank(message = "Email is required. Enter your email")
    @Email
    @Column(unique=true)
    private String email;

    public Employee(String firstName, String lastName, String phone, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.email = email;
    }
}

