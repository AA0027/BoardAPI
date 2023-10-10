package com.example.board.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Board {
    private String title;
    private String content;

    public Board(String title, String content)
    {
        this.title = title; this.content = content;
    }
}
