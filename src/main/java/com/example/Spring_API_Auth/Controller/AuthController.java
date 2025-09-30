package com.example.Spring_API_Auth.Controller;

import com.example.Spring_API_Auth.Dto.ApiResponse;
import com.example.Spring_API_Auth.Service.UserService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register/user")
    public ResponseEntity<ApiResponse<?>> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Đăng ký user " + request.getEmail() + " thành công", null)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping("/register/teacher")
    public ResponseEntity<ApiResponse<?>> registerTeacher(@RequestBody RegisterRequest request) {
        try {
            userService.registerTeacher(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(
                new ApiResponse<>(true, "Đăng ký giáo viên với email " + request.getEmail() + " thành công", null)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                new ApiResponse<>(false, e.getMessage(), null)
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, Object>>> login(@RequestBody LoginRequest request) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            var roles = auth.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();

            Map<String, Boolean> permissions = Map.of(
                    "GET", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_USER") || roles.contains("ROLE_TEACHER"),
                    "SEARCH", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_USER") || roles.contains("ROLE_TEACHER"),
                    "POST", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_TEACHER"),
                    "PUT", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_TEACHER"),
                    "DELETE", roles.contains("ROLE_ADMIN")
            );

            Map<String, Object> data = Map.of(
                    "username", request.getEmail(),
                    "roles", roles,
                    "permissions", permissions
            );

            return ResponseEntity.ok(
                new ApiResponse<>(true, "Login thành công", data)
            );

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                new ApiResponse<>(false, "Email hoặc mật khẩu sai", null)
            );
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<ApiResponse<Map<String, Object>>> logout() {
        SecurityContextHolder.clearContext();

        Map<String, Object> data = Map.of(
                "timestamp", LocalDateTime.now()
        );

        return ResponseEntity.ok(
            new ApiResponse<>(true, "Logout thành công", data)
        );
    }
}

@Data
class RegisterRequest {
    private String email;
    private String password;
}

@Data
class LoginRequest {
    private String email;
    private String password;
}
