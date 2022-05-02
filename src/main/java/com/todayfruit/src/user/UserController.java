package com.todayfruit.src.user;


import com.sun.istack.NotNull;
import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.src.user.model.GetUserRes;
import com.todayfruit.src.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;


@RestController   //Responsebody +controller!!
@RequestMapping("users")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class UserController {

    private final UserService userService;




    /* 프로필 조회 API*/
////    @ResponseBody
//    @GetMapping("")
//    public ResponseEntity getUser() {     //@RequestParam(required = false) String isbn
//
//        BasicResponse basicResponse = null;
//
//        if(userService.getUser() != null){
//            basicResponse = new BasicResponse(
//                    "true",
//                    "2000",
//                    "프로필 조회 성공",
//                    userService.getUser()
//            );
//
//        }
//
//        return ResponseEntity.ok(basicResponse);  //readBooks() 함수에 인자를 넣지 않으므로 오류 발생시킴???
//    }



//    /* 프로필 조회 API*/
////    @ResponseBody
//    @GetMapping("")
//    public ResponseEntity getUser() {     //@RequestParam(required = false) String isbn
//
//        return ResponseEntity.ok(userService.getUser());  //readBooks() 함수에 인자를 넣지 않으므로 오류 발생시킴???
//
////        return "API 테스트 성공 (웹 페이지)";
//    }





//    /* (id로) 프로필 조회 API*/
////    @ResponseBody
//    @GetMapping("/{userId}/profile")
//    public ResponseEntity getUser( @PathVariable("userId" ) Long userId) {     //@RequestParam(required = false) String isbn
//
//        return ResponseEntity.ok(userService.getUser(userId));  //readBooks() 함수에 인자를 넣지 않으므로 오류 발생시킴???



    //        } catch(BasicException exception){
//            return new BasicResponse((exception.getStatus()));  //service에서 받아온 catch문을 여기서 던진다.
//        }
//    }





    /**
     * 3. 프로필 조회 API
     * [POST] /users
     * @return getUserRes
     */
    @GetMapping("/{userId}/profile")
    public BasicResponse getUser( @PathVariable("userId" ) Long userId) {     //@RequestParam(required = false) String isbn

        try { //프로필 조회
            GetUserRes getUserRes = userService.getUser(userId);
            return new BasicResponse(getUserRes);

        } catch(BasicException exception){  //service 단계에서의 오류가 발생하면 catch해서 출력해준다.
            return new BasicResponse((exception.getStatus()));
        }

    }







}
