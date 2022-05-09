package com.todayfruit.src.product.model.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor


public class GetProductAndUserRes {

    GetProductRes getProductRes;
    private String sellerName;  //판매자 이름

}
