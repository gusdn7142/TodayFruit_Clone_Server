package com.todayfruit.src.user;


import com.sun.istack.NotNull;
import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;

import com.todayfruit.src.user.model.*;
import com.todayfruit.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Array;
import java.util.*;

import static com.todayfruit.config.BasicResponseStatus.*;


@RestController   //Responsebody +controller!!
@RequestMapping("users")
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;



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








//        String validErrorCode = null;
//        String validErrorMessage = null;



//            System.out.println(errorlist.get(2).getDefaultMessage());

//            System.out.println(errorlist.get(0).getDefaultMessage());

//            for(ObjectError e : list) {   //에러를 한줄 한줄 뽑아온다.
////                System.out.println(e.getDefaultMessage());
////                System.out.println(e.getCode());
//                validErrorCode = e.getCode();
//                validErrorMessage = e.getDefaultMessage();
//
//            }

//            return new BasicResponse(POST_USERS_EMPTY_PHONE);
//            return new BasicResponse(formalError, errorMessage );
//            System.out.println(bindingResult.getAllErrors());
//            return new BasicResponse(bindingResult.getAllErrors());
//        }



//        System.out.println("name:"+postUserReq.getName());
//        System.out.println("pass:"+postUserReq.getPassword());
//        System.out.println("error:"+bindingResult.hasErrors());





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 1. 회원가입 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
//    @ResponseBody
    @PostMapping("")
    public BasicResponse createUser(@Valid @RequestBody PostUserReq postUserReq, BindingResult bindingResult) {  //@RequestBody PostUserReq postUserReq

        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.

            System.out.println(bindingResult.getAllErrors());
            if (errorlist.get(0).getDefaultMessage().equals("이메일 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_EMAIL);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("비밀번호에 형식을 다시 확인해 주세요. (숫자, 영문, 특수문자 각 1자리 이상 및 8~16자)")) {
                return new BasicResponse(POST_USERS_INVALID_PASSWORD);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("이름 형식을 확인해 주세요. (한글 이름 2~4자 이내)")) {
                return new BasicResponse(POST_USERS_INVALID_NAME);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("핸드폰 번호를 형식에 맞게 입력해주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_PHONE);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("닉네임 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_NICKNAME);
            }
        }
        /* 유효성 검사 구현 끝*/


        try{
            //DB에 유저 등록
            String responseMessage = userService.createUser(postUserReq);

            return new BasicResponse(responseMessage);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 2. 로그인 API
     * [POST] /users
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @PostMapping("/login")
    public BasicResponse login(@Valid @RequestBody PostLoginReq postLoginReq, BindingResult bindingResult) {  //@RequestBody PostUserReq postUserReq

        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.

            System.out.println(bindingResult.getAllErrors());
            if (errorlist.get(0).getDefaultMessage().equals("이메일 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_EMAIL);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("비밀번호를 입력해주세요.")) {
                return new BasicResponse(POST_USERS_EMPTY_PASSWORD);
            }
        }
        /* 유효성 검사 구현 끝*/


        try{
            //로그인
            PostLoginRes postLoginRes = userService.login(postLoginReq);


            return new BasicResponse(postLoginRes);
        } catch(BasicException exception){
            return new BasicResponse(exception.getStatus());
        }


    }





//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 3. 프로필 조회 API
     * [POST] /users/:userId/profile
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





////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 4. 프로필 편집 API
     * [PATCH] /users/:userId/profile
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userId}/profile")
    public BasicResponse modifyInfo(@PathVariable("userId") Long userId, @Valid @RequestBody PatchUserReq patchUserReq, BindingResult bindingResult){


        /* 유효성 검사 구현부 */
        if(bindingResult.hasErrors()) {   //에러가 있다면
            List<ObjectError> errorlist = bindingResult.getAllErrors();  //모든 에러를 뽑아온다.

            if (errorlist.get(0).getDefaultMessage().equals("이름 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_NAME);
            }
            else if (errorlist.get(0).getDefaultMessage().equals("닉네임 형식을 확인해 주세요.")) {
                return new BasicResponse(POST_USERS_INVALID_NICKNAME);
            }

        }
        /* 유효성 검사 구현 끝*/


        try {

            /* Access Token을 통한 사용자 인가 구현 */
            int userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

            if(userId != userIdByAccessToken){  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
            }
            /* Access Token을 통한 사용자 인가 구현 끝 */



            //유저 id를 Dto에 넣음
            patchUserReq.setUserId(userId);

            //유저 정보 변경
            userService.modifyInfo(patchUserReq);  //userService.java로 patchUserReq객체 값 전송

            String result = "회원 정보 변경이 완료되었습니다.";   //정보 변경 성공시 메시지 지정
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }



////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 5. 회원 탈퇴 API
     * [PATCH] /users/:userId/status
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userId}/status")
    public BasicResponse deleteUser(@PathVariable("userId") Long userId){


        try {

            /* Access Token을 통한 사용자 인가 구현 */
            int userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

            if(userId != userIdByAccessToken){  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
            }
            /* Access Token을 통한 사용자 인가 구현 끝 */



            //유저 상태 비활성화
            userService.deleteUser(userId);

            String result = "계정이 삭제되었습니다.";
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }
    }



///////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 6. 로그아웃 API
     * [PATCH] /users/:userId/logout
     * @return BaseResponse<String>
     */

    @PatchMapping("/{userId}/logout")
    public BasicResponse logout (@PathVariable("userId") Long userId){   //BaseResponse<String>      //@PathVariable("id") int userIdx

        try {

            /* Access Token을 통한 사용자 인가 구현 */
            int userIdByAccessToken = jwtService.validAccessToken();  //클라이언트에서 받아온 토큰에서 Id 추출

            if(userId != userIdByAccessToken){  //AccessToken 안의 userId와 직접 입력받은 userId가 같지 않다면
                return new BasicResponse(INVALID_USER_JWT);  //권한이 없는 유저의 접근입니다.
            }
            /* Access Token을 통한 사용자 인가 구현 끝 */


            //PatchUserReq patchUserReq = new PatchUserReq(userIdx,null,null);
            //유저 로그아웃
            userService.logout(userId);

            String result = "유저가 로그아웃 되었습니다.";   //정보 변경 성공시 메시지 지정
            return new BasicResponse(result);
        } catch (BasicException exception) {
            return new BasicResponse((exception.getStatus()));
        }



    }








}
