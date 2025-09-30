package com.example.Spring_API_Auth.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Spring_API_Auth.Presenter.SubjectPresenter;
import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/subject")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectPresenter presenter;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public Object getAll(Pageable pageable) {
        return presenter.presentAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public Object getById(@PathVariable Long id) {
        return presenter.presentById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Object create(@Valid @RequestBody SubjectRequest request) {
        return presenter.presentCreate(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public Object update(@Valid @RequestBody SubjectRequest request, @PathVariable Long id) {
        return presenter.presentUpdate(request, id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public Object delete(@PathVariable Long id) {
        return presenter.presentDelete(id);
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Object searchSubject(@RequestBody SubjectSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
