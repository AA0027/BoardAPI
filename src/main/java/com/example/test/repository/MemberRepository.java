package com.example.test.repository;


import com.example.test.dao.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface MemberRepository extends JpaRepository<Member, Integer> {
    public Optional<Member> findByEmail(String email);
}
