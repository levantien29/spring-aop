package com.example.Spring_API_Auth.Converter;

import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeResponse;
import com.example.Spring_API_Auth.Model.Grade;

public class GradeConverter {

    // response
    public static GradeResponse toResponse(Grade grade) {
        if (grade == null) return null;
        GradeResponse dto = new GradeResponse();
        dto.setId(grade.getId());
        dto.setScore(grade.getScore());

        if (grade.getStudent() != null) {
            dto.setStudentName(grade.getStudent().getName());
        }
        if (grade.getSubject() != null) {
            dto.setSubjectName(grade.getSubject().getSubjectName()); 
        }
        return dto;
    }

    // thêm
    public static Grade toEntity(GradeRequest request) {
        if (request == null) return null;
        Grade grade = new Grade();
        grade.setScore(request.getScore());
        return grade;
    }

    // sửa
    public static void updateEntity(Grade grade, GradeRequest request) {
        if (request.getScore() != null) grade.setScore(request.getScore());
    }
}