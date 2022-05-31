package com.gmail.arthurstrokov.resume.entity;

import lombok.*;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;
import java.util.Objects;

/**
 * Entity model for storing in database
 *
 * @author Arthur Strokov
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "First name is required. Enter first name")
    private String firstName;
    @NotBlank(message = "Last name is required. Enter last name")
    private String lastName;
    @Pattern(regexp = "\\d{3}-\\d{9}", message = "Phone number should be like 375-291555376")
    @NotBlank(message = "Phone number is required. Enter phone number")
    private String phone;
    @Formula(value = "date_part('year', age(birth_date))")
    private Integer age;
    @Past
    @Temporal(TemporalType.DATE)
    @NotNull(message = "Birth date is required. Enter birth date: 'yyyy-MM-dd' ")
    private Date birthDate;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Gender is required. Enter gender: 'MALE' or 'FEMALE' ")
    private Gender gender;
    @Column(unique = true)
    @Email(message = "Email should be like arthurstrokov@gmail.com")
    @NotBlank(message = "Email is required. Enter email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) && Objects.equals(phone, employee.phone) && Objects.equals(age, employee.age) && Objects.equals(birthDate, employee.birthDate) && gender == employee.gender && Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, phone, age, birthDate, gender, email);
    }
}
