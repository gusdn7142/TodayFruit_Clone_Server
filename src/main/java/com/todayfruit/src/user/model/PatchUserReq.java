package com.todayfruit.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;



@Getter
@Setter
@AllArgsConstructor

public class PatchUserReq {

    private Long userId;

    @Pattern(regexp = "^[가-힣]{2,4}$", message="이름 형식을 확인해 주세요. (한글 이름 2~4자 이내)")  //한글 이름 2~4자 이내
    private String name;   //이름도 무조건 입력 해야함

    @Pattern(regexp = "^[0-9a-zA-Z가-힣]{2,20}$", message="닉네임 형식을 확인해 주세요.")  //글자수만 제한 2~20
    private String nickName;   //닉네임은 무조건 입력 해야함.

    private String introduction;  //소개글을 입력 안할수도 있음

    //@NotNull(message="이미지를 추가해주세요.")
    private String image;  //이미지는 무조건 값이 있게 오게 되어 있습니다.


}
