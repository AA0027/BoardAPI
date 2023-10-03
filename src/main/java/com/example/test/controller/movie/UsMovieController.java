package com.example.test.controller.movie;

import com.example.test.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UsMovieController {
    private final MovieService service;

    @GetMapping("/us/{query}")
    public void usMovies(@PathVariable("query") String query)
    {
        service.usMovie(query);
    }
    @GetMapping("/us/drama/{query}")
    public void usDrama(@PathVariable("query") String query)
    {
        service.usDrama(query);
    }
    @GetMapping("/us/fantasy/{query}")
    public void usFantasy(@PathVariable("query") String query)
    {
        service.usFantasy(query);
    }
    @GetMapping("/us/horror/{query}")
    public void usHorror(@PathVariable("query") String query)
    {
        service.usHorror(query);
    }
}
