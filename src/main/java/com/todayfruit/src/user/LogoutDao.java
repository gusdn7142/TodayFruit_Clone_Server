package com.todayfruit.src.user;


import com.todayfruit.src.user.model.Logout;
import org.springframework.data.jpa.repository.JpaRepository;



public interface LogoutDao extends JpaRepository<Logout, Long> {
//    /* 2. Refresh 토큰 DB에 저장 ( 로그인 API) API */
//    @Query(value="INSERT into Logout (refreshToken) values (:refreshToken)")
//    String refreshTokenSave(@Param("refreshToken") String refreshToken);



}
