package com.todayfruit.src.product;


import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.request.PatchProductReq;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.product.model.response.*;
import com.todayfruit.src.user.LogoutDao;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.Logout;
import com.todayfruit.src.user.model.domain.User;
import com.todayfruit.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.todayfruit.config.BasicResponseStatus.*;



@RestController
@RequestMapping("products")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class ProductController {

    private final ProductService productService;
    private final JwtService jwtService;
    private final UserDao userDao;
    private final LogoutDao logoutDao;



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 9. 상품 등록 API
     * [POST] /products/:userId
     * @return BaseResponse
     */
    @PostMapping(value = "/{userId}")      //, consumes = {"multipart/form-data"}
    public BasicResponse createProduct(@PathVariable("userId") Long userId,
                                       @Valid @RequestPart("postProductReq") PostProductReq postProductReq, BindingResult bindingResult,
                                       @RequestPart("imageFile") MultipartFile imageFile) {  //@RequestBody PostUserReq postUserReq


        try {
            /* Access Token을 통한 사용자 인가 적용 */
            Long userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

            if (userId != userIdByAccessToken) {  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
            }
            /* Access Token을 통한 사용자 인가 적용 끝 */
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }



        /* 로그아웃 상태 확인 구현 */
        Optional<User> user = userDao.findById(userId);  //외래키 활용을 위해 해당 User 객체를 불러옴.
        Optional<Logout> userLogout = logoutDao.checkLogout(user);  //외래키 활용을 위해 해당 User 객체를 불러옴.
        if (!userLogout.isPresent()) {  //로그아웃된 상태이면
            return new BasicResponse(PATCH_USERS_LOGOUT_USER); //"이미 로그아웃된 유저입니다."
        }
        /* 로그아웃 상태 확인 끝 */






        /* 유효성 검사 구현부 */
        if (bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.

            if (errorlist.get(0).getDefaultMessage().equals("배송타입 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_DeliveryType);
            } else if (errorlist.get(0).getDefaultMessage().equals("상품제목 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_Title);
            } else if (errorlist.get(0).getDefaultMessage().equals("상품가격 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_Price);
            } else if (errorlist.get(0).getDefaultMessage().equals("할인율 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_DiscountRate);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)")) {
                return new BasicResponse(POST_PRODUCTS_INVALID_saleCount);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("배송일을 입력해 주세요.")) {
                return new BasicResponse(POST_PRODUCTS_EMPTY_DeliveryDay);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("상품옵션 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_PRODUCT_OPTIONS_INVALID_optionName);
            }
        }
        if (imageFile.isEmpty()) {   //이미지가 비었다면
            return new BasicResponse(POST_PRODUCTS_EMPTY_IMAGE);
        }


        //이미지 파일의 확장자가 git, jpg, jpeg, png가 아니면 예외 발생
        String fileExt = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();

        if(! "gif".equals(fileExt) && ! "jpg".equals(fileExt) && ! "jpeg".equals(fileExt) && ! "png".equals(fileExt)){
            return new BasicResponse(POST_PRODUCTS_INVALID_FORM_IMAGE);

        }

        //이미지 파일 크기 제한 (10MB)
        if(imageFile.getSize() > 10485760){
            return new BasicResponse(POST_PRODUCTS_INVALID_SIZE_IMAGE);
        }
        /* 유효성 검사 구현 끝*/





        try {
            //DB에 상품 등록
            String responseMessage = productService.createProduct(postProductReq, userId, imageFile);

            return new BasicResponse(responseMessage);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }




    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        /**
         * 10. 상품 정보 수정 API
         * [PATCH] /products/:userId
         * @return BaseResponse
         */
        // Body
        @PatchMapping("/{userId}/{productId}") //
        public BasicResponse modifyProduct(@PathVariable("userId") Long userId  ,
                                           @PathVariable("productId") Long productId,
                                           @Valid @RequestPart PatchProductReq patchProductReq, BindingResult bindingResult,
                                           @RequestPart("imageFile") MultipartFile imageFile) {


            try{
                /* Access Token을 통한 사용자 인가 적용 */
                Long userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

                if(userId != userIdByAccessToken){  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                    return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
                }
                /* Access Token을 통한 사용자 인가 적용 끝 */
            } catch(BasicException exception){
                return new BasicResponse(exception.getStatus());
            }


            /* 로그아웃 상태 확인 구현 */
            Optional<User> user = userDao.findById(userId);  //외래키 활용을 위해 해당 User 객체를 불러옴.
            Optional<Logout> userLogout = logoutDao.checkLogout(user);  //외래키 활용을 위해 해당 User 객체를 불러옴.
            if(!userLogout.isPresent()){  //로그아웃된 상태이면
                return new BasicResponse(PATCH_USERS_LOGOUT_USER); //"이미 로그아웃된 유저입니다."
            }
            /* 로그아웃 상태 확인 끝 */





            /* 유효성 검사 구현부 */
            if(bindingResult.hasErrors()) {   //에러가 있다면
                List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.

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
                else if (errorlist.get(0).getDefaultMessage().equals("판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)")) {
                    return new BasicResponse(POST_PRODUCTS_INVALID_saleCount);
                }
                else if (errorlist.get(0).getDefaultMessage().equals("배송일을 입력해 주세요.")) {
                    return new BasicResponse(POST_PRODUCTS_EMPTY_DeliveryDay);
                }
                else if (errorlist.get(0).getDefaultMessage().equals("상품옵션 형식을 확인해 주세요.")) {
                    return new BasicResponse(POST_PRODUCT_OPTIONS_INVALID_optionName);
                }

            }
            if (imageFile.isEmpty()) {   //이미지가 비었다면
                return new BasicResponse(POST_PRODUCTS_EMPTY_IMAGE);
            }

            //이미지 파일의 확장자가 git, jpg, jpeg, png가 아니면 예외 발생
            String fileExt = imageFile.getOriginalFilename().substring(imageFile.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();

            if(! "gif".equals(fileExt) && ! "jpg".equals(fileExt) && ! "jpeg".equals(fileExt) && ! "png".equals(fileExt)){
                return new BasicResponse(POST_PRODUCTS_INVALID_FORM_IMAGE);

            }

            //이미지 파일 크기 제한 (10MB)
            if(imageFile.getSize() > 10485760){
                return new BasicResponse(POST_PRODUCTS_INVALID_SIZE_IMAGE);
            }

            /* 유효성 검사 구현 끝*/





            try{
                //DB에 상품 정보 수정
                String responseMessage = productService.modifyProduct(patchProductReq, productId, imageFile);

                return new BasicResponse(responseMessage);
            } catch(BasicException exception){
                return new BasicResponse(exception.getStatus());
            }



        }




////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 11. 전체 상품 조회 API
     * [GET] /products
     * @return BaseResponse
     */

    @GetMapping("")
    public BasicResponse getProducts(){


        try {

            //전체 상품 조회
            List<GetProductsRes> getProductsRes = productService.getProducts();  //userService.java로 patchUserReq객체 값 전송

            return new BasicResponse(getProductsRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }







////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 12. 특정 상품 조회 API
     * [GET] /products/:userId/:productId
     * @return BaseResponse
     */

    @GetMapping("/{productId}")
    public BasicResponse getProduct(@PathVariable("productId") Long productId){

        try {
            //특정 상품 조회
            GetProductAndUserRes getProductAndUserRes = productService.getProduct(productId);  //userService.java로 patchUserReq객체 값 전송


            return new BasicResponse(getProductAndUserRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }

    }





////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 13. 상품 삭제 API
     * [PATCH] /products/:userId/:productId/status
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userId}/{productId}/status")
    public BasicResponse deleteProduct(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId ) {


        try {

            /* Access Token을 통한 사용자 인가 적용 */
            Long userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

            if(userId != userIdByAccessToken){  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
            }
            /* Access Token을 통한 사용자 인가 적용 끝 */

            /* 로그아웃 상태 확인 구현 */
            Optional<User> user = userDao.findById(userId);  //외래키 활용을 위해 해당 User 객체를 불러옴.
            Optional<Logout> userLogout = logoutDao.checkLogout(user);  //외래키 활용을 위해 해당 User 객체를 불러옴.
            if(!userLogout.isPresent()){  //로그아웃된 상태이면
                return new BasicResponse(PATCH_USERS_LOGOUT_USER); //"이미 로그아웃된 유저입니다."
            }
            /* 로그아웃 상태 확인 끝 */



            //상품 상태 비활성화
            productService.deleteProduct(productId);

            String result = "상품 정보가 삭제되었습니다.";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }







////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 14. 상품 옵션 조회 API
     * [GET] /products/:productId/option
     * @return BaseResponse<String>
     */

    @GetMapping("/{productId}/option")
    public BasicResponse getProductOptions(@PathVariable("productId") Long productId ) {


        try {

            //상품 옵션 불러오기
            List<GetProductOptionRes> getProductOptionRes = productService.getProductOptions(productId);

            return new BasicResponse(getProductOptionRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }


















}
