package com.smilie.smiliebackend.joke.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class JokeResponse {
    private Long id;
    private String setup;
    private String delivery;
    private int likeCount;
}

