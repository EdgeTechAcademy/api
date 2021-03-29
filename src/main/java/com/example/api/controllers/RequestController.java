package com.example.api.controllers;

import com.example.api.models.Imdb;
import com.example.api.models.Movie;
import com.example.api.models.Post;
import com.example.api.services.MovieService;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;

@Controller
@RequestMapping("/req")
public class RequestController {

    /*
     *      Good article on use the RestTemplate object to get HTTP requests
     *      https://attacomsian.com/blog/http-requests-resttemplate-spring-boot
     *
     *      We have 4 End Points
     *      TODO    /req/movies/{title}
     *      Ask IMDB for a list of movies matching the title
     *          page rendered:
     *                  "movieList"
     *          model attributes passed:
     *                  movies: imdb.results        //  the list of movies from IMDB
     *          JSON format:
     *              models the Imdb object
     *                  String searchType;
     *                  String expression;
     *                  Movie[] results;            //  this is what we are looking for
     *                  String errorMessage;
     *      TODO   /req/examples
     *      displays a test page to exercise different AJAX calls.
     *          page rendered:  "apiExamples"
     *          model attributes passed:    none
     *
     *              ATTENTION Movie search:       IMDB API request
     *              click on the poster to to more details with a separate API request
     *                  click on the title to go to IMDB
     *              ATTENTION Weather Forecast:   openWeather API request
     *              enter a city or a lon/lat to get the weather report
     *                  city and lon/lat will use two different API requests. The JSON result is reformatted
     *                  to the so the page rendering can use the same JSON
     *              ATTENTION APOD:               APOD API request
     *              select a date and generate the Astronomy Picture Of the Day
     *                  if the picture of the day is a video the video will run
     *
     *      TODO    /req/posts
     *      makes a request to pseudo API service to return an array of Posts
     *          page rendered:
     *                  "postList"
     *          model attributes passed:
     *                  posts: posts                //  an array of Post objects
     *          JSON format:
     *              models a Post object
     *                  int userId;
     *                  int id;
     *                  String title;
     *                  String body;
     *      TODO /req/posts/{userId}
     *      makes a request to pseudo API service to return an array of Posts for a specific userId
     *          The rest is the same as the /req/posts above
     */
    private final RestTemplate restTemplate;
    private MovieService movieService;

    //  get a RestTemplateBuilder and use it for all of the requests. One will be fine
    public RequestController(RestTemplateBuilder restTemplateBuilder, MovieService movieService) {
        this.restTemplate = restTemplateBuilder.build();
        this.movieService = movieService;
    }

    /*
     *      Read the local database
     */
    @GetMapping(path = "/")
    public String listAllMovies(Model model) {
        Iterable<Movie> movies = movieService.listAllMovies();
        var arMovies = ((ArrayList)movies).toArray();
        model.addAttribute("movies", arMovies);
        model.addAttribute("title", "All Movies in DB");
        return "movieList";
    }

    /*
     *      search IMDB for a movie title
     *      This is a GET method. Title is on the URL
     */
    @GetMapping(path = "/movies/{title}")
    public String listMovies(@PathVariable String title, Model model) {
        return findMovies(title, model);
    }

    /*
     *      search IMDB for a movie title
     *      This is a POST method. Titles is from the form
     */
    @PostMapping(path = "/movies")
    public String listMoviesForm(@RequestParam String title, Model model) {
        return findMovies(title, model);
    }

    /*
     *      Both methods can use this function to get the movie data
     *          ATTENTION: This causes warnings in movieList.html since it will not
     *           be able to detect that the model object is returning a "movies" list or title
     */
    String findMovies(String title, Model model) {
        String url = "https://imdb-api.com/en/API/SearchMovie/k_lLeNEBFq/" + title;
        Imdb imdb = this.restTemplate.getForObject(url, Imdb.class);
        model.addAttribute("movies", imdb.getResults());
        model.addAttribute("title", title);
        return "movieList";
    }

    @GetMapping(path = "/movies/add/{id}/{title}/{image}/{description}")
    public String addMovie(@PathVariable String id, @PathVariable String title, @PathVariable String image, @PathVariable String description, Model model) {
        //String title, String image, String description
        Movie movie = new Movie();
        movie.setDescription(description);
        movie.setId(id);
        movie.setTitle(title);
        movie.setImage("https://imdb-api.com/images/original/" + image);
        movieService.add(movie);

        ArrayList<Movie> m = new ArrayList<>();
        m.add(movie);
        var arMovies = m.toArray();
        model.addAttribute("movies", arMovies);
        model.addAttribute("title", title);
        return "movieList";
    }

    /*
     *      No data, no interaction with the database
     *      just display the examples page and we will let the JS do all of the work
     */
    @GetMapping(path = "/examples")
    public String apiExamples() {
        return "apiExamples";
    }

    /*
     *      Make a request to a pseudo API server to get Post objects
     */
    @GetMapping(path = "/posts")
    public String listPosts(Model model) {
        String url;
        url = "https://jsonplaceholder.typicode.com/posts";
        var posts = restTemplate.getForObject(url, Post[].class);
        model.addAttribute("posts", posts);
        return "postList";
    }

    /*
     *      Same as above but we are searching for Posts from a particular userId
     */
    @GetMapping(path = "/posts/{userId}")
    public String listPostsForUser(@PathVariable String userId, Model model) {
        String url;
        url = "https://jsonplaceholder.typicode.com/posts?userId=" + userId;
        var posts = restTemplate.getForObject(url, Post[].class);
        model.addAttribute("posts", posts);
        return "postList";
    }
}
