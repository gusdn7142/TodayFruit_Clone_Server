package com.todayfruit.src.review.model.request;


import com.sun.istack.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Null;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PatchReviewReq {

//    private Long reviewId;

    private String star;  //별 이미지


    //직접 입력받는 값들
    @Size(max=100,message="최대 100자 까지만 입력 가능합니다." )
    private String content;  //리뷰 내용

    @Max(value = 5, message = "1~5점 사이로 입력해 주세요.")
    @Min(value = 1, message = "1~5점 사이로 입력해 주세요.")
    private int score;  //리뷰 점수

    private String image;  //리뷰 이미지


}
