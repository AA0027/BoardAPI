package com.example.board.dao;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "answer")
@Data
@NoArgsConstructor
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ManyToOne
    private Member member;
    @ManyToOne
    private BoardDao board;
    @Column(nullable = false)
    private String reply;

    public Answer(Member member, BoardDao board, String reply)
    {
        this.reply = reply; this.member = member; this.board = board;
    }
    public static Answer createAnswer(Member member, BoardDao board, String reply)
    {
        Answer answer = new Answer(member, board, reply);
        return answer;
    }
}
