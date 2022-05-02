package com.todayfruit.src.user;


import com.todayfruit.src.user.model.GetUserRes;
import com.todayfruit.src.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<User, Long> {   //해당 엔티티의 클래스명, 기본키(id) 타입

//    List<User> findAll();  //전체 칼럼 조회 (생략 가능)

//    @Transactional(readOnly = true)
    Optional<User> findById(Long userId);   //id를 통한 전체 칼럼 조회  (Optional을 안써주면 호환이 되지 않는다... 왝그러지... findById()함수가 Optional로 감싸져 있다)


    /* 3. 프로필 조회 API */
//    @Query(value="select id, image, nick_Name as nickName, introduction, email from user where id = :id", nativeQuery = true)
    @Query(value="select new com.todayfruit.src.user.model.GetUserRes(id, image, nickName, introduction, email) from User where id = :id")    //nativeQuery 사용!  nativeQuery = true
    GetUserRes getUser(@Param("id") Long userId);  //@param과 쿼리의 칼럼이 매핑된다.





















}
