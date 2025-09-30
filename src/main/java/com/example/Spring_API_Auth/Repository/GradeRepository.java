package com.example.Spring_API_Auth.Repository;

import com.example.Spring_API_Auth.Model.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface GradeRepository extends JpaRepository<Grade, Long>, JpaSpecificationExecutor<Grade> {
    // Ví dụ: kiểm tra sinh viên đã có điểm cho môn học này chưa
    boolean existsByStudentIdAndSubjectId(Long studentId, Long subjectId);

    boolean existsByStudentIdAndSubjectIdAndIdNot(Long studentId, Long subjectId, Long id);
}
