package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Spring_API_Auth.Converter.SubjectConverter;

import com.example.Spring_API_Auth.Exception.ResourceNotFoundException;
import com.example.Spring_API_Auth.Model.Subject;
import com.example.Spring_API_Auth.Model.Teacher;
import com.example.Spring_API_Auth.Repository.SubjectRepository;
import com.example.Spring_API_Auth.Repository.TeacherRepository;
import com.example.Spring_API_Auth.Specification.SubjectSpecification;
import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;
import com.example.Spring_API_Auth.Subject_Dto.SubjectResponse;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectService implements ISubjectService {

    private final SubjectRepository repository;
    private final TeacherRepository teacherRepository;

    @Override
    public Page<SubjectResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(SubjectConverter::toResponse);
    }

    @Override
    public SubjectResponse getById(Long id) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy môn học có id: " + id, "id"));
        return SubjectConverter.toResponse(subject);
    }

     @Override
    public SubjectResponse create(SubjectRequest request) {
        // Tìm teacher theo ID
        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + request.getTeacherId(), "teacherId"));

        Subject subject = SubjectConverter.toEntity(request);
        subject.setTeacher(teacher); // 👈 gán teacher

        return SubjectConverter.toResponse(repository.save(subject));
    }

    @Override
    public SubjectResponse update(SubjectRequest request, Long id) {
        Subject subject = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy môn học có id: " + id, "id"));

        Teacher teacher = teacherRepository.findById(request.getTeacherId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy giáo viên có id: " + request.getTeacherId(), "teacherId"));

        SubjectConverter.updateEntity(subject, request);
        subject.setTeacher(teacher); // 👈 update lại teacher

        return SubjectConverter.toResponse(repository.save(subject));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy môn học có id: " + id, "id");
        }
        repository.deleteById(id);
    }

    @Override
    public Page<SubjectResponse> searchSubject(SubjectSearchRequest request, Pageable pageable) {
        Page<Subject> page = repository.findAll(SubjectSpecification.build(request), pageable);
        return page.map(SubjectConverter::toResponse);
    }
}