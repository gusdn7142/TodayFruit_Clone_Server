package com.todayfruit.src.review.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewRes {

    private Long reviewId;  //리뷰 인덱스
    private String star;  //별점
    private String userName; //사용자 이름
    private String updateAt;  //작성일 (UpdateAt 칼럼 사용)
    private String reviewImage;  //리뷰 이미지
    private String content;  //리뷰 내용


}
