package com.example.Spring_API_Auth.Subject_Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import com.example.Spring_API_Auth.Grade_Dto.GradeResponse;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubjectResponse {
    private Long id;
    private String subjectName;
    private int credit;

    // Tên giáo viên dạy môn
    private String teacherName;
    private Long teacherId;
    // Danh sách điểm của sinh viên trong môn học
    // private List<GradeResponse> grades;
}
