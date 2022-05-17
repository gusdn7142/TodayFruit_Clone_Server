package com.todayfruit.src.product;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.product.model.response.GetProductOptionRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProductOptionDao extends JpaRepository<ProductOption, Long> {



    /* 10. 상품 정보 수정 API */
    //상품 옵션 변경
    @Modifying
    @Transactional
    @Query(value="update ProductOption set optionName = :optionName where id = :productOptionId and product = :productId and status = 'ACTIVE'\n")
    int modifyOptionName(@Param("optionName") String optionName, @Param("productOptionId") Long productOptionId , @Param("productId") Product product );
    /* 10. 상품 정보 수정 API */



    /* 13. 상품 옵션 정보 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update ProductOption set status = 'INACTIVE' where product = :productId and status = 'ACTIVE' ")
    void deleteProductOption(@Param("productId") Product product);



    /* 14. 상품 옵션 조회 API */
    @Query(value=" SELECT new com.todayfruit.src.product.model.response.GetProductOptionRes(p.id , p.optionName) FROM ProductOption p where p.product =:productId and p.status = 'ACTIVE'")
    List<GetProductOptionRes> getProductOptions(@Param("productId") Product product );


    /* 상품옵션 객체 조회- (17. 상품 구매 API 에서 활용) */
    @Query(value=" SELECT p FROM ProductOption p where p.id =:productOptionId and p.status = 'ACTIVE'")
    ProductOption checkStatusPrdocutOption(@Param("productOptionId") Long productOptionId);






}
