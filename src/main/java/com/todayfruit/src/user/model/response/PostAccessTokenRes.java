package com.todayfruit.src.user.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class PostAccessTokenRes {

    Long userId;
    String accessToken;

}
