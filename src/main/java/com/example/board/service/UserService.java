package com.example.board.service;

import com.example.board.DataNotFoundException;
import com.example.board.dao.Member;
import com.example.board.dto.LoginForm;
import com.example.board.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repo;
    private final PasswordEncoder encoder;
    public void save(LoginForm form)
    {
        Member member = Member.createMember(form.getEmail(), form.getPassword(), encoder);
        repo.save(member);
    }

    public Member getMember(String email)
    {
        Member member = repo.findByEmail(email).orElseThrow(()->new DataNotFoundException("Not found"+email));
        return member;
    }
}
