package com.todayfruit.src.review.model.request;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.user.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {

    private User user;  //리뷰 등록자
    private Product product; //리뷰 등록할 상품
    private String star; //별 이미지


    //입력해야할 부분
    private String image;  //이미지 형식에 맞게 들어옴.

    @Size(max=100,message="최대 100자 까지만 입력 가능합니다." )
    private String content;  //리뷰 내용

    @Max(value = 5, message = "1~5점 사이로 입력해 주세요.")
    @Min(value = 1, message = "1~5점 사이로 입력해 주세요.")
    private int score;  //리뷰 점수




}
