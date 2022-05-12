package com.todayfruit.src.review;


import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.review.model.domain.Review;
import com.todayfruit.src.review.model.response.GetReviewRes;
import com.todayfruit.src.user.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ReviewDao extends JpaRepository<Review, Long> {


    /*리뷰 객체 조회   (20. 리뷰 작성 API) */
    @Query(value="SELECT r FROM Review r where r.user =:userId and r.product =:productId and r.status = 'ACTIVE'")
    Review checkStatusReview(@Param("userId") User user, @Param("productId") Product product );


    /*21. 상품 리뷰 조회 API */
    @Query(value="SELECT new com.todayfruit.src.review.model.response.GetReviewRes( " +
            "r.id, " +
            "r.star, " +
            "u.name, " +
            "DATE_FORMAT(r.updateAt, '%y.%m.%d'), " +
            "r.image, " +
            "r.content) " +
            "FROM Review r left join r.product p " +
            "on r.product = p " +
            "join r.user u " +
            "on r.user = u " +
            "where r.product = :productId and r.status = 'ACTIVE'")
    List<GetReviewRes> getReviews(@Param("productId") Product product );




    //22. 리뷰 수정 API
    @Modifying
    @Transactional
    @Query(value="update Review r set r.score = :score, r.star = :star ,  r.image= :image, r.content= :content where r.id = :reviewId and r.status = 'ACTIVE'\n")
    void modifyReview(@Param("score") int score, @Param("star") String star, @Param("image") String image, @Param("content") String content, @Param("reviewId") Long reviewId);



    /*리뷰 작성자와 수정자 일치여부 확인   (22. 리뷰 수정 API) */
    @Query(value="SELECT r FROM Review r where r.id =:reviewId and r.user = :userId")
    Review checkReviewer(@Param("reviewId") Long reviewId, @Param("userId") User user);



    /*리뷰 객체 조회   (22. 상품 수정 API ~ 23. 리뷰 삭제 API) */
    @Query(value="SELECT r FROM Review r where r.id =:reviewid and r.status = 'ACTIVE'")
    Review checkStatusReview(@Param("reviewid") Long reviewid);


    /* 23. 리뷰 삭제 API */
    @Modifying
    @Transactional
    @Query(value="update Review r set r.status = 'INACTIVE' where r.id = :reviewId and r.status = 'ACTIVE' ")
    void deleteReview(@Param("reviewId") Long reviewId);





}
