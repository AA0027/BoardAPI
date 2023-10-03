package com.example.test.controller.movie;

import com.example.test.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class JaMovieController {
    private final MovieService service;

    @GetMapping("/japan/{query}")
    public void jaMovies(@PathVariable("query") String query)
    {
        service.japanMovie(query);
    }
    @GetMapping("/japan/drama/{query}")
    public void jaDrama(@PathVariable("query") String query)
    {
        service.japanDrama(query);
    }
    @GetMapping("/japan/Fantasy/{query}")
    public void jaFantasy(@PathVariable("query") String query)
    {
        service.japanFantasy(query);
    }
    @GetMapping("/japan/horror/{query}")
    public void jaHorror(@PathVariable("query") String query)
    {
        service.japanHorror(query);
    }
}
