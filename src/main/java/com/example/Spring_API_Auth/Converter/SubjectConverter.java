package com.example.Spring_API_Auth.Converter;


import com.example.Spring_API_Auth.Model.Subject;
import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectResponse;

public class SubjectConverter {

    // response
    public static SubjectResponse toResponse(Subject subject) {
        if (subject == null) return null;
        SubjectResponse dto = new SubjectResponse();
        dto.setId(subject.getId());
        dto.setSubjectName(subject.getSubjectName());
        dto.setCredit(subject.getCredit());

        if (subject.getTeacher() != null) {
            dto.setTeacherName(subject.getTeacher().getName());
            dto.setTeacherId(subject.getTeacher().getId());
        }
        return dto;
    }

    // thêm
    public static Subject toEntity(SubjectRequest request) {
        if (request == null) return null;
        Subject subject = new Subject();
        subject.setSubjectName(request.getSubjectName());
        subject.setCredit(request.getCredit());
        return subject;
    }

    // sửa
    public static void updateEntity(Subject subject, SubjectRequest request) {
        if (request.getSubjectName() != null) subject.setSubjectName(request.getSubjectName());
        if (request.getCredit() != 0) subject.setCredit(request.getCredit());
    }
}