package com.example.Spring_API_Auth.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.example.Spring_API_Auth.Dto.ApiResponse;
import com.example.Spring_API_Auth.Dto.StudentRequest;
import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Dto.StudentSearchRequest;
import com.example.Spring_API_Auth.Presenter.StudentPresenter;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

// @RestController
// @RequestMapping("/api/student")
// @RequiredArgsConstructor
// public class StudentController {

//     private final StudentPresenter presenter;

//     @GetMapping
//     @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//     public ResponseEntity<Page<StudentResponse>> getAll(Pageable pageable) {
//         return presenter.presentAll(pageable);
//     }

//     @GetMapping("/{id}")
//     @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//     public ResponseEntity<StudentResponse> getById(@PathVariable Long id) {
//         return presenter.presentById(id);
//     }

//     @PostMapping
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
//         return presenter.presentCreate(request);
//     }

//     @PutMapping("/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<StudentResponse> update(@Valid @RequestBody StudentRequest request, @PathVariable Long id) {
//         return presenter.presentUpdate(request, id);
//     }

//     @DeleteMapping("/{id}")
//     @PreAuthorize("hasRole('ADMIN')")
//     public ResponseEntity<Void> delete(@PathVariable Long id) {
//         return presenter.presentDelete(id);
//     }

//     @PostMapping("/search")
//     @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
//     public ResponseEntity<Page<StudentResponse>> searchStudent(@RequestBody StudentSearchRequest request, Pageable pageable) {
//         return presenter.presentSearch(request, pageable);
//     }
// }
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentPresenter presenter;

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
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<ApiResponse<StudentResponse>> create(@Valid @RequestBody StudentRequest request) {
        var response = presenter.presentCreate(request);
        var result = response.getBody();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Thêm sinh viên thành công", result)
        );
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<StudentResponse>> update(@Valid @RequestBody StudentRequest request, @PathVariable Long id) {
        var response = presenter.presentUpdate(request, id);
        var result = response.getBody();
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Cập nhật sinh viên thành công", result)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        presenter.presentDelete(id); // thường delete không trả body
        return ResponseEntity.ok(
            new ApiResponse<>(true, "Xóa sinh viên thành công", null)
        );
    }

    @PostMapping("/search")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER', 'TEACHER')")
    public Object searchStudent(@RequestBody StudentSearchRequest request, Pageable pageable) {
        return presenter.presentSearch(request, pageable);
    }
}
