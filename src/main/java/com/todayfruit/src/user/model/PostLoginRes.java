package com.todayfruit.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostLoginRes {

    private Long id;
    private String accessToken;
    private String refreshToken;
}
