package com.example.Spring_API_Auth.Dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StudentRequest {
    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotBlank(message = "Mã sinh viên không được để trống")
    private String code;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotBlank(message = "Số điện thoại không được để trống")
    @Pattern(regexp = "^(0|\\+84)[0-9]{9}$", message = "Số điện thoại không hợp lệ")
    private String phone;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Tuổi không được để trống")
    @Min(value = 18, message = "Tuổi phải từ 18 trở lên")
    private Integer age;

    @NotBlank(message = "chuyên nghành không được để trống")
    private String major;

    @NotNull(message = "GPA không được để trống")
    @Min(value = 0, message = "GPA không được nhỏ hơn 0")
    private Double gpa;
}
