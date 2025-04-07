package com.smilie.smiliebackend.joke.service;

import com.smilie.smiliebackend.joke.model.Joke;
import com.smilie.smiliebackend.joke.repository.JokeRepository;
import com.smilie.smiliebackend.joke.response.JokeResponse;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class JokeService {
    private final JokeClient jokeClient;
    private final JokeRepository jokeRepository;

    public JokeResponse getNewJokeAndStoreJokeIfNotExists() {
        JokeResponse jokeResponse = jokeClient.getNewJoke();

        boolean exists = jokeRepository.existsBySetupAndDelivery(jokeResponse.getSetup(), jokeResponse.getDelivery());

        if (!exists) {
            Joke joke = new Joke();
            joke.setSetup(jokeResponse.getSetup());
            joke.setDelivery(jokeResponse.getDelivery());

            Joke saved = jokeRepository.save(joke);
            return JokeResponse.builder()
                    .setup(saved.getSetup())
                    .delivery(saved.getDelivery())
                    .build();
        }

        return jokeResponse;
    }
}
