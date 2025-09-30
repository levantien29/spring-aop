package com.example.Spring_API_Auth.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "grade_auth")
public class Grade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double score;

    // Quan hệ n-1 với Student
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Quan hệ n-1 với Subject
    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;
}
