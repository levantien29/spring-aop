package com.example.Spring_API_Auth.Converter;

import com.example.Spring_API_Auth.Dto.StudentRequest;
import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Model.Student;

public class StudentConverter {

    //response
    public static StudentResponse toResponse(Student student) {
        if (student == null) return null;
        StudentResponse dto = new StudentResponse();
        dto.setId(student.getId());
        dto.setName(student.getName());
        dto.setCode(student.getCode());
        dto.setEmail(student.getEmail());
        dto.setPhone(student.getPhone());
        dto.setGender(student.getGender());
        dto.setAge(student.getAge());
        dto.setMajor(student.getMajor());
        dto.setGpa(student.getGpa());
        return dto;
    }

    //thêm
    public static Student toEntity(StudentRequest request) {
        if (request == null) return null;
        Student student = new Student();
        student.setName(request.getName());
        student.setCode(request.getCode());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setGender(request.getGender());
        student.setAge(request.getAge());
        student.setMajor(request.getMajor());
        student.setGpa(request.getGpa());
        return student;
    }
    //sửa
    public static void updateEntity(Student student, StudentRequest request) {
        if (request.getName() != null) student.setName(request.getName());
        if (request.getCode() != null) student.setCode(request.getCode());
        if (request.getEmail() != null) student.setEmail(request.getEmail());
        if (request.getPhone() != null) student.setPhone(request.getPhone());
        if (request.getGender() != null) student.setGender(request.getGender());
        if (request.getAge() != null) student.setAge(request.getAge());
        if (request.getMajor() != null) student.setMajor(request.getMajor());
        if (request.getGpa() != null) student.setGpa(request.getGpa());
    }
}