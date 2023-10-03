package com.example.test.controller.movie;

import com.example.test.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class KMovieController {
    private final MovieService service;

    @GetMapping("/korea/{query}")
    public void kMovies(@PathVariable("query") String query)
    {
        service.koreaMovie(query);
    }
    @GetMapping("/korea/drama/{query}")
    public void kDrama(@PathVariable("query") String query)
    {
        service.koreaDrama(query);
    }
    @GetMapping("/korea/fantasy/{query}")
    public void kFantasy(@PathVariable("query") String query)
    {
        service.koreaFantasy(query);
    }
    @GetMapping("/korea/horror/{query}")
    public void kHorror(@PathVariable("query") String query)
    {
        service.koreaHorror(query);
    }
}
