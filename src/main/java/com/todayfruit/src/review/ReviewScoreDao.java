package com.todayfruit.src.review;


import com.todayfruit.src.review.model.domain.ReviewScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewScoreDao extends JpaRepository<ReviewScore, Long> {


    /* 리뷰 점수를 별 이미지로 변환  (20. 상품리뷰 작성 API) */
    @Query(value="select rc.star from ReviewScore rc where rc.score = :score ")
    String convertScore(@Param("score") int score);


}
