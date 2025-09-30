package com.example.Spring_API_Auth.Teacher_Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.Spring_API_Auth.Subject_Dto.SubjectResponse;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TeacherResponse {
    private Long id;
    private String name;
    private String email;
    private int phone;
    private String department;

    // Danh sách môn dạy
    private List<String> subjects;
}