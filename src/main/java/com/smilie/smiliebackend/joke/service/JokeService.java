package com.smilie.smiliebackend.joke.service;

import com.smilie.smiliebackend.infrastructure.errorvalidation.AllJokeLikedException;
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

import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Log4j2
@Service
@AllArgsConstructor
public class JokeService {
    private final JokeClient jokeClient;
    private final JokeRepository jokeRepository;
    private final UserRepository userRepository;

    public JokeResponse getNewJokeAndStoreJokeIfNotExists() {
        JokeResponse jokeResponse = jokeClient.getNewJoke();

        boolean exists = jokeRepository.existsBySetupAndDelivery(
                jokeResponse.getSetup(), jokeResponse.getDelivery()
        );

        if (!exists) {
            Joke joke = new Joke();
            joke.setSetup(jokeResponse.getSetup());
            joke.setDelivery(jokeResponse.getDelivery());

            Joke saved = jokeRepository.save(joke);
            log.info("SAVING IN DATABASE: {}", saved);

            return JokeResponse.builder()
                    .id(saved.getId())
                    .setup(saved.getSetup())
                    .delivery(saved.getDelivery())
                    .likeCount(saved.getLikedByUsers().size())
                    .build();
        }

        Joke joke = jokeRepository.findBySetupAndDelivery(
                jokeResponse.getSetup(), jokeResponse.getDelivery()
        ).orElseThrow(() -> new JokeNotFoundException("Joke not found"));

        log.info("ALREADY IN DATABASE: {}", joke);

        return JokeResponse.builder()
                .id(joke.getId())
                .setup(joke.getSetup())
                .delivery(joke.getDelivery())
                .likeCount(joke.getLikedByUsers().size())
                .build();
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

    public List<JokeResponse> getJokesSortedByLikesPaged(int page, int size) {
        List<JokeResponse> allJokes = jokeRepository.findAll().stream()
                .map(joke -> JokeResponse.builder()
                        .id(joke.getId())
                        .setup(joke.getSetup())
                        .delivery(joke.getDelivery())
                        .likeCount(joke.getLikedByUsers().size())
                        .build())
                .sorted(Comparator.comparingInt(JokeResponse::getLikeCount).reversed())
                .toList();

        int start = page * size;
        int end = Math.min(start + size, allJokes.size());

        if (start >= allJokes.size()) {
            return List.of();
        }

        return allJokes.subList(start, end);
    }

    public JokeResponse getRandomUnlikedJoke(String login) {
        User user = userRepository.findByLogin(login)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        List<Joke> unlikedJokes = jokeRepository.findAll().stream()
                .filter(joke -> !joke.getLikedByUsers().contains(user))
                .toList();

        if (unlikedJokes.isEmpty()) {
            throw new AllJokeLikedException("No unliked jokes available");
        }

        Joke randomJoke = unlikedJokes.get(new Random().nextInt(unlikedJokes.size()));

        return JokeResponse.builder()
                .id(randomJoke.getId())
                .setup(randomJoke.getSetup())
                .delivery(randomJoke.getDelivery())
                .likeCount(randomJoke.getLikedByUsers().size())
                .build();
    }

}
