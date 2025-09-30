package com.example.Spring_API_Auth.Repository;

import com.example.Spring_API_Auth.Model.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SubjectRepository extends JpaRepository<Subject, Long>, JpaSpecificationExecutor<Subject> {
    boolean existsBySubjectName(String subjectName);

    boolean existsBySubjectNameAndIdNot(String subjectName, Long id);
}