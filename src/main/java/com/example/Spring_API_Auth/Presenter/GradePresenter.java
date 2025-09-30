package com.example.Spring_API_Auth.Presenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeResponse;
import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;
import com.example.Spring_API_Auth.Service.IGradeService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GradePresenter {

    private final IGradeService service;

    public ResponseEntity<Page<GradeResponse>> presentAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    public ResponseEntity<GradeResponse> presentById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<GradeResponse> presentCreate(GradeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    public ResponseEntity<GradeResponse> presentUpdate(GradeRequest request, Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    public ResponseEntity<Void> presentDelete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<GradeResponse>> presentSearch(GradeSearchRequest request, Pageable pageable) {
        return ResponseEntity.ok(service.searchGrade(request, pageable));
    }
}