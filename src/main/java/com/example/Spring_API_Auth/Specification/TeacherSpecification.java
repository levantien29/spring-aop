package com.example.Spring_API_Auth.Specification;

import com.example.Spring_API_Auth.Model.Teacher;
import com.example.Spring_API_Auth.Teacher_Dto.TeacherSearchRequest;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class TeacherSpecification {
    public static Specification<Teacher> build(TeacherSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }
            if (request.getName() != null && !request.getName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("name")), "%" + request.getName().toLowerCase() + "%"));
            }
            if (request.getEmail() != null && !request.getEmail().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("email")), "%" + request.getEmail().toLowerCase() + "%"));
            }
            if (request.getPhone() != null) {
                predicates.add(cb.equal(root.get("phone"), request.getPhone()));
            }
            if (request.getDepartment() != null && !request.getDepartment().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("department")), "%" + request.getDepartment().toLowerCase() + "%"));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}