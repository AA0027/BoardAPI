package com.example.test.dao;

import com.example.test.dto.Region;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "member")
public class Member {
    @Id()
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Region region;

    public Member(String name, String email,Region region)
    {
        this.name = name;
        this.email = email;
        this.region = region;
    }


}
