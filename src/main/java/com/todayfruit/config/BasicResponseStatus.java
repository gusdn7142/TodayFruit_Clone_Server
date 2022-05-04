package com.todayfruit.config;


import lombok.Getter;

@Getter
public enum BasicResponseStatus {  //enum 타입


    /**
     * 1000 : 요청 성공
     */
    SUCCESS("SUCCESS", 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류 (형식적 validation 처리)
     */
    POST_USERS_INVALID_EMAIL("FAIL",2000,"이메일 형식을 확인해 주세요."),
    POST_USERS_EMPTY_PASSWORD("FAIL",2000,"비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD("FAIL",2000,"비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)."),
    POST_USERS_EMPTY_NAME("FAIL",2000,"이름을 입력해주세요."),
    POST_USERS_INVALID_PHONE("FAIL",2000,"핸드폰 번호를 형식에 맞게 입력해주세요."),
    POST_USERS_EMPTY_NICKNAME("FAIL",2000,"닉네임을 입력해주세요."),


    EMPTY_JWT("FAIL", 3001, "JWT 토큰을 입력해 주세요."),



    /**
     * 3000 : Response 오류 (의미적 Vdalidation 처리)
     */
    POST_USERS_EXISTS_EMAIL("FAIL",3001,"이미 가입된 이메일입니다."),
    FAILED_TO_JOIN_CHECK("FAIL",3001,"가입되지 않은 사용자입니다."),
    PASSWORD_MATCH_FAIL("FAIL",3001,"잘못된 패스워드입니다."),


    INVALID_JWT("FAIL", 3001, "JWT 토큰이 변조되었거나 만료되었습니다."),





    /**
     * 4000 : 서버(DB) 오류
     */
    DATABASE_ERROR_USER("FAIL", 4000, "DB에서 프로필 조회에 실해하였습니다."),
//    DATABASE_ERROR2("FAIL", 4000, "잘못된 형식의 값을 입력하셨습니다.");;
    DATABASE_ERROR_CREATE_USER("FAIL", 4000, "DB에 유저 등록이 실패하였습니다."),
    DATABASE_ERROR_SAVE_RefreshToken("FAIL", 4000, "refresh 토큰 저장에 실패하였습니다."),
    DATABASE_ERROR_LOGIN_USER("FAIL", 4000, "로그인에 실패하였습니다."),
    DATABASE_ERROR_INACTIVE_RefreshToken("FAIL", 4000, "refresh 토큰 비활성화에 실패하였습니다."),;




















    private String responseStatus;
    private int responseCode;
    private String message;

    private BasicResponseStatus(String responseStatus, int responseCode, String message) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.message = message;
    }





}