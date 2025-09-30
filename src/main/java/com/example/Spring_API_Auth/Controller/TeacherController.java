package com.example.Spring_API_Auth.Controller;

import com.example.Spring_API_Auth.Dto.ApiResponse;
import com.example.Spring_API_Auth.Presenter.TeacherPresenter;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherRequest;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherSearchRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherPresenter presenter;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public Object getAll(Pageable pageable) {
        return presenter.presentAll(pageable);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public Object getById(@PathVariable Long id) {
        return presenter.presentById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> create(@Valid @RequestBody TeacherRequest request) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Thêm giáo viên thành công", presenter.presentCreate(request))
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> update(@Valid @RequestBody TeacherRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Cập nhật giáo viên thành công", presenter.presentUpdate(request, id))
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<?>> delete(@PathVariable Long id) {
        return ResponseEntity.ok(
                new ApiResponse<>(true, "Xóa giáo viên thành công", presenter.presentDelete(id))
        );
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public Object searchTeacher(@RequestBody TeacherSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
