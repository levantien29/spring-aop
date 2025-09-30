package com.example.Spring_API_Auth.Grade_Dto;

import lombok.Data;

@Data
public class GradeSearchRequest {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private Double Score;
}