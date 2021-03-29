package com.example.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;

@Getter
@Setter
@Table(name = "imdb")
public class Imdb {
    private String searchType;
    private String expression;
    private Movie[] results;
    private String errorMessage;
}
