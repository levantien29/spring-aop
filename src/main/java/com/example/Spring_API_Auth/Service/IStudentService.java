package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.Spring_API_Auth.Dto.StudentRequest;
import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Dto.StudentSearchRequest;

public interface IStudentService {
    public Page<StudentResponse> getAll(Pageable pageable);
    public StudentResponse getById(Long id);
    public StudentResponse create(StudentRequest request);
    public StudentResponse update(StudentRequest request, Long id);
    public void delete(Long id);
    public Page<StudentResponse> searchStudent(StudentSearchRequest request, Pageable pageable);
}
