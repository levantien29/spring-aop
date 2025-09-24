package com.example.Spring_API_Auth.Exception;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ErrorDetail {
    private String field;
    private List<String> messages;
    private String code;
}
