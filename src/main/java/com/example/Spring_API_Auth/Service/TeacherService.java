package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Spring_API_Auth.Converter.TeacherConverter;
import com.example.Spring_API_Auth.Exception.BadRequestException;
import com.example.Spring_API_Auth.Exception.ResourceNotFoundException;
import com.example.Spring_API_Auth.Model.Teacher;
import com.example.Spring_API_Auth.Repository.TeacherRepository;
import com.example.Spring_API_Auth.Specification.TeacherSpecification;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherRequest;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherResponse;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherSearchRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TeacherService implements ITeacherService {

    private final TeacherRepository repository;

    @Override
    public Page<TeacherResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(TeacherConverter::toResponse);
    }

    @Override
    public TeacherResponse getById(Long id) {
        Teacher teacher = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + id, "id"));
        return TeacherConverter.toResponse(teacher);
    }

    @Override
public TeacherResponse create(TeacherRequest request) {
    // Check email đã tồn tại chưa
    if (repository.existsByEmail(request.getEmail())) {
        throw new BadRequestException("Email đã tồn tại", "email");
    }

    Teacher teacher = TeacherConverter.toEntity(request);
    return TeacherConverter.toResponse(repository.save(teacher));
}

@Override
public TeacherResponse update(TeacherRequest request, Long id) {
    Teacher teacher = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + id, "id"));

    // Check email trùng với giáo viên khác (trừ chính id đang update)
    if (repository.existsByEmailAndIdNot(request.getEmail(), id)) {
        throw new BadRequestException("Email đã tồn tại", "email");
    }

    TeacherConverter.updateEntity(teacher, request);
    return TeacherConverter.toResponse(repository.save(teacher));
}


    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + id, "id");
        }
        repository.deleteById(id);
    }

    @Override
    public Page<TeacherResponse> searchTeacher(TeacherSearchRequest request, Pageable pageable) {
        Page<Teacher> page = repository.findAll(TeacherSpecification.build(request), pageable);
        return page.map(TeacherConverter::toResponse);
    }
}