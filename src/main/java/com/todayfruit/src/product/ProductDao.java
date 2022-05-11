package com.todayfruit.src.product;



import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.product.model.response.GetProductRes;
import com.todayfruit.src.product.model.response.GetProductsRes;
import com.todayfruit.src.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


public interface ProductDao extends JpaRepository<Product, Long> {








    /* 10. 상품 정보 수정 API */
    //배송타입 수정
    @Modifying
    @Transactional
    @Query(value="update Product set deliveryType = :deliveryType where id = :productId and status = 'ACTIVE'\n")
    void modifyDeliveryType(@Param("deliveryType") String deliveryType, @Param("productId") Long productId );

    //상품 제목 수정
    @Modifying
    @Transactional
    @Query(value="update Product set title = :title where id = :productId and status = 'ACTIVE'\n")
    void modifyTitle(@Param("title") String title, @Param("productId") Long productId );

    //상품 이미지 수정
    @Modifying
    @Transactional
    @Query(value="update Product set image = :image where id = :productId and status = 'ACTIVE'\n")
    void modifyImage(@Param("image") String image, @Param("productId") Long productId );

    //상품 가격 수정
    @Modifying
    @Transactional
    @Query(value="update Product set price = :price where id = :productId and status = 'ACTIVE'\n")
    void modifyPrice(@Param("price") String price, @Param("productId") Long productId );


    //상품 할인율 수정
    @Modifying
    @Transactional
    @Query(value="update Product set discountRate = :discountRate where id = :productId and status = 'ACTIVE'\n")
    void modifyDiscountRate(@Param("discountRate") int discountRate, @Param("productId") Long productId );


    //상품 판매수량 수정
    @Modifying
    @Transactional
    @Query(value="update Product set saleCount = :saleCount where id = :productId and status = 'ACTIVE'\n")
    void modifySaleCount(@Param("saleCount") int saleCount, @Param("productId") Long productId );


    //상품 설명 수정
    @Modifying
    @Transactional
    @Query(value="update Product set description = :description where id = :productId and status = 'ACTIVE'\n")
    void modifyDescription(@Param("description") String description, @Param("productId") Long productId );


    //상품 배송일 수정
    @Modifying
    @Transactional
    @Query(value="update Product set deliveryDay = :deliveryDay where id = :productId and status = 'ACTIVE'\n")
    void modifyDeliveryDay(@Param("deliveryDay") Date deliveryType, @Param("productId") Long productId );

    //상품 할인가격 변경
    @Modifying
    @Transactional
    @Query(value="update Product set discountPrice = :discountPrice where id = :productId and status = 'ACTIVE'\n")
    void modifyDiscountPrice(@Param("discountPrice") String discountPrice, @Param("productId") Long productId );


    /* 10. 상품 정보 수정 API 끝 */





    /* 11. 전체 상품 조회 API */
    @Query(value="select new com.todayfruit.src.product.model.response.GetProductsRes(" +
            "p.id,\n" +
            "p.deliveryType,\n" +
            "p.title,\n" +
            "p.image, \n" +
            "p.price,\n" +
            "p.discountRate," +
            "p.discountPrice," +
            "p.saleCount)" +
            "from Product p where p.status = 'ACTIVE'")
    List<GetProductsRes> getProducts();



    /* 12. 상품 인덱스를 통한 유저 객체 조회 - 특정 상품 조회 API에서 활용 */
    @Query(value="select p.user from Product p where p.id = :productId ")
    User getUserByProductId(@Param("productId") Long productId);



    /* 12. 특정 상품 조회 API */
    @Query(value="select new com.todayfruit.src.product.model.response.GetProductRes(" +
            "p.id,\n" +
            "p.deliveryType,\n" +
            "p.title,\n" +
            "p.image,\n" +
            "p.price,\n" +
            "p.discountRate," +
            "p.discountPrice," +
            "p.saleCount," +
            "p.description)" +
            "from Product p where p.id = :productId and p.status = 'ACTIVE'")
    GetProductRes getProduct(@Param("productId") Long productId);



    /* 13. 상품 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Product set status = 'INACTIVE' where id = :productId and status = 'ACTIVE' ")
    void deleteProduct(@Param("productId") Long productId);




    /* 14. 상품 객체 불러오기   (상품 옵션 불러오기 API )  */
    @Query(value="SELECT p FROM Product p where p.id =:productId and p.status = 'ACTIVE'")
    Product checkStatusPrdouct(@Param("productId") Long productId );











}
