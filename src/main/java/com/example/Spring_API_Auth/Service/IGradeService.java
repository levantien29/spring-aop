package com.example.Spring_API_Auth.Service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeResponse;
import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;

public interface IGradeService {
    Page<GradeResponse> getAll(Pageable pageable);

    GradeResponse getById(Long id);

    GradeResponse create(GradeRequest request);

    GradeResponse update(GradeRequest request, Long id);

    void delete(Long id);

    Page<GradeResponse> searchGrade(GradeSearchRequest request, Pageable pageable);
}