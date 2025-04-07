package com.smilie.smiliebackend.joke.controller;

import com.smilie.smiliebackend.joke.service.JokeService;
import com.smilie.smiliebackend.joke.response.JokeResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class JokeController {
    private final JokeService jokeService;

    @GetMapping("/joke")
    public ResponseEntity<JokeResponse> getJoke() {
        JokeResponse response = jokeService.getNewJokeAndStoreJokeIfNotExists();
        return ResponseEntity.ok(response);
    }
}
