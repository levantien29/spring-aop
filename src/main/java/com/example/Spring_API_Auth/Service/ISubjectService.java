    package com.example.Spring_API_Auth.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectResponse;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;

public interface ISubjectService {
    Page<SubjectResponse> getAll(Pageable pageable);

    SubjectResponse getById(Long id);

    SubjectResponse create(SubjectRequest request);

    SubjectResponse update(SubjectRequest request, Long id);

    void delete(Long id);

    Page<SubjectResponse> searchSubject(SubjectSearchRequest request, Pageable pageable);
}