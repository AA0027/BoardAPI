package com.example.test.service;

import com.example.test.dao.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieAPI api;
    //한국영화 전체 찾기
    public Movie koreaMovie(String query) { return api.requestMovie(query, "KR"); }
    //일본 영화 전체 찾기
    public Movie japanMovie(String query) { return api.requestMovie(query, "JP"); }
    //미국 영화 전체 찾기
    public Movie usMovie(String query) { return api.requestMovie(query, "US"); }

    //한국 드라마 전체 찾기
    public Movie koreaDrama(String query) { return api.requestMovie(query, "KR", "1");}
    //한국 판타지 영화 전체 찾기
    public Movie koreaFantasy(String query) { return api.requestMovie(query, "KR", "2");}
    //한국 호러 영화 전체 찾기
    public Movie koreaHorror(String query) { return api.requestMovie(query, "KR", "4");}
    //일본 드라마 전체 찾기
    public Movie japanDrama(String query) { return api.requestMovie(query, "JP", "1");}
    //일본 판타지 영화 전체 찾기
    public Movie japanFantasy(String query) { return api.requestMovie(query, "JP", "2");}
    //일본 호러영화 전체 찾기
    public Movie japanHorror(String query) { return api.requestMovie(query, "JP", "4");}
    //미국 드라마 전체 찾기
    public Movie usDrama(String query) { return api.requestMovie(query, "US", "1");}
    //미국 판타지 영화 전체 찾기
    public Movie usFantasy(String query) { return api.requestMovie(query, "US", "2");}
    //미국 호러영화 전체 찾기
    public Movie usHorror(String query) { return api.requestMovie(query, "US", "4");}

}
