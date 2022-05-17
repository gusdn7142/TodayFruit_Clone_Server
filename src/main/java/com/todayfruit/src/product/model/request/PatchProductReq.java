package com.todayfruit.src.product.model.request;


import com.fasterxml.jackson.annotation.JsonGetter;
import com.todayfruit.src.user.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor


public class PatchProductReq {

    private Long productId;

    //    @NotBlank(message="배송타입 형식을 확인해 주세요.")
    @Pattern(regexp = "^(쿠배송|무료배송)$", message="배송타입 형식을 확인해 주세요.")
    private String deliveryType;  //배송 타입 ("쿠배송" or "무료배송") : 한글 4글자


    @Pattern(regexp = "^[0-9a-zA-Z가-힣\\s.,()]{4,30}$", message="상품제목 형식을 확인해 주세요.")
    private String title;  //상품 제목 : 한글, 숫자, 영어 등 최소 4자 ~ 최대 30자

    //정규표현식 지정 필요
    private String image;  //형식이 정해져 있음.


    @Pattern(regexp = "^[0-9]{3,10}원$", message="상품가격 형식을 확인해 주세요.")
    private String price;  //상품 가격 : 100만원 이하?

    //@Pattern(regexp = "^[0-9]{1,2}$$", message="할인율 형식을 확인해 주세요.")
    //@Size(min=0, max=99, message="할인율 형식을 확인해 주세요.")
    @Max(value = 99, message = "할인율 형식을 확인해 주세요.")
    @Min(value = 0, message = "할인율 형식을 확인해 주세요.")
    private int discountRate;  //할인율 : 0~99%

    //    //@Pattern(regexp = "^[0-9]{1,4}$$", message="판매수량 형식을 확인해 주세요.")
//    @Size(min=10, max=3000, message="판매수량 형식을 확인해 주세요.")
    @Max(value = 3000, message = "판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)")
    @Min(value = 10, message = "판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)")
    private int saleCount;  //판매수량 : 최대 1000개


    private String description;  //상품설명 : 이미지 형식 (알아서 들어온다?)

    private Date deliveryDay;  //배송일 : "2019-10-31" 형식   (아마도 날짜 선택을 통해 폼을 전송해 줄것이다!!!)


    private String discountPrice;  //할인된 가격 (직접 입력받지 않음.)


    //상품 옵션에 해당 (productOption 테이블)
//    @Pattern(regexp = "^[0-9a-zA-Z가-힣\\s.]{4,20}$", message="상품옵션 형식을 확인해 주세요.")
    private List<Long> productOptionId;
    private List<@Pattern(regexp = "^[0-9a-zA-Z가-힣\\s.,()]{2,20}$", message="상품옵션 형식을 확인해 주세요.") String> optionName;




//    JSONObject productOption = new JSONObject();

//    @JsonGetter
//    public JSONObject getPoductOption() {
//        return productOption;
//    }






}
