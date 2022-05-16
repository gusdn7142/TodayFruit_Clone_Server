package com.todayfruit.src.purchase;

import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.purchase.model.domain.Purchase;
import com.todayfruit.src.purchase.model.response.GetPurchaseRes;
import com.todayfruit.src.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PurchaseDao extends JpaRepository<Purchase, Long> {


    /* 18. 주문한 상품 조회 API  */
    @Query(value="SELECT new com.todayfruit.src.purchase.model.response.GetPurchaseRes( " +
            "pc.id," +
            "p.title, " +
            "p.image, " +
            "po.optionName, " +
            "pc.purchaseCount, " +
            "concat(pc.purchaseCount * SUBSTRING_INDEX(p.discountPrice,'원',1),'원') as totalPrice, " +
            "pc.shippingAddress, " +
            "case when p.deliveryType='무료배송' then '0원'\n " +
            "when p.deliveryType='쿠배송' then '3000원'\n " +
            "END as deliveryPrice) " +
            "FROM Purchase pc " +
            "left join Product p " +
            "on pc.product = p " +
            "join ProductOption po " +
            "on pc.productOption = po " +
            "where pc.user = :userId and pc.status = 'ACTIVE' ")
    List<GetPurchaseRes> getPurchaseInfo(@Param("userId") User user );


    /*  주문상품 객체 불러오기   (19. 상품 주문 취소 API )  */
    @Query(value="SELECT p FROM Purchase p where p.id =:purchaseId and p.status = 'ACTIVE'")
    Purchase checkStatusPurchase(@Param("purchaseId") Long purchaseId );


    /* 19. 주문한 상품 정보 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Purchase p set p.status = 'INACTIVE' where p.id = :purchaseId and p.status = 'ACTIVE'")
    void deletePurchaseInfo(@Param("purchaseId") Long purchaseId);




    /* 상품을 구매한 유저인지 검증 -  checkPurchaser()  (20. 상품 리뷰 작성 API) */
    @Query(value="SELECT p FROM Purchase p where p.product =:productId and p.user = :userId and p.status = 'ACTIVE'")
    Purchase checkPurchaser(@Param("productId") Product product, @Param("userId") User user );













}
