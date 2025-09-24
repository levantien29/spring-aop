package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Spring_API_Auth.Converter.StudentConverter;
import com.example.Spring_API_Auth.Dto.StudentRequest;
import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Dto.StudentSearchRequest;
import com.example.Spring_API_Auth.Exception.BadRequestException;
import com.example.Spring_API_Auth.Exception.ResourceNotFoundException;
import com.example.Spring_API_Auth.Model.Student;
import com.example.Spring_API_Auth.Repository.StudentReposiory;
import com.example.Spring_API_Auth.Specification.StudentSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentService implements IStudentService {

    private final StudentReposiory repository;

    @Override
    public Page<StudentResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(StudentConverter::toResponse);
    }

    @Override
    public StudentResponse getById(Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id: " + id, "id"));
        return StudentConverter.toResponse(student);
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        if (repository.existsByEmail(request.getEmail())) {
            throw new BadRequestException("Email đã tồn tại", "email");
        }
        if (repository.existsByCode(request.getCode())) {
            throw new BadRequestException("Mã sinh viên đã tồn tại", "code");
        }

        Student student = StudentConverter.toEntity(request);
        return StudentConverter.toResponse(repository.save(student));
    }

    @Override
    public StudentResponse update(StudentRequest request, Long id) {
        Student student = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id: " + id, "id"));

        if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
            throw new BadRequestException("Email đã tồn tại", "email");
        }
        if (repository.existsByCodeAndIdNot(request.getCode(), id)) {
            throw new BadRequestException("Mã sinh viên đã tồn tại", "code");
        }

        StudentConverter.updateEntity(student, request);
        return StudentConverter.toResponse(repository.save(student));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy sinh viên có id: " + id, "id");
        }
        repository.deleteById(id);
    }

    @Override
    public Page<StudentResponse> searchStudent(StudentSearchRequest request, Pageable pageable) {
        Page<Student> page = repository.findAll(StudentSpecification.build(request), pageable);
        return page.map(StudentConverter::toResponse);
    }
}
