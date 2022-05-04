package com.todayfruit.src.user;


import com.todayfruit.src.user.model.GetUserRes;
import com.todayfruit.src.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.awt.print.Book;
import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<User, Long> {   //해당 엔티티의 클래스명, 기본키(id) 타입

//    List<User> findAll();  //전체 칼럼 조회 (생략 가능)

//    @Transactional(readOnly = true)
    Optional<User> findById(Long userId);   //id를 통한 전체 칼럼 조회  (Optional을 안써주면 호환이 되지 않는다... 왝그러지... findById()함수가 Optional로 감싸져 있다)
                                            //현재 안쓰는중!!
    User findByEmail(String email);       //(회원 가입 API) API



    /* 1. 이메일 중복 검사    (회원 가입 API) API */
    @Query(value="select u from User u where u.email = :email and u.status = 'ACTIVE'")
    User checkByemail(@Param("email") String email);


    /* 2. 패스워드와 idx 가져오기 ( 로그인 API) API */
    @Query(value="select u.password from User u where u.email = :email and u.status = 'ACTIVE'")
    String checkByPassword(@Param("email") String email);









    /* 3. 프로필 조회 API */
//    @Query(value="select id, image, nick_Name as nickName, introduction, email from user where id = :id", nativeQuery = true)
    @Query(value="select new com.todayfruit.src.user.model.GetUserRes(id, name, image, nickName, introduction, email) from User where id = :id")    //nativeQuery 사용!  nativeQuery = true
    GetUserRes getUser(@Param("id") Long userId);  //@param과 쿼리의 칼럼이 매핑된다.



//    @PersistenceContext
//    EntityManager em = null;
//    List<User> email2 =  em.createQuery("select u from User u where u.email = :email and  u.status = 'ACTIVE'")
//                .setParameter("email", email)
//                .getResultList();
//    }




//Optional : 'null일 수도 있는 객체'를 감싸는 일종의 Wrapper 클래스
















}
