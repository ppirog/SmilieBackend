package com.smilie.smiliebackend.joke.model;

import jakarta.persistence.*;
import lombok.*;

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
}
