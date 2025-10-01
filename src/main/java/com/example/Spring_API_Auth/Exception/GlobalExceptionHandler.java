// package com.example.Spring_API_Auth.Exception;

// import jakarta.servlet.http.HttpServletRequest;
// import jakarta.validation.ConstraintViolationException;
// import lombok.extern.slf4j.Slf4j;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.MethodArgumentNotValidException;
// import org.springframework.web.bind.annotation.ExceptionHandler;
// import org.springframework.web.bind.annotation.RestControllerAdvice;

// import java.time.Instant;
// import java.util.List;
// import java.util.Map;
// import java.util.stream.Collectors;

// @Slf4j
// @RestControllerAdvice
// public class GlobalExceptionHandler {

//     // 400 - Validation (RequestBody)
//     @ExceptionHandler(MethodArgumentNotValidException.class)
//     public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex,
//                                                           HttpServletRequest request) {
//         log.error("Validation error at [{}]: {}", request.getRequestURI(), ex.getMessage());

//         //gom nhiều message vào 1 list
//         Map<String, List<String>> fieldErrors = ex.getBindingResult().getFieldErrors()
//                 .stream()
//                 .collect(Collectors.groupingBy(
//                         err -> err.getField(),
//                         Collectors.mapping(err -> err.getDefaultMessage(), Collectors.toList())
//                 ));

//         List<ErrorDetail> details = fieldErrors.entrySet().stream()
//                 .map(entry -> ErrorDetail.builder()
//                         .field(entry.getKey())
//                         .message(entry.getValue())
//                         .code(ErrorCode.VALIDATION_ERROR.name())
//                         .build())
//                 .collect(Collectors.toList());

//         ErrorResponse response = ErrorResponse.builder()
//                 .timestamp(Instant.now())
//                 .status(HttpStatus.BAD_REQUEST.value())
//                 .code(ErrorCode.VALIDATION_ERROR.name())
//                 .path(request.getRequestURI())
//                 .errors(details)
//                 .build();

//         return ResponseEntity.badRequest().body(response);
//     }

//     // 400 - Validation (RequestParam, PathVariable)
//     @ExceptionHandler(ConstraintViolationException.class)
//     public ResponseEntity<ErrorResponse> handleConstraintViolation(ConstraintViolationException ex,
//                                                                    HttpServletRequest request) {
//         log.error("Constraint violation at [{}]: {}", request.getRequestURI(), ex.getMessage());

//         Map<String, List<String>> fieldErrors = ex.getConstraintViolations()
//                 .stream()
//                 .collect(Collectors.groupingBy(
//                         v -> v.getPropertyPath().toString(),
//                         Collectors.mapping(v -> v.getMessage(), Collectors.toList())
//                 ));

//         List<ErrorDetail> details = fieldErrors.entrySet().stream()
//                 .map(entry -> ErrorDetail.builder()
//                         .field(entry.getKey())
//                         .message(entry.getValue())
//                         .code(ErrorCode.CONSTRAINT_VIOLATION.name())
//                         .build())
//                 .collect(Collectors.toList());

//         ErrorResponse response = ErrorResponse.builder()
//                 .timestamp(Instant.now())
//                 .status(HttpStatus.BAD_REQUEST.value())
//                 .code(ErrorCode.CONSTRAINT_VIOLATION.name())
//                 .path(request.getRequestURI())
//                 .errors(details)
//                 .build();

//         return ResponseEntity.badRequest().body(response);
//     }

//     // 400 - Custom BadRequest
//     @ExceptionHandler(BadRequestException.class)
//     public ResponseEntity<ErrorResponse> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
//         log.error("BadRequest at [{}]: field={}, message={}", request.getRequestURI(), ex.getField(), ex.getMessage());

//         ErrorDetail detail = ErrorDetail.builder()
//                 .field(ex.getField())
//                 .message(List.of(ex.getMessage()))
//                 .code(ErrorCode.BAD_REQUEST.name())
//                 .build();

//         ErrorResponse response = ErrorResponse.builder()
//                 .timestamp(Instant.now())
//                 .status(HttpStatus.BAD_REQUEST.value())
//                 .code(ErrorCode.BAD_REQUEST.name())
//                 .path(request.getRequestURI())
//                 .errors(List.of(detail))
//                 .build();

//         return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
//     }

//     // 404
//     @ExceptionHandler(ResourceNotFoundException.class)
//     public ResponseEntity<ErrorResponse> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
//         log.warn("Resource not found at [{}]: field={}, message={}", request.getRequestURI(), ex.getField(), ex.getMessage());

//         ErrorDetail detail = ErrorDetail.builder()
//                 .field(ex.getField())
//                 .message(List.of(ex.getMessage()))
//                 .code(ErrorCode.RESOURCE_NOT_FOUND.name())
//                 .build();

//         ErrorResponse response = ErrorResponse.builder()
//                 .timestamp(Instant.now())
//                 .status(HttpStatus.NOT_FOUND.value())
//                 .code(ErrorCode.RESOURCE_NOT_FOUND.name())
//                 .path(request.getRequestURI())
//                 .errors(List.of(detail))
//                 .build();

//         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
//     }

//     // 500 - Internal Server Error
//     @ExceptionHandler(Exception.class)
//     public ResponseEntity<ErrorResponse> handleException(Exception ex, HttpServletRequest request) {
//         log.error("Unexpected error at [{}]: ", request.getRequestURI(), ex);

//         ErrorDetail detail = ErrorDetail.builder()
//                 .field(null)
//                 .message(List.of("Internal server error"))
//                 .code(ErrorCode.INTERNAL_ERROR.name())
//                 .build();

//         ErrorResponse response = ErrorResponse.builder()
//                 .timestamp(Instant.now())
//                 .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                 .code(ErrorCode.INTERNAL_ERROR.name())
//                 .path(request.getRequestURI())
//                 .errors(List.of(detail))
//                 .build();

//         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
//     }
// }
package com.example.Spring_API_Auth.Exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 400 - Validation (RequestBody)
    @ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<ErrorDetail> handleValidation(MethodArgumentNotValidException ex,
                                                    HttpServletRequest request) {
//     log.error("Validation error at [{}]: {}", request.getRequestURI(), ex.getMessage());

    // lấy lỗi đầu tiên trong danh sách
    var firstError = ex.getBindingResult().getFieldErrors().get(0);

    ErrorDetail detail = ErrorDetail.builder()
            .field(firstError.getField())
            .message(firstError.getDefaultMessage())
            .code(ErrorCode.BAD_REQUEST.name())
            .status(false)
            .build();

    return ResponseEntity.badRequest().body(detail);
}


   // 400 - Validation (RequestParam, PathVariable)
@ExceptionHandler(ConstraintViolationException.class)
public ResponseEntity<ErrorDetail> handleConstraintViolation(ConstraintViolationException ex,
                                                             HttpServletRequest request) {
    log.error("Constraint violation at [{}]: {}", request.getRequestURI(), ex.getMessage());

    // lấy lỗi đầu tiên
    ConstraintViolation<?> firstError = ex.getConstraintViolations().iterator().next();

    ErrorDetail detail = ErrorDetail.builder()
            .field(firstError.getPropertyPath().toString()) // tên field bị lỗi
            .message(firstError.getMessage())   // message lỗi
            .code(ErrorCode.BAD_REQUEST.name())
            .status(false)
            .build();

    return ResponseEntity.badRequest().body(detail);
}


    // 400 - Custom BadRequest
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorDetail> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
        log.error("BadRequest at [{}]: field={}, message={}", request.getRequestURI(), ex.getField(), ex.getMessage());

        ErrorDetail detail = ErrorDetail.builder()
                .field(ex.getField())
                .message(ex.getMessage())
                .code(ErrorCode.BAD_REQUEST.name())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(detail);
    }

    // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDetail> handleNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
        log.warn("Resource not found at [{}]: field={}, message={}", request.getRequestURI(), ex.getField(),
                ex.getMessage());

        ErrorDetail detail = ErrorDetail.builder()
                .field(ex.getField())
                .message(ex.getMessage())
                .code(ErrorCode.RESOURCE_NOT_FOUND.name())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(detail);
    }

    // 500 - Internal Server Error
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetail> handleException(Exception ex, HttpServletRequest request) {
        log.error("Unexpected error at [{}]: ", request.getRequestURI(), ex);

        ErrorDetail detail = ErrorDetail.builder()
                .field(null)
                .message("Internal server error")
                .code(ErrorCode.INTERNAL_ERROR.name())
                .status(false)
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(detail);
    }
}
