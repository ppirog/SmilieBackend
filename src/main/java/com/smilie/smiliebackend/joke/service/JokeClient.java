package com.smilie.smiliebackend.joke.service;

import com.smilie.smiliebackend.joke.response.JokeResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "jokeClient", url = "https://v2.jokeapi.dev")
public interface JokeClient {
    @GetMapping("/joke/Any?type=twopart")
    JokeResponse getNewJoke();
}
