package com.example.Spring_API_Auth.Specification;

import com.example.Spring_API_Auth.Grade_Dto.GradeSearchRequest;
import com.example.Spring_API_Auth.Model.Grade;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class GradeSpecification {
    public static Specification<Grade> build(GradeSearchRequest request) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (request.getId() != null) {
                predicates.add(cb.equal(root.get("id"), request.getId()));
            }
            if (request.getScore() != null) {
                predicates.add(cb.equal(root.get("score"), request.getScore()));
            }
            if (request.getStudentId() != null) {
                predicates.add(cb.equal(root.get("student").get("id"), request.getStudentId()));
            }
            if (request.getSubjectId() != null) {
                predicates.add(cb.equal(root.get("subject").get("id"), request.getSubjectId()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
