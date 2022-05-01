package com.todayfruit.user;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface UserDao extends JpaRepository<User, Long> {   //해당 엔티티의 클래스명, 기본키(id) 타입

//    List<User> findAll();  //생략 가능
    Optional<User> findById(Long userId);   //Optional을 안써주면 호환이 되지 않는다... 왝그러지...













}
