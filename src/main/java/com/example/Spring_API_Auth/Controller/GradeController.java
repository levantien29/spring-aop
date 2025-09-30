package com.example.Spring_API_Auth.Controller;

import com.example.Spring_API_Auth.Dto.ApiResponse;
import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;
import com.example.Spring_API_Auth.Presenter.GradePresenter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody GradeRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Thêm điểm thành công", presenter.presentCreate(request))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@Valid @RequestBody GradeRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cập nhật điểm thành công", presenter.presentUpdate(request, id))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Xóa điểm thành công", presenter.presentDelete(id))
        );
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN','USER','TEACHER')")
    public Object searchGrade(@RequestBody GradeSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
