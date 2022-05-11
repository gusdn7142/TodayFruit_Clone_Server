package com.todayfruit.src.purchase.model.response;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor


public class GetPurchaseRes {


    private Long purchaseId; //구매 인덱스
    private String title;  //상품 제목
    private String image;  //상품 이미지
    private String optionName;  //상폼 웁션 명

    private int purchaseCount;  //구매 수량
    private String totalPrice; //상품 금액
    private String shippingAddress; //배송지
    private String deliveryPrice;  //배송비









}
