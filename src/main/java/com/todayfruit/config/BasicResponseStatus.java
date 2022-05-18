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
    POST_PRODUCTS_EMPTY_IMAGE("FAIL", 2013, "상품 이미지를 입력해 주세요."),
    POST_PRODUCTS_INVALID_DiscountRate("FAIL", 2014, "할인율 형식을 확인해 주세요."),
    POST_PRODUCTS_INVALID_saleCount("FAIL", 2015, "판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)"),
    POST_PRODUCTS_EMPTY_DeliveryDay("FAIL", 2016, "배송일을 입력해 주세요."),
    POST_PRODUCT_OPTIONS_INVALID_optionName("FAIL", 2017, "상품옵션 형식을 확인해 주세요."),


    //purchase 도메인
    POST_PURCHASE_INVALID_PurchaseCount("FAIL", 2017, "구매 수량은 1~100개 까지만 입력 가능합니다."),


    //Review 도메인
    POST_REVIEWS_INVALID_CONTENT("FAIL", 2020, "리뷰 내용은 최대 한글 100자 까지만 입력 가능합니다."),
    POST_REVIEWS_INVALID_SCORE("FAIL", 2021, "리뷰 점수는 1~5점 사이로 입력해 주세요."),




    /**
     * 3000 : Response 오류 (의미적 Vdalidation 처리)
     */
    POST_USERS_EXISTS_EMAIL("FAIL",3000,"이미 가입된 이메일 입니다."),
    POST_USERS_EXISTS_NICKNAME("FAIL",3001,"이미 존재하는 닉네임 입니다."),
    FAILED_TO_JOIN_CHECK("FAIL",3002,"가입되지 않은 사용자입니다."),
    PASSWORD_MATCH_FAIL("FAIL",3003,"잘못된 패스워드입니다."),

    PATCH_USERS_DELETE_USER("FAIL",3004,"회원 탈퇴된 계정입니다."),
    PATCH_USERS_LOGOUT_USER("FAIL",3005,"로그아웃된 유저입니다."),


    //product 도메인
    PATCH_PRODUCTS_DELETE_PRDOCUT("FAIL",3010,"삭제된 상품입니다."),
    PATCH_PRODUCTS_NOT_EXISTS_PRDOCUT_OPTION("FAIL", 3012, "해당 상품 옵션 인덱스에 해당하는 상품을 찾을 수 없습니다."),
    GET_PRODUCTS_NOT_EXISTS_PRDOCUT_OPTION("FAIL",3011,"상품 옵션을 찾을 수 없습니다."),
    PATCH_PRODUCTS_DIFFERENT_COUNT_PRDOCUT_OPTION("FAIL", 3013, "해당 상품의 옵션 개수에 맞개 옵션 인덱스와 옵션 명을 입력해 주세요."),


    //purchase 도메인
    PATCH_PRODUCTS_DELETE_PURCHASE("FAIL",3020,"삭제된 주문 정보입니다."),


    //review 도메인
    POST_REVIEWS_EXISTS_REVIEW("FAIL",3031,"이미 해당 상품의 리뷰를 작성하셨습니다."),
    POST_REVIEWS_NOT_EXISTS_STAR("FAIL",3032,"리뷰 점수를 별점으로 변환하는데 실패하였습니다."),
    PATCH_REIVEWS_NOT_SAME_REVIEWER("FAIL",3033,"리뷰 작성자와 수정자가 일치하지 않습니다."),
    PATCH_REVIEWS_DELETE_REVIEW("FAIL",3034,"삭제된 리뷰입니다."),
    POST_REIVEWS_NOT_PURCHASER("FAIL",3035,"상품을 구매한 유저가 아닙니다."),



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
    DATABASE_ERROR_FAI_Reissuance_AccessToken("FAIL", 4011, "Access 토큰 재발급에 실패하였습니다."),


    //product 도메인
    DATABASE_ERROR_CREATE_PRODUCT("FAIL", 4020, "DB에 상품 등록에 실패하였습니다."),
    DATABASE_ERROR_MODIFY_FAIL_PRODUCT("FAIL", 4021, "상품 정보 변경시 오류가 발생하였습니다."),
    //DATABASE_ERROR_MODIFY_FAIL_PRODUCTS_OptionName("FAIL", 4028, "상품 옵션 변경시 오류가 발생하였습니다."),
    DATABASE_ERROR_GET_FAIL_PRODUCTS("FAIL", 4029, "상품 조회에 실패하였습니다."),
    DATABASE_ERROR_DELETE_PRODUCTS("FAIL", 4030, "상품 삭제에 실패하였습니다."),
    DATABASE_ERROR_GET_FAIL_PRODUCT_OPTIONS("FAIL", 4031, "상품 옵션 조회에 실패하였습니다."),


    //purchase 도메인
    DATABASE_ERROR_CREATE_PURCHASE("FAIL", 4032, "상품 구매에 실패하였습니다."),
    DATABASE_ERROR_GET_FAIL_PURCHASE("FAIL", 4033, "상품 주문 정보 조회에 실패하였습니다."),
    DATABASE_ERROR_DELETE_PURCHASE("FAIL", 4033, "해당 주문 취소에 실패하였습니다."),


    //review 도메인
    DATABASE_ERROR_CREATE_REVIEW("FAIL", 4040, "DB에 리뷰 등록이 실패하였습니다."),
    DATABASE_ERROR_MODIFY_FAIL_REVIEW("FAIL", 4041, "상품 리뷰 수정시 오류가 발생하였습니다."),
    DATABASE_ERROR_DELETE_REVIEWS("FAIL", 4042, "리뷰 삭제에 실패하였습니다."),
    DATABASE_ERROR_GET_FAIL_REVIEW("FAIL", 4043, "상품 리뷰 조회에 실패하였습니다.");



    private String responseStatus;
    private int responseCode;
    private String message;

    private BasicResponseStatus(String responseStatus, int responseCode, String message) {
        this.responseStatus = responseStatus;
        this.responseCode = responseCode;
        this.message = message;
    }





}