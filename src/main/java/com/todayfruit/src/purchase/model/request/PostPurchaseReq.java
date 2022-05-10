package com.todayfruit.src.purchase.model.request;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.user.model.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;




@Getter
@Setter
@AllArgsConstructor

public class PostPurchaseReq {

    private User user;  //구매자 인덱스
    private Product product;  //상품 인덱스
    private ProductOption productOption;  //구매할 상품옵션 인덱스



    @Max(value = 100, message = "구매 수량은 1~100개 까지만 입력 가능합니다.")
    @Min(value = 1, message = "구매 수량은 1~100개 까지만 입력 가능합니다.")
    private int purchaseCount;       //구매 개수

    private String shippingAddress;  //배송지 (형식이 되어서 오기때문에 필요 없음)







}
