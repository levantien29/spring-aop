package com.example.Spring_API_Auth.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.Spring_API_Auth.Converter.GradeConverter;
import com.example.Spring_API_Auth.Exception.ResourceNotFoundException;
import com.example.Spring_API_Auth.Grade_Dto.GradeRequest;
import com.example.Spring_API_Auth.Grade_Dto.GradeResponse;
import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;
import com.example.Spring_API_Auth.Model.Grade;
import com.example.Spring_API_Auth.Repository.GradeRepository;
import com.example.Spring_API_Auth.Repository.StudentReposiory;
import com.example.Spring_API_Auth.Repository.SubjectRepository;
import com.example.Spring_API_Auth.Specification.GradeSpecification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GradeService implements IGradeService {

    private final GradeRepository repository;
    private final StudentReposiory studentRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public Page<GradeResponse> getAll(Pageable pageable) {
        return repository.findAll(pageable).map(GradeConverter::toResponse);
    }

    @Override
    public GradeResponse getById(Long id) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy điểm có id: " + id, "id"));
        return GradeConverter.toResponse(grade);
    }

    @Override
    public GradeResponse create(GradeRequest request) {
        Grade grade = GradeConverter.toEntity(request);

        // lấy student
        var student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id: " + request.getStudentId(), "studentId"));

        // lấy subject
        var subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy môn học có id: " + request.getSubjectId(), "subjectId"));

        grade.setStudent(student);
        grade.setSubject(subject);

        return GradeConverter.toResponse(repository.save(grade));
    }

    @Override
    public GradeResponse update(GradeRequest request, Long id) {
        Grade grade = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy điểm có id: " + id, "id"));

        GradeConverter.updateEntity(grade, request);

        // nếu request có studentId -> cập nhật lại student
        if (request.getStudentId() != null) {
            var student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sinh viên có id: " + request.getStudentId(), "studentId"));
            grade.setStudent(student);
        }

        // nếu request có subjectId -> cập nhật lại subject
        if (request.getSubjectId() != null) {
            var subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy môn học có id: " + request.getSubjectId(), "subjectId"));
            grade.setSubject(subject);
        }

        return GradeConverter.toResponse(repository.save(grade));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy điểm có id: " + id, "id");
        }
        repository.deleteById(id);
    }

    @Override
    public Page<GradeResponse> searchGrade(GradeSearchRequest request, Pageable pageable) {
        Page<Grade> page = repository.findAll(GradeSpecification.build(request), pageable);
        return page.map(GradeConverter::toResponse);
    }
}