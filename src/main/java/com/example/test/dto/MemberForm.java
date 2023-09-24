package com.example.test.dto;

import lombok.*;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
public class MemberForm {

    private String name;
    private String email;
    private String addr;

    @Builder
    public MemberForm(String name, String email, String addr)
    {
        this.name = name;
        this.email = email;
        this.addr = addr;
    }
}
