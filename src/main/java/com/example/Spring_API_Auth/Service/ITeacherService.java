package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.Spring_API_Auth.Teacher_Dto.TeacherRequest;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherResponse;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherSearchRequest;

public interface ITeacherService {
    Page<TeacherResponse> getAll(Pageable pageable);

    TeacherResponse getById(Long id);

    TeacherResponse create(TeacherRequest request);

    TeacherResponse update(TeacherRequest request, Long id);

    void delete(Long id);

    Page<TeacherResponse> searchTeacher(TeacherSearchRequest request, Pageable pageable);
}