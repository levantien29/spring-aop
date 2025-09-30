package com.example.Spring_API_Auth.Presenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.Spring_API_Auth.Service.ITeacherService;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherRequest;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherResponse;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherSearchRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TeacherPresenter {

    private final ITeacherService service;

    public ResponseEntity<Page<TeacherResponse>> presentAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    public ResponseEntity<TeacherResponse> presentById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<TeacherResponse> presentCreate(TeacherRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    public ResponseEntity<TeacherResponse> presentUpdate(TeacherRequest request, Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    public ResponseEntity<Void> presentDelete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<TeacherResponse>> presentSearch(TeacherSearchRequest request, Pageable pageable) {
        return ResponseEntity.ok(service.searchTeacher(request, pageable));
    }
}