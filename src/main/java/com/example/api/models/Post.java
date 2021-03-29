package com.example.api.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Table;
import javax.websocket.server.ServerEndpoint;

@Getter
@Setter
@Table(name = "posts")
public class Post {
    private int userId;
    private int id;
    private String title;
    private String body;
}
