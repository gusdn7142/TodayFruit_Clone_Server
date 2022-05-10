package com.todayfruit.src.purchase;


import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.src.product.ProductService;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.purchase.model.request.PostPurchaseReq;
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
@RequestMapping("purchases")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class PurchaseController {

    private final PurchaseService purchaseService;
    private final JwtService jwtService;
    private final UserDao userDao;
    private final LogoutDao logoutDao;


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 17. 상품 구매 API
     * [POST] /purchases/:userId/products/:productId/:productOptionId
     * @return BaseResponse
     */
    // Body
    @PostMapping("/{userId}/products/{productId}/{productOptionId}") //
    public BasicResponse createPurchase(@PathVariable("userId") Long userId, @PathVariable("productId") Long productId, @PathVariable("productOptionId") Long productOptionId, @Valid @RequestBody PostPurchaseReq postPurchaseReq, BindingResult bindingResult   ) {

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

            if (errorlist.get(0).getDefaultMessage().equals("구매 수량은 1~100개 까지만 입력 가능합니다.")) {
                return new BasicResponse(POST_PURCHASE_INVALID_PurchaseCount);
            }
        }
        /* 유효성 검사 구현 끝*/


        try {
            //상품 구매
            String responseMessage = purchaseService.createPurchase(userId, productId, productOptionId, postPurchaseReq );

            return new BasicResponse(responseMessage);
        } catch (BasicException exception) {
            return new BasicResponse(exception.getStatus());
        }

    }






}
