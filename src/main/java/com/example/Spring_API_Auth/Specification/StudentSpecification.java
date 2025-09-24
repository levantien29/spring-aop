package com.example.Spring_API_Auth.Specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.example.Spring_API_Auth.Dto.StudentSearchRequest;
import com.example.Spring_API_Auth.Model.Student;

import jakarta.persistence.criteria.Predicate;

public class StudentSpecification {
    public static Specification<Student> build(StudentSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }
            if (request.getName() != null && !request.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getCode() != null && !request.getCode().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("code")), "%" + request.getCode().toLowerCase() + "%"));
            }
            if (request.getEmail() != null && !request.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getMajor() != null && !request.getMajor().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("major")), "%" + request.getMajor().toLowerCase() + "%"));
            }
            if (request.getGender() != null && !request.getGender().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("gender")), "%" + request.getGender().toLowerCase() + "%"));
            }
            if (request.getPhone() != null) {
                predicates.add(cb.like(root.get("phone"), "%" + request.getPhone() + "%"));
            }
            if (request.getGpa() != null) {
                predicates.add(cb.equal(root.get("gpa"), request.getGpa()));
            }
            if (request.getAge() != null) {
                predicates.add(cb.equal(root.get("age"), request.getAge()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
