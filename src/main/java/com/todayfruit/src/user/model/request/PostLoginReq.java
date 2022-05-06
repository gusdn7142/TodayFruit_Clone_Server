package com.todayfruit.src.user.model.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor

public class PostLoginReq {

    //@Email(message="이메일 형식을 확인해 주세요.")
    @NotBlank(message="이메일 형식을 확인해 주세요.")
    @Pattern(regexp = "^[A-Za-z0-9_\\.\\-]+@[A-Za-z0-9\\-]+\\.[A-Za-z0-9\\-]+$", message="이메일 형식을 확인해 주세요.")
    private String email;

    @NotBlank(message="비밀번호를 입력해주세요.")
    //@Pattern(regexp = "^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\\\(\\)\\-_=+]).{8,16}$", message="비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)")
    private String password;

}
