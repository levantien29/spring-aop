package com.example.Spring_API_Auth.Config;

import com.example.Spring_API_Auth.Entities.User;
import com.example.Spring_API_Auth.Repository.RoleRepository;
import com.example.Spring_API_Auth.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @EventListener
    public void appReady(ApplicationReadyEvent event) {
        // tạo roles nếu chưa có
        if (roleRepository.findByName("ROLE_ADMIN").isEmpty())
            roleRepository.save(new com.example.Spring_API_Auth.Entities.Role(null, "ROLE_ADMIN"));
        if (roleRepository.findByName("ROLE_USER").isEmpty())
            roleRepository.save(new com.example.Spring_API_Auth.Entities.Role(null, "ROLE_USER"));

        // tạo admin cứng
        if (userRepository.findByEmail("admin@example.com").isEmpty()) {
            User admin = new User();
            admin.setEmail("admin@example.com");
            admin.setPassword(passwordEncoder.encode("123456"));
            admin.getRoles().add(roleRepository.findByName("ROLE_ADMIN").get());
            userRepository.save(admin);
        }
    }
}
