package com.example.Spring_API_Auth.AOP;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Aspect
@Component
public class LoggingAspect {

    // ====== HELPER ======
    private String getCurrentUserEmail() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return auth.getName();
        }
        return "anonymous";
    }

    private String getCurrentUserRoles() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            return auth.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .collect(Collectors.joining(", "));
        }
        return "[ANONYMOUS]";
    }

    private Map<String, Boolean> getCurrentUserPermissions() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
            var roles = auth.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();
            return Map.of(
                    "GET", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_USER"),
                    "POST", roles.contains("ROLE_ADMIN"),
                    "PUT", roles.contains("ROLE_ADMIN"),
                    "DELETE", roles.contains("ROLE_ADMIN"));
        }
        return Map.of(
                "GET", false,
                "POST", false,
                "PUT", false,
                "DELETE", false);
    }

    // ====== AUTH ======
    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.AuthController.register(..)) && args(request)", returning = "result")
    public void logRegister(Object request, Object result) {
        String email = "unknown";
        if (request != null) {
            log.debug("Request type: {}, content: {}", request.getClass().getName(), request);
            if (request instanceof RegisterRequest) {
                email = ((RegisterRequest) request).getEmail();
            } else {
                // Fallback để lấy email từ toString nếu có
                String requestStr = request.toString();
                if (requestStr != null && requestStr.contains("email=")) {
                    email = requestStr.substring(requestStr.indexOf("email=") + 6,
                            requestStr.indexOf(",", requestStr.indexOf("email=")));
                }
            }
        }
        String role = "[USER]"; // Vai trò mặc định khi đăng ký
        log.info("📌 [REGISTER] User '{}' với Role {} đăng ký: {}, Kết quả: {}, lúc: {}",
                email, role, request, result, LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.AuthController.login(..)) && args(request)", returning = "result")
    public void logLoginSuccess(Object request, Object result) {
        log.info("🔑 [LOGIN] User '{}' với Role {} login thành công: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), result, LocalDateTime.now());
    }

    @AfterThrowing(pointcut = "execution(* com.example.Spring_API_Auth.Controller.AuthController.login(..)) && args(request)", throwing = "ex")
    public void logLoginFail(Object request, Exception ex) {
        log.warn("❌ [LOGIN FAIL] User '{}' với Role {}: {}, Lý do: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), request, ex.getMessage(), LocalDateTime.now());
    }

    // ====== STUDENT ======
    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.create(..)) && args(request)", returning = "result")
    public void logCreateStudent(Object request, Object result) {
        log.info("➕ [STUDENT] User '{}' với Role {} và Permissions {} thêm sinh viên: {}, Kết quả: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), getCurrentUserPermissions(), request, result,
                LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.update(..)) && args(request, id)", returning = "result")
    public void logUpdateStudent(Object request, Long id, Object result) {
        log.info(
                "✏️ [STUDENT] User '{}' với Role {} và Permissions {} cập nhật sinh viên ID {}: {}, Kết quả: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), getCurrentUserPermissions(), id, request, result,
                LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.delete(..)) && args(id)", returning = "result")
    public void logDeleteStudent(Long id, Object result) {
        log.info("🗑 [STUDENT] User '{}' với Role {} và Permissions {} xoá sinh viên ID {}, Kết quả: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), getCurrentUserPermissions(), id, result,
                LocalDateTime.now());
    }

    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.searchStudent(..))", returning = "result")
    public void logSearchStudent(Object result) {
        if (result instanceof ResponseEntity<?> responseEntity) {
            Object body = responseEntity.getBody();

            if (body instanceof Page<?> page) {
                int totalElements = (int) page.getTotalElements();
                int totalPages = page.getTotalPages();
                int pageNumber = page.getNumber() + 1; // Page bắt đầu từ 0
                int pageSize = page.getSize();

                log.info("🔍 [STUDENT] User '{}' | Role: {} | Permissions: {} | Tìm kiếm sinh viên => "
                        + "Kết quả: {} bản ghi, Trang {}/{} (mỗi trang {}), lúc: {}",
                        getCurrentUserEmail(),
                        getCurrentUserRoles(),
                        getCurrentUserPermissions(),
                        totalElements,
                        pageNumber,
                        totalPages,
                        pageSize,
                        LocalDateTime.now());
            } else {
                log.info("🔍 [STUDENT] User '{}' | Role: {} | Permissions: {} | Kết quả: {}, lúc: {}",
                        getCurrentUserEmail(),
                        getCurrentUserRoles(),
                        getCurrentUserPermissions(),
                        body,
                        LocalDateTime.now());
            }
        } else {
            log.info("🔍 [STUDENT] User '{}' | Role: {} | Permissions: {} | Kết quả: {}, lúc: {}",
                    getCurrentUserEmail(),
                    getCurrentUserRoles(),
                    getCurrentUserPermissions(),
                    result,
                    LocalDateTime.now());
        }
    }

    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.getAll(..))", returning = "result")
    public void logGetAllStudents(Object result) {
        log.info("📖 [STUDENT] User '{}' với Role {} và Permissions {} lấy danh sách sinh viên, Kết quả: {}, lúc: {}",
                getCurrentUserEmail(), getCurrentUserRoles(), getCurrentUserPermissions(), result, LocalDateTime.now());
    }

        // ====== VALIDATION ERROR ======
// @AfterThrowing(
//     pointcut = "execution(* com.example.Spring_API_Auth.Controller.StudentController.create(..))",
//     throwing = "ex")
// public void logValidationError(Exception ex) {
//     if (ex instanceof MethodArgumentNotValidException manvEx) {
//         // Gom danh sách lỗi field
//         String errors = manvEx.getBindingResult()
//                 .getFieldErrors()
//                 .stream()
//                 .map(e -> e.getField() + ": " + e.getDefaultMessage())
//                 .collect(Collectors.joining(", "));

//         // Lấy endpoint bị gọi
//         String endpoint = "/api/student";

//         log.error("⚠️ [VALIDATION ERROR] User='{}' | Role='{}' | Endpoint='{}' | Lỗi='{}' | Thời gian='{}'",
//                 getCurrentUserEmail(),
//                 getCurrentUserRoles(),
//                 endpoint,
//                 errors,
//                 LocalDateTime.now()
//         );
//     }
// }



    @AfterReturning(pointcut = "execution(* com.example.Spring_API_Auth.Controller.AuthController.logout(..))", returning = "result")
public void logLogout(Object result) {
    log.info("🔓 [LOGOUT] User '{}' với Role '{}' đã đăng xuất thành công. Kết quả: {}, lúc: {}",
            getCurrentUserEmail(),
            getCurrentUserRoles(),
            result,
            LocalDateTime.now());
}

}

// Lớp RegisterRequest giả định (cần thêm nếu chưa có)
class RegisterRequest {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "RegisterRequest(email=" + email + ", password=****)";
    }
}