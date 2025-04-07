package com.smilie.smiliebackend.joke.controller;

import com.smilie.smiliebackend.joke.service.JokeService;
import com.smilie.smiliebackend.joke.response.JokeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@AllArgsConstructor
public class JokeController {
    private final JokeService jokeService;

    @GetMapping("/joke")
    public ResponseEntity<JokeResponse> getJoke() {
        JokeResponse response = jokeService.getNewJokeAndStoreJokeIfNotExists();
        log.info("JOKE RESPONSE: {}", response);
        return ResponseEntity.ok(response);
    }
}
