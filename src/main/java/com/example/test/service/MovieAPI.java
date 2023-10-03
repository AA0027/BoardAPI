package com.example.test.service;

import com.example.test.dao.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class MovieAPI {
    private final RestTemplate restTemplate;
    private final String CLIENT_ID ="u8KkPZWhFC1zC65uYoqr";
    private final String CLIENT_SECRET = "Ls8LfoYruJ";


    public Movie requestMovie(String query, String country)
    {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder.fromUriString("").path("")
                .queryParam("query",encode)
                .queryParam("country", country)
                .encode().build().toUri();

        RequestEntity<Void> req = RequestEntity.get(uri)
                .header("X-Naver-Client-Id",CLIENT_ID)
                .header("X-Naver-Client-Secret",CLIENT_SECRET).build();

        ResponseEntity<Movie> result = restTemplate.exchange(req, Movie.class);
        return result.getBody();
    }

    public Movie requestMovie(String query, String country, String genre)
    {
        ByteBuffer buffer = StandardCharsets.UTF_8.encode(query);
        String encode = StandardCharsets.UTF_8.decode(buffer).toString();

        URI uri = UriComponentsBuilder.fromUriString("").path("")
                .queryParam("query",encode)
                .queryParam("country", country)
                .queryParam("genre", genre)
                .encode().build().toUri();

        RequestEntity<Void> req = RequestEntity.get(uri)
                .header("X-Naver-Client-Id",CLIENT_ID)
                .header("X-Naver-Client-Secret",CLIENT_SECRET).build();

        ResponseEntity<Movie> result = restTemplate.exchange(req, Movie.class);
        return result.getBody();
    }
}
