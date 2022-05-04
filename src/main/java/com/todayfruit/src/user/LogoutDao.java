package com.todayfruit.src.user;


import com.todayfruit.src.user.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface LogoutDao extends JpaRepository<Logout, Long> {
//    /* 2. Refresh 토큰 DB에 저장 ( 로그인 API) API */
//    @Query(value="INSERT into Logout (refreshToken) values (:refreshToken)")
//    String refreshTokenSave(@Param("refreshToken") String refreshToken);


    /* 2. 해당 사용자가 발급한 refresh 토큰을 모두 INACTIVE 시킴  ( 로그인 API) API */
    @Modifying
    @Transactional
    @Query(value="update Logout set status = 'INACTIVE' where user = :userId and status = 'ACTIVE'")
    void refreshTokenInactive(@Param("userId") User user);




}
