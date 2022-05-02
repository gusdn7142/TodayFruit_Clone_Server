package com.todayfruit.config;


import lombok.Getter;

@Getter
public enum BasicResponseStatus {  //enum 타입


    /**
     * 1000 : 요청 성공
     */
    SUCCESS("SUCCESS", 1000, "요청에 성공하였습니다."),



    /**
     * 5000 : 서버(DB) 오류
     */
    DATABASE_ERROR_USER("FAIL", 4000, "DB에서 프로필 조회에 실해하였습니다."),
    DATABASE_ERROR2("FAIL", 4000, "잘못된 형식의 값을 입력하셨습니다.");;












    private String responseStatus;
    private int responseCode;
    private String message;

    private BasicResponseStatus(String responseStatus, int responseCode, String message) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.message = message;
    }





}