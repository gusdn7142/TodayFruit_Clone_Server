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

    //user 도메인
    POST_USERS_INVALID_EMAIL("FAIL",2000,"이메일 형식을 확인해 주세요."),
    POST_USERS_EMPTY_PASSWORD("FAIL",2001,"비밀번호를 입력해주세요."),
    POST_USERS_INVALID_PASSWORD("FAIL",2002,"비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)."),
    POST_USERS_INVALID_NAME("FAIL",2003,"이름 형식을 확인해 주세요. (한글 이름 2~4자 이내)"),
    POST_USERS_INVALID_PHONE("FAIL",2004,"핸드폰 번호를 형식에 맞게 입력해주세요."),
    POST_USERS_INVALID_NICKNAME("FAIL",2005,"닉네임 형식을 확인해 주세요."),


    EMPTY_ACCESS_TOKEN("FAIL", 2101, "Access Token을 입력해 주세요."),
    INVALID_ACCESS_TOKEN("FAIL", 2102, "Access Token이 변조되었거나 만료되었습니다."),
    INVALID_USER_JWT("FAIL",2103,"권한이 없는 유저의 접근입니다."),
    NOT_INVALID_ACCESS_TOKEN("FAIL", 2104, "Access Token이 아직 만료되지 않았습니다."),


    EMPTY_REFRESH_TOKEN("FAIL", 2111, "Refresh Token을 입력해 주세요."),
    INVALID_REFRESH_TOKEN("FAIL", 2112, "Refresh Token이 변조되었거나 만료되었습니다."),




    //prdouct 도메인
    POST_PRODUCTS_INVALID_DeliveryType("FAIL", 2010, "배송타입 형식을 확인해 주세요."),
    POST_PRODUCTS_INVALID_Title("FAIL", 2011, "상품제목 형식을 확인해 주세요."),
    POST_PRODUCTS_INVALID_Price("FAIL", 2012, "상품가격 형식을 확인해 주세요."),
    POST_PRODUCTS_INVALID_DiscountRate("FAIL", 2013, "할인율 형식을 확인해 주세요."),
    POST_PRODUCTS_INVALID_saleCount("FAIL", 2014, "판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)"),
    POST_PRODUCTS_EMPTY_DeliveryDay("FAIL", 2015, "배송일을 입력해 주세요."),
    POST_PRODUCT_OPTIONS_INVALID_optionName("FAIL", 2016, "상품옵션 형식을 확인해 주세요."),


    /**
     * 3000 : Response 오류 (의미적 Vdalidation 처리)
     */
    POST_USERS_EXISTS_EMAIL("FAIL",3000,"이미 가입된 이메일 입니다."),
    POST_USERS_EXISTS_NICKNAME("FAIL",3001,"이미 존재하는 닉네임 입니다."),
    FAILED_TO_JOIN_CHECK("FAIL",3002,"가입되지 않은 사용자입니다."),
    PASSWORD_MATCH_FAIL("FAIL",3003,"잘못된 패스워드입니다."),

    PATCH_USERS_DELETE_USER("FAIL",3004,"이미 탈퇴된 계정입니다."),
    PATCH_USERS_LOGOUT_USER("FAIL",3005,"로그아웃된 유저입니다."),





    /**
     * 4000 : 서버(DB) 오류
     */
    DATABASE_ERROR_USER("FAIL", 4000, "DB에서 프로필 조회에 실해하였습니다."),
//    DATABASE_ERROR2("FAIL", 4000, "잘못된 형식의 값을 입력하셨습니다.");;
    DATABASE_ERROR_CREATE_USER("FAIL", 4001, "DB에 유저 등록이 실패하였습니다."),
    DATABASE_ERROR_INACTIVE_RefreshToken("FAIL", 4002, "refresh 토큰 비활성화에 실패하였습니다."),
    DATABASE_ERROR_SAVE_RefreshToken("FAIL", 4003, "refresh 토큰 저장에 실패하였습니다."),
    DATABASE_ERROR_LOGIN_USER("FAIL", 4004, "로그인에 실패하였습니다."),

    DATABASE_ERROR_MODIFY_FAIL_USER_NAME("FAIL", 4005, "이름 변경시 오류가 발생하였습니다."),
    DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME("FAIL", 4006, "닉네임 변경시 오류가 발생하였습니다."),
    DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION("FAIL", 4007, "소개글 변경시 오류가 발생하였습니다."),
    DATABASE_ERROR_MODIFY_FAIL_USER_IMAGE("FAIL", 4008, "이미지 변경시 오류가 발생하였습니다."),
    DATABASE_ERROR_DELETE_USER("FAIL", 4009, "회원탈퇴에 실패하였습니다."),
    DATABASE_ERROR_FAIL_LOGOUT("FAIL", 4010, "로그아웃에 실패 하였습니다."),


    //product 도메인
    DATABASE_ERROR_CREATE_PRODUCT("FAIL", 4020, "DB에 상품 등록이 실패하였습니다.");








    private String responseStatus;
    private int responseCode;
    private String message;

    private BasicResponseStatus(String responseStatus, int responseCode, String message) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.message = message;
    }





}