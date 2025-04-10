package com.smilie.smiliebackend.joke.service;

import com.smilie.smiliebackend.infrastructure.errorvalidation.JokeNotFoundException;
import com.smilie.smiliebackend.joke.model.Joke;
import com.smilie.smiliebackend.joke.repository.JokeRepository;
import com.smilie.smiliebackend.joke.response.JokeResponse;
import com.smilie.smiliebackend.loginandregister.User;
import com.smilie.smiliebackend.loginandregister.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@AllArgsConstructor
public class JokeService {
    private final JokeClient jokeClient;
    private final JokeRepository jokeRepository;
    private final UserRepository userRepository;

    public JokeResponse getNewJokeAndStoreJokeIfNotExists() {
        JokeResponse jokeResponse = jokeClient.getNewJoke();

        boolean exists = jokeRepository.existsBySetupAndDelivery(jokeResponse.getSetup(), jokeResponse.getDelivery());

        if (!exists) {
            Joke joke = new Joke();
            joke.setSetup(jokeResponse.getSetup());
            joke.setDelivery(jokeResponse.getDelivery());

            Joke saved = jokeRepository.save(joke);
            log.info("SAVING IN DATABASE: {}", saved);

            return JokeResponse.builder()
                    .setup(saved.getSetup())
                    .delivery(saved.getDelivery())
                    .build();
        }

        log.info("ALREADY IN DATABASE: {}", jokeResponse);
        return jokeResponse;
    }

    public boolean likeJoke(Long jokeId, String login) {
        Joke joke = jokeRepository.findById(jokeId)
                .orElseThrow(() -> new JokeNotFoundException("Joke not found"));

        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        if (joke.getLikedByUsers().contains(user)) {
            return false;
        }

        joke.getLikedByUsers().add(user);
        jokeRepository.save(joke);
        return true;
    }

    public int getLikes(Long id) {
        Joke joke = jokeRepository.findById(id)
                .orElseThrow(() -> new JokeNotFoundException("Joke not found"));
        return joke.getLikedByUsers().size();
    }
}
