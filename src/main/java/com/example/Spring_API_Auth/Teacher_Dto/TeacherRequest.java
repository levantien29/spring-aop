package com.example.Spring_API_Auth.Teacher_Dto;

import java.util.List;

import com.example.Spring_API_Auth.Subject_Dto.SubjectRequest;

import jakarta.validation.constraints.Email;
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
public class TeacherRequest {
    @NotBlank(message = "Tên giáo viên không được để trống")
    private String name;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Số điện thoại không được để trống")
    private Integer phone;

    @NotBlank(message = "Khoa/Bộ môn không được để trống")
    private String department;

    // private List<SubjectRequest> subjects;
}