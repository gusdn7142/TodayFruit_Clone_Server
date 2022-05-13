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
    @Modifying
    @Transactional
    @Query(value="update Product set deliveryType = :deliveryType," +
            "title = :title, " +
            "image = :image, " +
            "price = :price, " +
            "discountRate = :discountRate, " +
            "saleCount = :saleCount, " +
            "description = :description, " +
            "deliveryDay = :deliveryDay, " +
            "discountPrice = :discountPrice " +
            " where id = :productId and status = 'ACTIVE'\n")
    void modifyProduct(@Param("deliveryType") String deliveryType,
                       @Param("title") String title,
                       @Param("image") String image,
                       @Param("price") String price,
                       @Param("discountRate") int discountRate,
                       @Param("saleCount") int saleCount,
                       @Param("description") String description,
                       @Param("deliveryDay") Date deliveryDay,
                       @Param("discountPrice") String discountPrice,
                       @Param("productId") Long productId
                       );

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
