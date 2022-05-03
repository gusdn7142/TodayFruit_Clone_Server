package com.todayfruit.src.user.model;


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

    @NotBlank(message="이름을 입력해주세요.")
    private String name;

    @NotBlank(message="핸드폰 번호를 형식에 맞게 입력해주세요.")
    @Pattern(regexp = "^(010|011)\\d{4}\\d{4}$", message="핸드폰 번호를 형식에 맞게 입력해주세요.")
    private String phone;


    @NotBlank(message="닉네임을 입력해주세요.")
    private String nickName;

}
