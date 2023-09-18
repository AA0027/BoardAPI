package com.example.test.service;

import com.example.test.dao.login.Member;
import com.example.test.dto.MemberForm;
import com.example.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService  {
    private final MemberRepository repo;
    private final PasswordEncoder encoder;

    public Optional<Member> findOne(String email) {
        return repo.findByEmail(email);
    }
    public void save(MemberForm form)
    {

        Member member = Member.builder().email(form.getEmail()).password(encoder.encode(form.getPassword())).build();
        repo.save(member);

    }

    public  Integer join(String email, String pw)
    {
        Member member = Member.createUser(email, pw, encoder);
        repo.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member)
    {
        repo.findByEmail(member.getEmail())
                .ifPresent(m -> {throw new IllegalStateException("이미 존재하는 회원입니다.");});
    }

}
