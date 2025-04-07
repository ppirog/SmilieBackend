package com.smilie.smiliebackend.joke.repository;


import com.smilie.smiliebackend.joke.model.Joke;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JokeRepository extends JpaRepository<Joke, Long> {
    boolean existsBySetupAndDelivery(String setup, String delivery);
}
