package com.todayfruit.src.user;

import com.todayfruit.config.BasicException;
import static com.todayfruit.config.BasicResponseStatus.*;


import com.todayfruit.src.user.model.GetUserRes;
import com.todayfruit.src.user.model.User;
import com.todayfruit.src.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;


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
