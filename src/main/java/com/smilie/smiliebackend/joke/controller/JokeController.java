package com.smilie.smiliebackend.joke.controller;

import com.smilie.smiliebackend.joke.response.JokeResponse;
import com.smilie.smiliebackend.joke.service.JokeService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

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

    @GetMapping("/jokes")
    public ResponseEntity<List<JokeResponse>> getJokesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<JokeResponse> jokes = jokeService.getJokesSortedByLikesPaged(page, size);
        return ResponseEntity.ok(jokes);
    }

    @GetMapping("/random-unliked")
    public ResponseEntity<JokeResponse> getRandomUnlikedJoke(Principal principal) {
        String login = principal.getName();
        JokeResponse joke = jokeService.getRandomUnlikedJoke(login);
        return ResponseEntity.ok(joke);
    }

}
