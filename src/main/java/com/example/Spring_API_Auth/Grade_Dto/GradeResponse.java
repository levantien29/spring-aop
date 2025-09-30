package com.example.Spring_API_Auth.Grade_Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GradeResponse {
    private Long id;
    private Double score;
    private String studentName;
    private String subjectName;
}