package com.example.board.dao;

import com.example.board.dto.Board;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "board")
@NoArgsConstructor
@Data
public class BoardDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long board_id;
    private String title;
    @Column(columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createDate;
    private int viewCnt;
    @ManyToOne
    private Member member;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    private List<Answer> answers;

    @Builder
    public BoardDao(String title, String content, LocalDateTime createDate, int viewCnt, Member member)
    {
        this.title = title; this.content = content; this.createDate = createDate;
        this.viewCnt = viewCnt; this.member = member;
    }
}
