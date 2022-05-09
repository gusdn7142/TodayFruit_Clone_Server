package com.todayfruit.src.user;


import com.todayfruit.src.user.model.*;
import com.todayfruit.src.user.model.domain.User;
import com.todayfruit.src.user.model.response.GetUserRes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
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


    /* 상품등록시 활용됨 */
    //@Query(value="select u from User u where u.email = :email and u.status = 'ACTIVE'")
   // User findById(@Param("userId")  userId);




    User findByEmail(String email);       //(회원 가입 API) API



    /* 1. 이메일 중복 검사    (회원 가입 API) API */
    @Query(value="select u from User u where u.email = :email and u.status = 'ACTIVE'")
    User checkByemail(@Param("email") String email);




    /* 1. 닉네임 중복 검사    (회원 가입 API) API */
    @Query(value="select u from User u where u.nickName = :nickName and u.status = 'ACTIVE'")
    User checkNickName(@Param("nickName") String nickName);


    /* 2. 패스워드와 idx 가져오기 ( 로그인 API) API */
    @Query(value="select u.password from User u where u.email = :email and u.status = 'ACTIVE'")
    String checkByPassword(@Param("email") String email);





    /* 3. 프로필 조회 API */
//    @Query(value="select id, image, nick_Name as nickName, introduction, email from user where id = :id", nativeQuery = true)
    @Query(value="select new com.todayfruit.src.user.model.response.GetUserRes(id, name, image, nickName, introduction, email) from User where id = :id")    //nativeQuery 사용!  nativeQuery = true
    GetUserRes getUser(@Param("id") Long userId);  //@param과 쿼리의 칼럼이 매핑된다.



    /* 4. 프로필 편집 API */
    //이름 변경
    @Modifying
    @Transactional
    @Query(value="update User set name = :name where id = :userId and status = 'ACTIVE'\n")
    void modifyName(@Param("name") String name, @Param("userId") Long userId );

    //닉네임 변경
    @Modifying
    @Transactional
    @Query(value="update User set nickName = :nickName where id = :userId and status = 'ACTIVE'\n")
    void modifyNickName(@Param("nickName") String nickName, @Param("userId") Long userId );

    //소개글 변경
    @Modifying
    @Transactional
    @Query(value="update User set introduction = :introduction where id = :userId and status = 'ACTIVE'\n")
    void modifyIntroduction(@Param("introduction") String introduction, @Param("userId") Long userId );

    //이미지 변경
    @Modifying
    @Transactional
    @Query(value="update User set image = :image where id = :userId and status = 'ACTIVE'\n")
    void modifyImage(@Param("image") String image, @Param("userId") Long userId );





    /* 5. 회원 탈퇴 API */
    @Modifying
    @Transactional
    @Query(value="update User set status = 'INACTIVE' where id = :userId and status = 'ACTIVE'\n")
    void deleteUser(@Param("userId") Long userId );


    /* 5. user 계정 활성화 여부 확인   (회원 탈퇴 API) */
    @Query(value="SELECT u FROM User u where u.id =:userId and u.status = 'INACTIVE'")
    User checkdeleteUser(@Param("userId") Long userId );








//    @PersistenceContext
//    EntityManager em = null;
//    List<User> email2 =  em.createQuery("select u from User u where u.email = :email and  u.status = 'ACTIVE'")
//                .setParameter("email", email)
//                .getResultList();
//    }

//Optional : 'null일 수도 있는 객체'를 감싸는 일종의 Wrapper 클래스
















}
