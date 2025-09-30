package com.example.Spring_API_Auth.Grade_Dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class GradeRequest {
    @NotNull(message = "Sinh viên không được để trống")
    private Long studentId;

    @NotNull(message = "Môn học không được để trống")
    private Long subjectId;

    @NotNull(message = "Điểm không được để trống")
    @Min(value = 0, message = "Điểm phải >= 0")
    @Max(value = 10, message = "Điểm phải <= 10")
    private Double score;
}
