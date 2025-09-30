package com.example.Spring_API_Auth.Subject_Dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SubjectRequest {
    @NotBlank(message = "Tên môn học không được để trống")
    private String subjectName;

    @NotNull(message = "Số tín chỉ không được để trống")
    @Min(value = 1, message = "Môn học phải có ít nhất 1 tín chỉ")
    private Integer credit;

    @NotNull(message = "Phải chọn giáo viên dạy môn học")
    private Long teacherId;
}