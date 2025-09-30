package com.example.Spring_API_Auth.Converter;

import com.example.Spring_API_Auth.Model.Teacher;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherRequest;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherResponse;
import com.example.Spring_API_Auth.Model.Subject;

public class TeacherConverter {

    // response
    public static TeacherResponse toResponse(Teacher teacher) {
        if (teacher == null)
            return null;
        TeacherResponse dto = new TeacherResponse();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setEmail(teacher.getEmail());
        dto.setPhone(teacher.getPhone());
        dto.setDepartment(teacher.getDepartment());

        if (teacher.getSubjects() != null) {
            dto.setSubjects(
                    teacher.getSubjects().stream()
                            .map(Subject::getSubjectName)
                            .toList());
        }

        return dto;
    }

    // thêm
    public static Teacher toEntity(TeacherRequest request) {
        if (request == null)
            return null;
        Teacher teacher = new Teacher();
        teacher.setName(request.getName());
        teacher.setEmail(request.getEmail());
        teacher.setPhone(request.getPhone());
        teacher.setDepartment(request.getDepartment());
        return teacher;
    }

    // sửa
    public static void updateEntity(Teacher teacher, TeacherRequest request) {
        if (request.getName() != null)
            teacher.setName(request.getName());
        if (request.getEmail() != null)
            teacher.setEmail(request.getEmail());
        if (request.getPhone() != 0)
            teacher.setPhone(request.getPhone());
        if (request.getDepartment() != null)
            teacher.setDepartment(request.getDepartment());
    }
}
