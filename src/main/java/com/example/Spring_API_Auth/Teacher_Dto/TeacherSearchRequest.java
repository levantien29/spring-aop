package com.example.Spring_API_Auth.Teacher_Dto;

import lombok.Data;

@Data
public class TeacherSearchRequest {
    private Long id;
    private String name;
    private String email;
    private Integer phone;
    private String department;
}
