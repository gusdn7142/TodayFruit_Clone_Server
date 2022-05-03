package com.todayfruit.src.user;

import com.todayfruit.config.BasicException;
import static com.todayfruit.config.BasicResponseStatus.*;


import com.todayfruit.src.user.model.GetUserRes;
import com.todayfruit.src.user.model.PostUserReq;
import com.todayfruit.src.user.model.User;
import com.todayfruit.src.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Book;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Random;


@Service
@RequiredArgsConstructor  //final이 붙거나 @NotNull 이 붙은 필드의 생성자를 자동 생성해주는 롬복 어노테이션
public class UserService {

    private final UserDao userDao;


///////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    /* 프로필 조회 API*/
//    public List<User> getUser(){
//        List<User> user = userDao.findAll();  //BookRepository에서 도서의 id를 통해 raw를 받아옴?
//
//        if(!user.isEmpty()){  //사용자 프로필 목록이 비어있지 않다면.
//            return user;
//        }
//
//        throw new EntityNotFoundException("전체 유저의 프로필 조회 실패"); //id 찾기 실패
//
//    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    /* (id로) 프로필 조회 API*/
//    public Optional<User> getUser(Long userId) throws BasicException {
//
//        Optional<User> user = userDao.findById(userId);  //BookRepository에서 도서의 id를 통해 raw를 받아옴?
//
//        if (!user.isPresent()) {    //값이 존재하면 true
//            System.out.println(user);
//            throw new BasicException(DATABASE_ERROR);   //'회원 탈퇴(유저 비활성화)에 실패하였습니다.'
//        }
//
//        return user;
//    }





//        try {
//                throw new RuntimeException("Customer not found");

//        }
//        catch(Exception exception){
//            System.out.println("객체가 없어요");
//            throw new BasicException(DATABASE_ERROR2);   //'회원 탈퇴(유저 비활성화)에 실패하였습니다.'
//        }






//        System.out.println(user);
//        System.out.println("차이가 사람을 만든다.");
//        System.out.println(user.get());

//            if (user.isEmpty()){ //값이 존재하면 true
//        if (user.isPresent()){ //값이 존재하면 true
//            return user;  //user.get();
//        }
//        throw new EntityNotFoundException("id를 통한 프로필 조회 실패");







// ////////////////////////////////////////////////////////////////////////////////////////////////////
    /* 1. 회원 가입 -  createUser() */
    public String createUser(PostUserReq postUserReq) throws BasicException {

        //이메일 중복 검사 ("ACTIVE"가 1일떄 포함)
        if (userDao.checkByemail(postUserReq.getEmail()) != null){   //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!
            throw new BasicException(POST_USERS_EXISTS_EMAIL); //"이미 가입된 이메일 입니다."
        }
//        User checkUser = userDao.findByEmail(postUserReq.getEmail());  //테이블에 같은 이메일이 여러개면 오류난다....List로 처리해야함 그럴땐!!


        //DB에 유저 등록 (이메일, 비밀번호, 이름, 전화번호, 닉네임 저장)
        try{
            User userCreate = new User();  //userCreate 객체 생성
            BeanUtils.copyProperties(postUserReq,userCreate);  //postUserReq(dto) 객체의 내용을 userCreate로 옮긴다.


            //패스워드 암호화
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);  //BCryptPasswordEncoder 클래스 활용 (암호화 속도는 default가 10)
            userCreate.setPassword(encoder.encode(postUserReq.getPassword()));  //userCreate 객체에 암호화된 패스워드 삽입



            //String salt = "asdasd2312eawsdsad";
            //.getSalt(salt)
//            String decryptPassword = userDao.getPassword(postUserReq.getEmail());  //이메일로 패스워드 뺴오기

//            BCryptPasswordEncoder encoder2 = new BCryptPasswordEncoder();  //BCryptPasswordEncoder 클래스 활용
            //패스워드 복호화 (matches로 확인만 한다!)
//            if(encoder2.matches("AS",decryptPassword)){
//                System.out.println("암호화된 비밀번호는 다음과 같습니다."+decryptPassword);
//                System.out.println("패스워드가 일치합니다!");
//            }
//            else{
//                System.out.println("패스워드가 일치하지 않습니다.");
//
//            }


            userDao.save(userCreate);   //"user" DB에 정보 저장
            return "사용자 등록에 성공하였습니다.";

        } catch (Exception exception) {
            throw new BasicException(DATABASE_ERROR_CREATE_USER);  //유저 생성 실패 에러
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



















}
