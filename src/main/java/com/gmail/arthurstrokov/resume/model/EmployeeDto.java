package com.gmail.arthurstrokov.resume.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
