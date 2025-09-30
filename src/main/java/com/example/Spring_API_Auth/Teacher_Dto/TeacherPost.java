package com.example.Spring_API_Auth.Teacher_Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeacherPost {
    private Long id;
    private String name;
    private String email;
    private int phone;
    private String department;
}
