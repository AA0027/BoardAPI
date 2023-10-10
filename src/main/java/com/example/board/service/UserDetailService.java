package com.example.board.service;

import com.example.board.DataNotFoundException;
import com.example.board.dao.Member;
import com.example.board.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {
    private final UserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = repo.findByEmail(email)
                .orElseThrow(()->new DataNotFoundException("Data not fouded"+email));

        return User.builder().username(member.getEmail())
                .password(member.getPassword()).roles("USER").build();
    }
}
