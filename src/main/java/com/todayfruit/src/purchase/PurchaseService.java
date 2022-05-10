package com.todayfruit.src.purchase;


import com.todayfruit.config.BasicException;
import com.todayfruit.src.product.ProductDao;
import com.todayfruit.src.product.ProductOptionDao;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.product.model.request.PostProductReq;
import com.todayfruit.src.purchase.model.domain.Purchase;
import com.todayfruit.src.purchase.model.request.PostPurchaseReq;
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
            if(productDao.checkStatusPrdouct(productId) == null){   //상품이 삭제되었다면..
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
            System.out.println(exception);
            throw new BasicException(DATABASE_ERROR_CREATE_PURCHASE);  //"상품 구매에 실패하였습니다."
        }
    }














}
