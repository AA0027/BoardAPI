package com.example.test.controller.user;

import com.example.test.dto.MemberForm;
import com.example.test.service.user.MemberService;
import com.example.test.service.user.MemberService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;


    @GetMapping("/signup")
    public String signup()
    {
        return "signup";
    }
    @Transactional
    @PostMapping("/signup")
    public String register(MemberForm form, BindingResult bindingResult)
    {

            if(bindingResult.hasErrors())
            {
             return "login";
            }
            System.out.println(form.getEmail());

        memberService.join(form.getEmail(), form.getPassword());
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login()
    {
        return "login";
    }

}
