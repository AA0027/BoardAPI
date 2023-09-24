package com.example.test.dao;

import com.example.test.dto.MemberForm;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
@Table(name = "member")
@Entity()
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    private String addr;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Question> questions;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    public MemberForm toForm()
    {
        MemberForm memberForm = new MemberForm();
        memberForm.setName(name);
        memberForm.setAddr(addr);
        memberForm.setEmail(email);
        return memberForm;
    }

    @Builder
    public Member(String name, String email,String addr)
    {
        this.name = name;
        this.addr = addr;
        this.email = email;
    }
}
