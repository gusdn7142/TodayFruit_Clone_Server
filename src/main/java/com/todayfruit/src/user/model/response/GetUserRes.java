package com.todayfruit.src.user.model.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
public class GetUserRes {

    private Long id;
    private String name;
    private String image;
    private String nickName;
    private String introduction;
    private String email;


//    Long getId();
//    String getImage();
//    String getNickName();
//    String getIntroduction();
//    String getEmail();


//    public GetUserRes(String image){
//        this.id=id;
//        this.image=image;
//        this.nickName=nickName;
//        this.introduction=introduction;
//        this.email=email;
//    }

}
