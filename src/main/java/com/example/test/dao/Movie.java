package com.example.test.dao;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private int display;
    private Item[] items;

    @Data
    static class Item{
        public String title;
        public String link;
        public String image;
        public String subtitle;
        @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy")
        public Date pubDate;
        public String director;
        public String actor;
        public Integer userRating;
    }
}
