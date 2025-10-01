package com.example.Spring_API_Auth.Config;

import com.example.Spring_API_Auth.Service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http

            .csrf(csrf -> csrf.disable())
            .cors().and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/", "/home", "/login", "/register").permitAll()
                .requestMatchers("/css/**", "/js/**", "/images/**", "/favicon.ico").permitAll()
                .requestMatchers("/api/auth/**").permitAll()

                .requestMatchers(HttpMethod.POST, "/api/student/search").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/student/**").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/student/**").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/student/**").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/student/**").hasAnyRole("ADMIN", "TEACHER")

                .requestMatchers(HttpMethod.POST, "/api/teacher/search").hasAnyRole("USER", "ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.GET, "/api/teacher/**").hasAnyRole("USER", "ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.POST, "/api/teacher/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/teacher/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/teacher/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/subject/search").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/subject/**").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/subject/**").hasAnyRole("ADMIN", "TEACHER")
                .requestMatchers(HttpMethod.PUT, "/api/subject/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/subject/**").hasRole("ADMIN")

                .requestMatchers(HttpMethod.POST, "/api/grade/search").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/grade/**").hasAnyRole("USER", "TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/grade/**").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/grade/**").hasAnyRole("TEACHER", "ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/grade/**").hasAnyRole("ADMIN", "TEACHER")
                
                .anyRequest().authenticated()
            )
            
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/perform-login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
            )
            
            .httpBasic(basic -> basic
                .realmName("Student Management API")
            )
            
            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login?logout=true")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            
            .exceptionHandling(ex -> ex
                .accessDeniedPage("/access-denied")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) 
            throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:3000")); // FE
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }
}