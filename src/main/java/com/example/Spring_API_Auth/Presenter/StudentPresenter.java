package com.example.Spring_API_Auth.Presenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.Spring_API_Auth.Dto.StudentRequest;
import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Dto.StudentSearchRequest;
import com.example.Spring_API_Auth.Service.IStudentService;

import lombok.RequiredArgsConstructor;
@Component
@RequiredArgsConstructor
public class StudentPresenter {

    private final IStudentService service;

    public ResponseEntity<Page<StudentResponse>> presentAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    // Cho View (Thymeleaf)
    public Page<StudentResponse> viewAll(Pageable pageable) {
        return service.getAll(pageable);
    }

    public ResponseEntity<StudentResponse> presentById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<StudentResponse> presentCreate(StudentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    public ResponseEntity<StudentResponse> presentUpdate(StudentRequest request, Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    public ResponseEntity<Void> presentDelete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<StudentResponse>> presentSearch(StudentSearchRequest request, Pageable pageable) {
        return ResponseEntity.ok(service.searchStudent(request, pageable));
    }
}
