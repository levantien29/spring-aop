package com.example.Spring_API_Auth.Controller;

import com.example.Spring_API_Auth.Dto.ApiResponse;
import com.example.Spring_API_Auth.Presenter.SubjectPresenter;
import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody SubjectRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Thêm môn học thành công", presenter.presentCreate(request))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@Valid @RequestBody SubjectRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cập nhật môn học thành công", presenter.presentUpdate(request, id))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Xóa môn học thành công", presenter.presentDelete(id))
        );
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Object searchSubject(@RequestBody SubjectSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
