package com.example.Spring_API_Auth.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class StudentResponse {
    private Long id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String gender;
    private int age;
    private String major;
    private double gpa;
}
