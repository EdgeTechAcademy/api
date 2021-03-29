package com.example.api.services;

import com.example.api.models.Movie;
import com.example.api.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {
    @Autowired
    private MovieRepository movieRepository;

    public Iterable<Movie> listAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Integer id) {
        Optional<Movie> oMovie = movieRepository.findById(id);
        return oMovie.orElse(null);
    }

    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> oMovie = movieRepository.findById(id);
        oMovie.ifPresent(movie -> movieRepository.delete(movie));
    }

    public Movie add(Movie movie) {
        return movieRepository.save(movie);
    }

//    public Iterable<Movie> findByTitle(String title) {
//        return movieRepository.findByTitleContaining(title);
//    }
}
