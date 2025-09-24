package com.example.Spring_API_Auth.Controller;

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

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request){
        try {
            userService.registerUser(request.getEmail(), request.getPassword());
            return ResponseEntity.ok(Map.of("message", "Đăng ký thành công"));
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request){
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(auth);

            // Lấy danh sách role
            var roles = auth.getAuthorities().stream()
                    .map(r -> r.getAuthority())
                    .toList();

            // Map role sang quyền API
            Map<String, Boolean> permissions = Map.of(
                    "GET", roles.contains("ROLE_ADMIN") || roles.contains("ROLE_USER"),
                    "POST", roles.contains("ROLE_ADMIN"),
                    "PUT", roles.contains("ROLE_ADMIN"),
                    "DELETE", roles.contains("ROLE_ADMIN")
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Login thành công",
                    "username", request.getEmail(),
                    "roles", roles,
                    "permissions", permissions
            ));

        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Email hoặc mật khẩu sai"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Xóa SecurityContext (dù Basic Auth stateless)
        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of(
                "message", "Logout successful",
                "timestamp", LocalDateTime.now()
        ));
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
