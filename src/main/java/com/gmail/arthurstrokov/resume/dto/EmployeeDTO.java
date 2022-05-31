package com.gmail.arthurstrokov.resume.dto;

import com.gmail.arthurstrokov.resume.entity.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private Integer age;
    private Date birthDate;
    private Gender gender;
    private String email;
}
