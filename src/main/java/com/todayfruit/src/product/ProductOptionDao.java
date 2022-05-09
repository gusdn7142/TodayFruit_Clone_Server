package com.todayfruit.src.product;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.domain.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOptionDao extends JpaRepository<ProductOption, Long> {



    /* 10. 상품 정보 수정 API */
    //상품 옵션 변경
    @Modifying
    @Transactional
    @Query(value="update ProductOption set optionName = :optionName where id = :productOptionId and product = :productId and status = 'ACTIVE'\n")
    void modifyOptionName(@Param("optionName") String optionName, @Param("productOptionId") Long productOptionId , @Param("productId") Product product );

    /* 10. 상품 정보 수정 API */


}
