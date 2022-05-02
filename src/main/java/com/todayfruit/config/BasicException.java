package com.todayfruit.config;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class BasicException extends Exception{  //Exception 클래스 상속 받음
    private BasicResponseStatus status;
}
