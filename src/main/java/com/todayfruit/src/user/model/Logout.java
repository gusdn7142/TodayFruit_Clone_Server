package com.todayfruit.src.user.model;


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
@Table(name = "logout")   //엔티티와 매핑할 테이블을 지정
public class Logout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //DDL문이 생성될때 적용되는 것이다!!
    private Long id;  //인덱스

    @Column (nullable=false, columnDefinition="varchar(300)")  //NULL 허용여부와 타입 세부 지정
    private String refreshToken;  //리프레시 토큰


    /*사용자 인덱스는 일단 보류!! */
    //@Column
    //private Long userId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  //사용자 인덱스(외래키)


    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserStatus status; //데이터 상태 (INACTIVE가 0, ACTIVE가 1)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt; //갱신 시각




}
