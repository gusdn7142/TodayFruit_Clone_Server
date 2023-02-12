package com.todayfruit.src.user.model.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class PostLoginRes {

    private Long userId;
    private String accessToken;
    private String refreshToken;
}
