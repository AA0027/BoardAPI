package com.example.test.dao.board;

import jakarta.persistence.*;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "answer")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class Answer {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @ManyToOne
    private Question question;

    @Builder
    public Answer(String content,LocalDateTime time,Question q)
    {
        this.content = content;
        this.createDate = time;
        this.question = q;
    }
}
