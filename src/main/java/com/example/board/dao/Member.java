package com.example.board.dao;

import com.example.board.dto.LoginForm;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Entity
@Table(name = "member")
@NoArgsConstructor
@Data
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<BoardDao> board;
    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Answer> answers;
    public static Member createMember(String email, String password, PasswordEncoder passwordEncoder)
    {
        Member member = Member.builder().email(email).password(passwordEncoder.encode(password))
                .build();
        return member;
    }
    @Builder
    public Member(String email, String password)
    {
        this.email = email; this.password = password;
    }
}
