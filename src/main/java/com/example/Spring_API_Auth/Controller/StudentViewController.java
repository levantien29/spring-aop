package com.example.Spring_API_Auth.Controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.Spring_API_Auth.Dto.StudentResponse;
import com.example.Spring_API_Auth.Presenter.StudentPresenter;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class StudentViewController {

    private final StudentPresenter presenter;

    @GetMapping("/dashboard")
    public String dashboard(Pageable pageable, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("username", auth.getName());

        Page<StudentResponse> students = presenter.viewAll(pageable);
        model.addAttribute("students", students.getContent());
        model.addAttribute("page", students);

        return "dashboard";
    }
}
