package com.smilie.smiliebackend.joke.repository;


import com.smilie.smiliebackend.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JokeRepository extends JpaRepository<Joke, Long> {
    boolean existsBySetupAndDelivery(String setup, String delivery);
    Optional<Joke> findBySetupAndDelivery(String setup, String delivery);
}
