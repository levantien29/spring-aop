package com.example.Spring_API_Auth.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;
import com.example.Spring_API_Auth.Presenter.GradePresenter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/grade")
@RequiredArgsConstructor
public class GradeController {

    private final GradePresenter presenter;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','USER','TEACHER')")
    public Object getAll(Pageable pageable) {
        return presenter.presentAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER','TEACHER')")
    public Object getById(@PathVariable Long id) {
        return presenter.presentById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public Object create(@Valid @RequestBody GradeRequest request) {
        return presenter.presentCreate(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public Object update(@Valid @RequestBody GradeRequest request, @PathVariable Long id) {
        return presenter.presentUpdate(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object delete(@PathVariable Long id) {
        return presenter.presentDelete(id);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER','TEACHER')")
    public Object searchGrade(@RequestBody GradeSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
