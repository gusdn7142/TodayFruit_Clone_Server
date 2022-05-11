package com.todayfruit.src.product.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor

public class GetProductsRes {

    private Long productId;  //상품 인덱스
    private String deliveryType;  //배송 타입
    private String title;  //상품 제목
    private String image; //상품 이미지

    private String price;  //상품 가격

    private int discountRate;  //할인율


    private String discountPrice;  //할인된 가격

    private int saleCount;  //판매 수량

    //private int orderCount //주문자 수 (나중에 추가)


}
