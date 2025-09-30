package com.example.Spring_API_Auth.Specification;

import com.example.Spring_API_Auth.Model.Subject;
import com.example.Spring_API_Auth.Subject_Dto.SubjectSearchRequest;

import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SubjectSpecification {
    public static Specification<Subject> build(SubjectSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }
            if (request.getSubjectName() != null && !request.getSubjectName().isBlank()) {
                predicates.add(cb.like(cb.lower(root.get("subjectName")), "%" + request.getSubjectName().toLowerCase() + "%"));
            }
            if (request.getCredit() != null) {
                predicates.add(cb.equal(root.get("credit"), request.getCredit()));
            }
            if (request.getTeacherId() != null) {
                predicates.add(cb.equal(root.get("teacher").get("id"), request.getTeacherId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}