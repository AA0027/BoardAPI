package com.example.test.service.user;

import com.example.test.dao.Member;
import com.example.test.service.user.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> user = service.findOne(username);
        Member member = user.orElseThrow(() -> new UsernameNotFoundException("없는 회원입니다!!"));
        return Member.builder()
                .email(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
