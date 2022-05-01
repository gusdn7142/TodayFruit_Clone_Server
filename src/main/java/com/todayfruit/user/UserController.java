package com.todayfruit.user;


import com.sun.istack.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;


@RestController
@RequestMapping("users")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class UserController {

    private final UserService userService;




    /* 프로필 조회 API*/
//    @ResponseBody
    @GetMapping("")
    public ResponseEntity getUser() {     //@RequestParam(required = false) String isbn

         return ResponseEntity.ok(userService.getUser());  //readBooks() 함수에 인자를 넣지 않으므로 오류 발생시킴???

//        return "API 테스트 성공 (웹 페이지)";
    }



    /* (id로) 프로필 조회 API*/
//    @ResponseBody
    @GetMapping("/{userId}/profile")
    public ResponseEntity getUser(@PathVariable("userId" ) Long userId) {     //@RequestParam(required = false) String isbn

        return ResponseEntity.ok(userService.getUser(userId));  //readBooks() 함수에 인자를 넣지 않으므로 오류 발생시킴???
    }









}
