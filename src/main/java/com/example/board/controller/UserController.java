package com.example.board.controller;

import com.example.board.dto.LoginForm;
import com.example.board.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/myBoard")
public class UserController {
    private final UserService service;
    @GetMapping("/login")
    public String login(){return "loginForm";}

    @GetMapping("/signup")
    public String signup()
    {
        return "userRegForm";
    }

    @PostMapping("/signup")
    public String register(LoginForm loginForm)
    {
        service.save(loginForm);
        System.out.println(loginForm.getEmail());
        System.out.println(loginForm.getPassword());
        return "redirect:/myBoard/login";
    }
}
