package com.todayfruit.user;


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

    /* 프로필 조회 API*/
    public List<User> getUser(){
        List<User> user = userDao.findAll();  //BookRepository에서 도서의 id를 통해 raw를 받아옴?

        if(!user.isEmpty()){  //사용자 프로필 목록이 비어있지 않다면.
            return user;
        }

        throw new EntityNotFoundException("전체 유저의 프로필 조회 실패"); //id 찾기 실패

    }

////////////////////////////////////////////////////////////////////////////////////////////////////////

    /* (id로) 프로필 조회 API*/
    public Optional<User> getUser(Long userId){

        Optional<User> user = userDao.findById(userId);  //BookRepository에서 도서의 id를 통해 raw를 받아옴?

//        System.out.println(user);
//        System.out.println("차이가 사람을 만든다.");
//        System.out.println(user.get());

        if (user.isPresent()){ //값이 존재하면 true
            return user;  //user.get();
        }

        throw new EntityNotFoundException("id를 통한 프로필 조회 실패");
    }





}
