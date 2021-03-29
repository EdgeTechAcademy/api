package com.example.api.controllers;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/api/")
public class ApiController {

    private final RestTemplate restTemplate;

    //  get a RestTemplateBuilder and use it for all of the requests. One will be fine
    public ApiController(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    /*
     *      Added a Get Movies from Server button to the apiExamples page. The JS will make an AJAX request to this server
     *      and return the same JSON string it would have retrieved doing it client side
     */
    @GetMapping(path = "/movies/{title}", produces = { "application/json"})
    public ResponseEntity<?> listMovies2(@PathVariable String title) {
        String url = "https://imdb-api.com/en/API/SearchMovie/k_lLeNEBFq/" + title;
        return new ResponseEntity<>(this.restTemplate.getForObject(url, String.class), HttpStatus.OK);
    }
}
