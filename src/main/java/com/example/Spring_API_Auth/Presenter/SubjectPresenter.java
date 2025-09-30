package com.example.Spring_API_Auth.Presenter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.example.Spring_API_Auth.Service.ISubjectService;
import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectResponse;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SubjectPresenter {

    private final ISubjectService service;

    public ResponseEntity<Page<SubjectResponse>> presentAll(Pageable pageable) {
        return ResponseEntity.ok(service.getAll(pageable));
    }

    public ResponseEntity<SubjectResponse> presentById(Long id) {
        return ResponseEntity.ok(service.getById(id));
    }

    public ResponseEntity<SubjectResponse> presentCreate(SubjectRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    public ResponseEntity<SubjectResponse> presentUpdate(SubjectRequest request, Long id) {
        return ResponseEntity.ok(service.update(request, id));
    }

    public ResponseEntity<Void> presentDelete(Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Page<SubjectResponse>> presentSearch(SubjectSearchRequest request, Pageable pageable) {
        return ResponseEntity.ok(service.searchSubject(request, pageable));
    }
}

