package com.todayfruit.src.product;


import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.todayfruit.config.BasicResponseStatus.*;

@RestController  //Responsebody +controller!!
@RequestMapping("products")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;




//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 8. 상품 등록 API
     * [POST] /users
     * @return BaseResponse
     */
    // Body
    @PostMapping("/{userId}") //
    public BasicResponse createProduct(@PathVariable("userId") Long userId  , @Valid @RequestBody PostProductReq postProductReq, BindingResult bindingResult ) {  //@RequestBody PostUserReq postUserReq


        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.
//
            System.out.println(bindingResult.getAllErrors());
            if (errorlist.get(0).getDefaultMessage().equals("배송타입 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_DeliveryType);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("상품제목 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_Title);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("상품가격 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_Price);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("할인율 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_DiscountRate);
            }
            if (errorlist.get(0).getDefaultMessage().equals("판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_saleCount);
            }
            if (errorlist.get(0).getDefaultMessage().equals("배송일을 입력해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_EMPTY_DeliveryDay);
            }
            if (errorlist.get(0).getDefaultMessage().equals("상품옵션 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCT_OPTIONS_INVALID_optionName);
            }

        }
        /* 유효성 검사 구현 끝*/


        //System.out.println(postProductReq.getDeliveryDay());


        try{
            //DB에 상품 등록
            String responseMessage = productService.createProduct(postProductReq,userId);

            return new BasicResponse(responseMessage);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }
    }











//        try{
    //DB에 상품 등록
    //String responseMessage = productService.createProduct(postUserReq);

    //String str = "에라 모르겠다" + " , " + postProductReq.getDeliveryType() + " , " + postProductReq.getTitle() + " , " + postProductReq.getPrice() + " , " + postProductReq.getDiscountRate() + " , " + postProductReq.getDiscountRate() +
    //postProductReq.getSaleCount() + " , " + postProductReq.getDescription() ; //+ " , " + postProductReq.getDeliveryDay();

//    String str2 = "성공";
//
//            return new BasicResponse(str2);
//        } catch(BasicException exception){
//            return new BasicResponse(exception.getStatus());
//        }



















}
