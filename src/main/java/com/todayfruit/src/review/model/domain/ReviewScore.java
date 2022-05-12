package com.todayfruit.src.review.model.domain;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;

@Getter
@Setter  //Setter를 써야할까??
@Entity                 //JPA를 사용해서 테이블과 매핑할 클래스 선언
@DynamicInsert          //jpa 함수(ex, save)로 insert시 null인 field는 제외
@DynamicUpdate          //jpa 함수(ex)로 update시 null인 field는 제외
@Table(name = "review_score")   //엔티티와 매핑할 테이블을 지정
public class ReviewScore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //DDL문이 생성될때 적용되는 것이다!!
    private Long id;  //리뷰 점수 인덱스

    @Column(nullable=false)
    private int score;  //리뷰 점수

    @Column(nullable=false, columnDefinition="TEXT")  //NULL 허용여부와 타입 세부 지정
    private String star;  //별 이미지



}
