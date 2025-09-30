package com.example.Spring_API_Auth.Subject_Dto;

import lombok.Data;

@Data
public class SubjectSearchRequest {
    private Long id;
    private String subjectName;
    private Integer credit;
    private Long teacherId;
}