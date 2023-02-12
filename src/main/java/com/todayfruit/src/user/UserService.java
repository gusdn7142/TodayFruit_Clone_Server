package com.todayfruit.src.user;

import com.todayfruit.config.BasicException;
import static com.todayfruit.config.BasicResponseStatus.*;


import com.todayfruit.src.user.model.*;
import com.todayfruit.src.user.UserDao;
import com.todayfruit.src.user.model.domain.Logout;
import com.todayfruit.src.user.model.domain.User;
import com.todayfruit.src.user.model.request.PatchUserReq;
import com.todayfruit.src.user.model.request.PostLoginReq;
import com.todayfruit.src.user.model.response.PostLoginRes;
import com.todayfruit.src.user.model.request.PostUserReq;
import com.todayfruit.src.user.model.response.GetUserRes;
import com.todayfruit.src.user.model.response.PostAccessTokenRes;
import com.todayfruit.util.JwtService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Book;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class UserService {

    private final UserDao userDao;
    private final JwtService jwtservice;
    private final LogoutDao logoutDao;


// ////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 1. 회원 가입 -  createUser() */
    public String createUser(PostUserReq postUserReq) throws BasicException {

        //이메일 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.checkByemail(postUserReq.getEmail()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
            throw new BasicException(POST_USERS_EXISTS_EMAIL); //"이미 가입된 이메일 입니다."
        }
//        User checkUser = userDao.findByEmail(postUserReq.getEmail());  //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.checkNickName(postUserReq.getNickName()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
            throw new BasicException(POST_USERS_EXISTS_NICKNAME); //"이미 존재하는 닉네임 입니다."
        }


            //DB에 유저 등록 (이메일, 비밀번호, 이름, 전화번호, 닉네임 저장)
            try{
                User userCreate = new User();  //userCreate 객체 생성
                BeanUtils.copyProperties(postUserReq,userCreate);  //postUserReq(dto) 객체의 내용을 userCreate로 옮긴다. (DB에 저장하기 위함.)


            //패스워드 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);  //BCryptPasswordEncoder 클래스 활용 (암호화 속도는 default가 10)
            userCreate.setPassword(encoder.encode(postUserReq.getPassword()));  //userCreate 객체에 암호화된 패스워드 삽입

            userDao.save(userCreate);   //"user" DB에 정보 저장
            return "사용자 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_USER);  //유저 생성 실패 에러
        }
    }





 ///////////////////////////////////////////////////////////////////////////////////////////////////
    /* 2. 로그인 -  login() */
    public PostLoginRes login(PostLoginReq postLoginReq) throws BasicException {

        //DB에서 해당 이메일에 해당하는 사용자를 조회하여 (암호화된)패스워드를 가져옴.
        String bcryptPassword = null;
        if(userDao.checkByPassword(postLoginReq.getEmail()) != null){  //이메일에 해당하는 패스워드가 존재하면.
            bcryptPassword = userDao.checkByPassword(postLoginReq.getEmail());
        }
        else{ //이메일에 해당하는 패스워드가 없다면 오류메시지 출력
            throw new BasicException(FAILED_TO_JOIN_CHECK); //"가입되지 않은 사용자입니다."
        }


        //패스워드 일치여부 확인 (matches로 확인만 한다! 복호화 no)
        BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();  //BCryptPasswordEncoder 클래스 활용
        if(!encoder2.matches(postLoginReq.getPassword(),bcryptPassword)){      //입력받은 password와 DB에서 불러온 (암호화된)password를 비교
            throw new BasicException(PASSWORD_MATCH_FAIL); //"잘못된 패스워드입니다."
        }


        //jwt 발급 (accessToken, refreshToken)
        User userLogin = userDao.checkByemail(postLoginReq.getEmail());           //userIdx를 보내기 위함 (이미 앞에서 검증하여 오류X)
        String accessToken = jwtservice.createAccessToken(userLogin.getId());    //accessToken 발급 : 사용자 인가 절차 구현 (만료시간 3시간)
        String refreshToken = jwtservice.createRefreshToken(userLogin.getId());    //refreshToken 발급  : access 토큰 발급 용도 (만료시간 1개월)

        //만료기한 확인해보기
        //jwtservice.getJwtContents(accessToken);
        //jwtservice.getJwtContents(refreshToken);


        // 해당 사용자가 발급한 refresh 토큰을 모두 INACTIVE 시킴
        try{
            logoutDao.refreshTokenInactive(userLogin);
        }catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_INACTIVE_RefreshToken);  //"refresh 토큰 비활성화에 실패하였습니다."
        }


        try{
            //Refresh 토큰을 DB에 저장
            Logout logoutDBCreate = new Logout();  //logoutDBCreate 객체 생성
            logoutDBCreate.setRefreshToken(refreshToken);
            logoutDBCreate.setUser(userLogin);

            logoutDao.save(logoutDBCreate);  //"logout" DB에 정보 저장
        }catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_SAVE_RefreshToken);  //"refresh 토큰 저장에 실패하였습니다."
        }


        try {
            //postLoginRes 객체에 userIdx와 jwt를 담아 클라이언트에게 전송
            PostLoginRes postLoginRes = PostLoginRes.builder()
                    .userId(userLogin.getId())
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .build();

            return postLoginRes;

        }catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_LOGIN_USER);  //"로그인 실패"
        }




    }


////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 3. 프로필 조회 API */
    public GetUserRes getUser(Long userId) throws BasicException {

        //프로필 조회
        GetUserRes getUserRes = userDao.getUser(userId);

        //DB에서 받아온 객체가 null이면
        if(getUserRes==null){
            throw new BasicException(DATABASE_ERROR_USER);   //'DB에서 프로필 조회에 실해하였습니다.'
        }
        return getUserRes;
    }



//// ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 4. 프로필 편집 - modifyInfo()  */
    public void modifyInfo(PatchUserReq patchUserReq) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...

        //닉네임 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.checkNickName(patchUserReq.getNickName()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
            throw new BasicException(POST_USERS_EXISTS_NICKNAME); //"이미 존재하는 닉네임 입니다."
        }


        try{
            //이름 변경
            if(patchUserReq.getName() != null){
                userDao.modifyName(patchUserReq.getName(), patchUserReq.getUserId());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NAME);   //"이름 변경시 오류가 발생하였습니다."
        }


        try{
            //닉네임 값 변경
            if(patchUserReq.getNickName() != null){
                userDao.modifyNickName(patchUserReq.getNickName(), patchUserReq.getUserId());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_NICKNAME);   //"닉네임 변경시 오류가 발생하였습니다"
        }


        try{
            //소개글 변경
            if(patchUserReq.getIntroduction() != null){
                userDao.modifyIntroduction(patchUserReq.getIntroduction(), patchUserReq.getUserId());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_INTRODUCTION);   //"소개글 변경시 오류가 발생하였습니다."
        }

        try{
            //이미지 변경
            if(patchUserReq.getImage() != null){
                userDao.modifyImage(patchUserReq.getImage(), patchUserReq.getUserId());
            }
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_MODIFY_FAIL_USER_IMAGE);   //"이미지 변경시 오류가 발생하였습니다."
        }



    }



 /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 5. 회원 탈퇴 - deleteUser()  */
    public void deleteUser(Long userId) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...

        //회원 탈퇴 여부 확인 (유저가 계속 클릭시..)
        if(userDao.checkStatusUser(userId) == null){
            throw new BasicException(PATCH_USERS_DELETE_USER);  //"이미 탈퇴된 계정입니다."
        }

        try{
            //회원 탈퇴
            userDao.deleteUser(userId);
        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_DELETE_USER);   //'회원 탈퇴(유저 비활성화)에 실패하였습니다.'
        }

    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* 7. 로그아웃 - logout()  */
    public void logout(Long userId) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...


        Optional<User> userLogout = userDao.findById(userId);  //외래키 활용을 위해 해당 User 객체를 불러옴.
//        if(!logoutDao.checkLogout(userLogout).isEmpty()){  //로그아웃된 상태이면
//            throw new BasicException(PATCH_USERS_LOGOUT_USER);  //"이미 로그아웃된 유저입니다."
//        }

        try{
            //해당 유저의 Logout 레코드 모두 비활성화
            logoutDao.logout(userLogout.get());

        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAIL_LOGOUT);   //"로그아웃에 실패 하였습니다."
        }
    }



/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* 8. Access token 재발급 - createAccessToken()  */
    public PostAccessTokenRes createAccessToken(Long userId, String refreshToken) throws BasicException {    //UserController.java에서 객체 값( id, nickName)을 받아와서...


        //RefreshToken 활성화 여부 확인 (로그아웃 상태 확인)
        if (logoutDao.checkByRefreshToken(refreshToken) != null){   //로그아웃된 상태이면!!
            throw new BasicException(PATCH_USERS_LOGOUT_USER); //"이미 로그아웃된 유저입니다."
        }



        try{
            //AccessToken 재발급
            Optional<User> userLogin = userDao.findById(userId);
            String accessToken = jwtservice.createAccessToken(userLogin.get().getId());    //accessToken 발급

            PostAccessTokenRes postAccessTokenRes = new PostAccessTokenRes(userId,accessToken);  //userId와 발급된 accessToken 리턴


            return postAccessTokenRes;

        } catch(Exception exception){
            throw new BasicException(DATABASE_ERROR_FAI_Reissuance_AccessToken);   //"Access 토큰 재발급에 실패하였습니다."
        }
    }













}
