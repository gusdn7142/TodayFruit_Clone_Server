package com.todayfruit.user;


import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Generated;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@Setter  //Setter를 써야할까??
@Entity                 //JPA를 사용해서 테이블과 매핑할 클래스 선언
@Table(name = "user")   //엔티티와 매핑할 테이블을 지정
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;  //인덱스

    @Column (nullable=false, columnDefinition="varchar(30)")  //NULL 허용여부와 타입 세부 지정
    private String nickName;  //닉네임

    @Column (nullable=false, columnDefinition ="TEXT")
    private String image;   //이미지

    @Column (nullable=true,columnDefinition ="varchar(60)")
    private String email;  //이메일

    @Column (nullable=false, columnDefinition ="varchar(100)")
    private String introduction; //소개글

    @Column (nullable=false, columnDefinition ="varchar(200)")
    private String password;  //패스워드 (해쉬함수로 넣을 예정)


    @Column (columnDefinition = "varchar(10) default 'ACTIVE'")
    @Enumerated(EnumType.STRING)
    private UserStatus status; //데이터 상태 (INACTIVE가 0, ACTIVE가 1)

    @Column (nullable=false,  columnDefinition = "timestamp default CURRENT_TIMESTAMP")
    private LocalDateTime createAt;  //생성 시각

    @Column (nullable=false, columnDefinition = "timestamp default CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    private LocalDateTime updateAt; //갱신 시각




}
