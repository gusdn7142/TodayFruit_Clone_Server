package com.todayfruit.src.user.model.domain;


import com.todayfruit.src.user.model.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter  //Setter를 써야할까??

@Entity                 //JPA를 사용해서 테이블과 매핑할 클래스 선언
@DynamicInsert          //jpa 함수(ex, save)로 insert시 null인 field는 제외
@DynamicUpdate          //jpa 함수(ex)로 update시 null인 field는 제외
@Table(name = "user")   //엔티티와 매핑할 테이블을 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   //DDL문이 생성될때 적용되는 것이다!!
    private Long id;  //인덱스

    @Column (nullable=false, columnDefinition="varchar(30)")  //NULL 허용여부와 타입 세부 지정
    private String name;  //사용자 이름

    @Column (nullable=false, columnDefinition="varchar(30)")  //NULL 허용여부와 타입 세부 지정
    private String phone;  //전화번호

    @Column (nullable=false, columnDefinition="varchar(30)")  //NULL 허용여부와 타입 세부 지정
    private String nickName;  //닉네임

    @Column (nullable=true, columnDefinition ="TEXT")
    private String image;   //이미지

    @Column (nullable=false,columnDefinition ="varchar(60)")
    private String email;  //이메일

    @Column (nullable=true, columnDefinition ="varchar(100)")
    private String introduction; //소개글

    @Column (nullable=false, columnDefinition ="varchar(200)")
    private String password;  //패스워드 (해쉬함수로 넣을 예정)


    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserStatus status; //데이터 상태 (INACTIVE가 0, ACTIVE가 1)

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createAt;  //생성 시각

    @Column (columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt; //갱신 시각


//    //영속성 전이(삭제)를 위해 선언
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE)  //orphanRemoval=true
//    private List<Logout> logout = new ArrayList<Logout>();



}
