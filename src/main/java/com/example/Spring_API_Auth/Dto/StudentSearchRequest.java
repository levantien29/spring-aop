package com.example.Spring_API_Auth.Dto;

import lombok.Data;

@Data
public class StudentSearchRequest {
    private Long id;
    private String name;
    private String code;
    private String email;
    private String phone;
    private String gender;
    private Integer age;
    private String major;
    private Double gpa;
}
