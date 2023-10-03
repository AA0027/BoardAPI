package com.example.test.service;

import com.example.test.dao.Member;
import com.example.test.dto.MemberForm;
import com.example.test.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repo;

    @Transactional
    public Member update(MemberForm form,long id)
    {
        Member member = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("id"+id));
        member.setName(form.getName());
        member.setEmail(form.getEmail());
        return member;
    }
}
