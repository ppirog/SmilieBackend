package com.smilie.smiliebackend.joke.controller;

import com.smilie.smiliebackend.joke.model.Joke;
import com.smilie.smiliebackend.joke.service.JokeService;
import com.smilie.smiliebackend.joke.response.JokeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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

    @PostMapping("/{id}/like")
    public ResponseEntity<String> likeJoke(@PathVariable Long id, Principal principal) {
        String login = principal.getName();

        boolean liked = jokeService.likeJoke(id, login);

        if (liked) {
            return ResponseEntity.ok("Joke liked!");
        } else {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Joke already liked by user");
        }
    }

    @GetMapping("/jokes/{id}/likes")
    public int getLikeCount(@PathVariable Long id) {
        return jokeService.getLikes(id);
    }
}
