package com.smilie.smiliebackend.joke.model;

import com.smilie.smiliebackend.loginandregister.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor
@Table(name = "joke" , indexes = {
        @Index(name = "idx_setup", columnList = "setup"),
        @Index(name = "idx_delivery", columnList = "delivery")
})
public class Joke {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String setup;
    private String delivery;

    @ManyToMany
    @JoinTable(
            name = "joke_likes",
            joinColumns = @JoinColumn(name = "joke_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"joke_id", "user_id"})
    )
    private Set<User> likedByUsers = new HashSet<>();
}
