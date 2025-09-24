package com.example.Spring_API_Auth.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.example.Spring_API_Auth.Model.Student;

public interface StudentReposiory extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    boolean existsByEmail(String email);

    boolean existsByCode(String code);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByCodeAndIdNot(String code, Long id);
}
