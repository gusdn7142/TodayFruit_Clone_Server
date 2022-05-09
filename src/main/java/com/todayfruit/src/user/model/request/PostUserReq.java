package com.todayfruit.src.user.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.*;


@Getter
@Setter
@AllArgsConstructor

public class PostUserReq {


    //@Email(message="이메일 형식을 확인해 주세요.")
    @NotBlank(message="이메일 형식을 확인해 주세요.")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message="이메일 형식을 확인해 주세요.")
    private String email;

    @NotBlank(message="비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)")
    @Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\)\\-_=+]).{8,16}$", message="비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)")
    private String password;

    //@NotBlank(message="이름을 입력해주세요.")
    @Pattern(regexp = "^[가-힣]{2,4}$", message="이름 형식을 확인해 주세요. (한글 이름 2~4자 이내)")  //한글 이름 2~4자 이내
    private String name;   //이름도 무조건 입력 해야함

    @NotBlank(message="핸드폰 번호를 형식에 맞게 입력해주세요.")
    @Pattern(regexp = "^(010|011)\\d{4}\\d{4}$", message="핸드폰 번호를 형식에 맞게 입력해주세요.")
    private String phone;

    @Pattern(regexp = "^[0-9a-zA-Z가-힣\\s]{2,15}$", message="닉네임 형식을 확인해 주세요.")  //글자수만 제한 2~20
    private String nickName;   //닉네임은 무조건 입력 해야함.


}
