package com.todayfruit.src.review;


import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.src.product.ProductService;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.request.PatchProductReq;
import com.todayfruit.src.purchase.PurchaseService;
import com.todayfruit.src.review.model.request.PatchReviewReq;
import com.todayfruit.src.review.model.request.PostReviewReq;
import com.todayfruit.src.review.model.response.GetReviewRes;
import com.todayfruit.src.user.LogoutDao;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.Logout;
import com.todayfruit.src.user.model.domain.User;
import com.todayfruit.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.todayfruit.config.BasicResponseStatus.*;

@RestController  //Responsebody +controller!!
@RequestMapping("reviews")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class ReviewController {

    private final ReviewService reviewService;
    private final JwtService jwtService;
    private final UserDao userDao;
    private final LogoutDao logoutDao;
    private final PurchaseService purchaseService;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 20. 상품 리뷰 작성 API
     * [POST] /products/:userId
     * @return BaseResponse
     */
    // Body
    @PostMapping("/{userId}/{productId}") //
    public BasicResponse createReview(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId , @Valid @RequestBody PostReviewReq postReviewReq, BindingResult bindingResult ) {  //@RequestBody PostUserReq postUserReq

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



        try {
            /* 상품을 구매한 유저인지 검증 */
            purchaseService.checkPurchaser(userId, productId);
            /* 상품을 구매한 유저인지 검증 끝 */
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }




        /* 유효성 검사 구현부 */
        if (bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.
            System.out.println(bindingResult.getAllErrors());
            if (errorlist.get(0).getDefaultMessage().equals("한글은 최대 100자 까지만 입력 가능합니다.")) {
                return new BasicResponse(POST_REVIEWS_INVALID_CONTENT);
            } else if (errorlist.get(0).getDefaultMessage().equals("1~5점 사이로 입력해 주세요.")) {
                return new BasicResponse(POST_REVIEWS_INVALID_SCORE);
            }
            /* 유효성 검사 구현 끝*/
        }


        try {
            //DB에 리뷰 등록
            String responseMessage = reviewService.createReview(postReviewReq, userId, productId  );

            return new BasicResponse(responseMessage);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }

    }



//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 21. 상품 리뷰 조회 API
     * [GET] /products
     * @return BaseResponse
     */

    @GetMapping("/{productId}")
    public BasicResponse getReviews(@PathVariable("productId") Long productId){


        try {

            //전체 상품 조회
            List<GetReviewRes> getReviewRes = reviewService.getReviews(productId);

            return new BasicResponse(getReviewRes);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }


    }




/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 22. 상품 리뷰 수정 API
     * [PATCH] /reviews/:userId/:reviewId
     * @return BaseResponse
     */
    // Body
    @PatchMapping("/{userId}/{reviewId}") //
    public BasicResponse modifyReview(@PathVariable("userId") Long userId  , @PathVariable("reviewId") Long reviewId, @Valid @RequestBody PatchReviewReq patchReviewReq, BindingResult bindingResult ) { //

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



        try {
            /*리뷰 수정자(Reviewer) 검증  */
            reviewService.checkReviewer(userId, reviewId);
            /*리뷰 수정자(Reviewer) 검증  끝 */
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }




        /* 유효성 검사 구현부 */
        if (bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.
            System.out.println(bindingResult.getAllErrors());
            if (errorlist.get(0).getDefaultMessage().equals("한글은 최대 100자 까지만 입력 가능합니다.")) {
                return new BasicResponse(POST_REVIEWS_INVALID_CONTENT);
            } else if (errorlist.get(0).getDefaultMessage().equals("1~5점 사이로 입력해 주세요.")) {
                return new BasicResponse(POST_REVIEWS_INVALID_SCORE);
            }
            /* 유효성 검사 구현 끝*/
        }





        try{
            //상품 리뷰 수정
            String responseMessage = reviewService.modifyReview(patchReviewReq, userId, reviewId);

            return new BasicResponse(responseMessage);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }


    }







////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 23. 상품 리뷰 삭제 API
     * [PATCH] /reviews/:userId/:reviewId/status
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userId}/{reviewId}/status")
    public BasicResponse deleteReview(@PathVariable("userId") Long userId, @PathVariable("reviewId") Long reviewId ) {


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


            try {
                /*리뷰 삭제자(Reviewer) 검증  */
                reviewService.checkReviewer(userId, reviewId);
                /*리뷰 삭제자(Reviewer) 검증  끝 */
            } catch(BasicException exception){
                return new BasicResponse(exception.getStatus());
            }



            //리뷰 상태 비활성화
            reviewService.deleteReview(reviewId);

            String result = "상품 리뷰가 삭제되었습니다.";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }








}
