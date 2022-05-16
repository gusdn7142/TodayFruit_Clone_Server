package com.todayfruit.src.purchase;


import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.ProductDao;
import com.todayfruit.src.product.ProductOptionDao;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.purchase.model.domain.Purchase;
import com.todayfruit.src.purchase.model.request.PostPurchaseReq;
import com.todayfruit.src.purchase.model.response.GetPurchaseRes;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.todayfruit.config.BasicResponseStatus.*;

@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class PurchaseService {


    private final PurchaseDao purchaseDao;
    private final UserDao userDao;
    private final ProductDao productDao;
    private final ProductOptionDao productOptionDao;


///////////////////////////////////////////////////////////////////////////////////////////////////
    /* 17. 상품 구매 API-  createPurchase() */
    public String createPurchase(Long userId, Long productId, Long productOptionId, PostPurchaseReq postPurchaseReq) throws BasicException {


            //사용자 id를 통해 사용자 객체 불러오기
            User purchaseUser = userDao.checkStatusUser(userId);

            //회원 삭제 여부 확인
            if(purchaseUser == null){
                throw new BasicException(PATCH_USERS_DELETE_USER);  //"이미 탈퇴된 계정입니다."
            }


            //상품 id를 통해 상품 객체 불러오기
            Product purchaseProduct = productDao.checkStatusPrdouct(productId);

            //상품 삭제여부 확인
            if(purchaseProduct == null){   //상품이 삭제되었다면..
                throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
            }


            //상품옵션 id를 통해 상품 옵션 객체 불러오기
            ProductOption purchaseProductOption = productOptionDao.checkStatusPrdocutOption(productOptionId);

            //상품 옵션 삭제여부 확인
            if(purchaseProductOption==null){   //상품 옵션이 존재하지 않다면
                throw new BasicException(GET_PRODUCTS_NOT_EXISTS_PRDOCUT_OPTION);  //"상품 옵션을 찾을 수 없습니다."
            }



        try{
            //DTO에 사용자, 상품, 상품 옵션 객체 담기
            postPurchaseReq.setUser(purchaseUser);
            postPurchaseReq.setProduct(purchaseProduct);
            postPurchaseReq.setProductOption(purchaseProductOption);


            //복사할 상품구매 객체 생성 후 복사
            Purchase Purchasecreate = new Purchase();
            BeanUtils.copyProperties(postPurchaseReq,Purchasecreate);

            //상품구매 객체 DB에 등록
            purchaseDao.save(Purchasecreate);   //"Purchase" DB에 정보 저장


            return "상품 구매 등록을 완료했습니다..";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_PURCHASE);  //"상품 구매에 실패하였습니다."
        }
    }







///////////////////////////////////////////////////////////////////////////////////////////////////
    /* 18. 상품 주문 조회 API-  createPurchase() */
    public List<GetPurchaseRes> getPurchaseInfo(Long userId) throws BasicException {


        //사용자 id를 통해 사용자 객체 불러오기
        User purchaseUser = userDao.checkStatusUser(userId);

        //회원 삭제 여부 확인
        if(purchaseUser == null){
            throw new BasicException(PATCH_USERS_DELETE_USER);  //"이미 탈퇴된 계정입니다."
        }


        try{
            //사용자 id를 통해 상품 주문 정보 조회
            List<GetPurchaseRes> productPurchase = purchaseDao.getPurchaseInfo(purchaseUser);


            return productPurchase;

        } catch (Exception exception) {
            System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_GET_FAIL_PURCHASE);  //"상품 주문 정보 조회에 실패하였습니다."
        }

    }


////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 19. 상품 주문 취소 API - deletePurchaseInfo()   */
    public void deletePurchaseInfo(Long purchaseId) throws BasicException {


        //주문정보 삭제 여부 조회 (유저가 계속 클릭시..)
        if(purchaseDao.checkStatusPurchase(purchaseId) == null){   //주문정보가 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PURCHASE);  //"삭제된 주문 정보입니다."
        }


        try{
            //상품 주문 취소
            purchaseDao.deletePurchaseInfo(purchaseId);

        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_PURCHASE);   //'해당 주문 상품정보 취소에 실패하였습니다.'
        }

    }






////////////////////////////////////////////////////////////////////////////////////////
    /* 상품을 구매한 유저인지 검증 -  checkPurchaser()  (20. 상품 리뷰 작성 API) */
    public void checkPurchaser(Long userId, Long productId) throws BasicException {


        //사용자 인덱스를 통해 사용자 객체를 불러옴
        User purchaseuser = userDao.checkStatusUser(userId);

        //회원 탈퇴 여부 확인 (유저가 계속 클릭시..)
        if(purchaseuser == null){
            throw new BasicException(PATCH_USERS_DELETE_USER);  //"회원 탈퇴된 계정입니다."
        }


        //상품 id를 통해 상품 객체 불러오기
        Product purchaseProduct = productDao.checkStatusPrdouct(productId);

        //상품 삭제여부 확인
        if(purchaseProduct == null){   //상품이 삭제되었다면..
            throw new BasicException(PATCH_PRODUCTS_DELETE_PRDOCUT);  //"삭제된 상품 입니다."
        }



        //상품을 구매한 유저인지 확인
        if(purchaseDao.checkPurchaser(purchaseProduct, purchaseuser) == null){
            throw new BasicException(POST_REIVEWS_NOT_PURCHASER);  //"상품을 구매한 유저가 아닙니다."
        }


    }





}
