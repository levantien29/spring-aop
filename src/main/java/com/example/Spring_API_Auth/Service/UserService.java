package com.example.Spring_API_Auth.Service;

import com.example.Spring_API_Auth.Entities.Role;
import com.example.Spring_API_Auth.Entities.User;
import com.example.Spring_API_Auth.Repository.RoleRepository;
import com.example.Spring_API_Auth.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    
    //hàm đăng kí tài khoản

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public void registerUser(String email, String password){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email đã tồn tại");
        }
        if(password == null || password.length() < 6){
            throw new RuntimeException("Password phải ít nhất 6 ký tự");
        }

        Role roleUser = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("Role USER chưa tồn tại"));

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add(roleUser);

        userRepository.save(user);
    }
    
     public void registerTeacher(String email, String password){
        if(userRepository.findByEmail(email).isPresent()){
            throw new RuntimeException("Email đã tồn tại");
        }
        if(password == null || password.length() < 6){
            throw new RuntimeException("Password phải ít nhất 6 ký tự");
        }

        Role roleTeacher = roleRepository.findByName("ROLE_TEACHER")
                .orElseThrow(() -> new RuntimeException("Role TEACHER chưa tồn tại"));

        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.getRoles().add(roleTeacher);

        userRepository.save(user);
    }
}