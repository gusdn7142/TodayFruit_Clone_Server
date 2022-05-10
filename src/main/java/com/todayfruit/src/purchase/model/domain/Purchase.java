package com.todayfruit.src.purchase.model.domain;


import com.todayfruit.src.product.model.domain.ProductOption;
import com.todayfruit.src.purchase.model.PurchaseStatus;
import com.todayfruit.src.product.model.domain.Product;
import com.todayfruit.src.user.model.domain.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter  //Setter를 써야할까??
@Entity                 //JPA를 사용해서 테이블과 매핑할 클래스 선언
@DynamicInsert          //jpa 함수(ex, save)로 insert시 null인 field는 제외
@DynamicUpdate          //jpa 함수(ex)로 update시 null인 field는 제외
@Table(name = "purchase")   //엔티티와 매핑할 테이블을 지정
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //DDL문이 생성될때 적용되는 것이다!!
    private Long id;  //상품 주문 인덱스

    @Column(nullable=false)  //NULL 허용여부와 타입 세부 지정
    private int purchaseCount;  //상품 구매 개수

    @Column(nullable=false, columnDefinition="varchar(100)")  //NULL 허용여부와 타입 세부 지정
    private String shippingAddress;  //배송지


    /*상품 인덱스 */
    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용한다.)
    @JoinColumn(name = "product_id")
    private Product product;  //상품 인덱스.


    /*상품 인덱스 */
    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용한다.)
    @JoinColumn(name = "product_option_id")
    private ProductOption productOption;  //상품 옵션 인덱스.


    /*사용자 인덱스 */
    @ManyToOne(fetch = FetchType.LAZY)  //실무에서는 n+1 쿼리조회 문제 때문에 LAZY(지연 로딩만 사용한다.)
    @JoinColumn(name = "user_id")
    private User user;     //주문자 인덱스(외래키)




    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private PurchaseStatus status;    //데이터 상태 (INACTIVE가 0, ACTIVE가 1)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt; //갱신 시각




}
