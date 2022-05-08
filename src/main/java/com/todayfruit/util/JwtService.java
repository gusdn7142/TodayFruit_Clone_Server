package com.todayfruit.util;


import com.todayfruit.config.BasicException;
import com.todayfruit.config.BasicResponse;
import com.todayfruit.config.secret.Secret;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.todayfruit.config.BasicResponseStatus.*;


@Service
public class JwtService {

    /*
    로그인시 JWT (AccessToken) 발급
    @param userIdx
    @return String
     */
    public String createAccessToken(Long userIdx) {  //access 토큰 생성
        Date now = new Date();
        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE) //헤더 타입 지정
                .setIssuer(Secret.JWT_ISSUER)  //토큰 발급자 지정
                .setIssuedAt(now)    //토큰 발급 시간 (페이로드)
                .setExpiration(new Date(now.getTime()+(1000 * 60 * 60 * 3)))  //마감 기한 (하루 : 3시간 * 60분 * 60초 * 1000밀리세컨드)
                .claim("userIdx", userIdx)      //페이로드 (유저의 idx (primary 키)값)
                .signWith(SignatureAlgorithm.HS256, Secret.ACCESS_TOKEN_KEY)                 //서명 (헤더의 alg인 HS256 알고리즘 사용, 비밀키로 JWT_SECRET_KEY 사용)
                .compact();
    }


 //now.getTime()+(1000 * 60 * 60 * 24) 는 하루이다!!
//




    /*
 로그인시 JWT (RefreshToken) 발급
 @param userIdx
 @return String
  */
    public String createRefreshToken(Long userIdx) {  //Refresh 토큰 생성
        Date now = new Date();  //현재시각

        /* 토큰 마감기한에 사용 */
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);  //현재시각
        cal.add(Calendar.MONTH, +1); //1개월 더하기
        /*                  */

        return Jwts.builder()
                .setHeaderParam(Header.TYPE,Header.JWT_TYPE) //헤더 타입 지정
                .setIssuer(Secret.JWT_ISSUER)  //토큰 발급자 지정
                .setIssuedAt(now)    //토큰 발급 시간 (페이로드)
                .setExpiration(new Date(cal.getTimeInMillis()))  //마감 기한 (1개월)
                .claim("userIdx", userIdx)      //페이로드 (유저의 idx (primary 키)값)
                .signWith(SignatureAlgorithm.HS256, Secret.REFRESH_TOKEN_KEY)                 //서명 (헤더의 alg인 HS256 알고리즘 사용, 비밀키로 JWT_SECRET_KEY 사용)
                .compact();
    }


    //1000 * 60 * 60 * 24)*2 등 한달 이상 설정시 오류 발생!!






//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//    /*
//    Header에서 이름("AccessToken" 혹은 "RefreshToken")으로 토큰 값 추출
//    @return String
//     */
//    public String[] getJwt(){
//        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
//
//        String accessToken = request.getHeader("AccessToken");  //Access Token 값 가져오기
////        String refreshToken = request.getHeader("RefreshToken");  //Refresh Token 값 가져오기
//
////        String[] jwt = {accessToken, refreshToken};
//
//        return jwt;
//    }



    /*
    Access Token의 유효성과 만료여부 확인 (+JWT에서 userIdx 추출)
    @return int
    @throws BaseException
     */
    public Long validAccessToken() throws BasicException{

        //Header에서 이름("AccessToken" )으로 토큰 값 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("AccessToken");  //Access Token 값 가져오기

        //accessToken 값의 null 체크
        if(accessToken == null || accessToken.length() == 0){
            throw new BasicException(EMPTY_ACCESS_TOKEN);  //"Access Token을 입력해 주세요."
        }

        // JWT 파싱
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()                //유효한 토큰인지 확인,  즉 로그인시 부여한 jwt 토큰인지 확인
                    .setSigningKey(Secret.ACCESS_TOKEN_KEY)   //서명키 입력
                    .parseClaimsJws(accessToken);
        } catch (Exception ignored) {             //오류발생시 리턴
            throw new BasicException(INVALID_ACCESS_TOKEN);  //"Access Token이 변조되었거나 만료되었습니다""
        }

        //  userIdx 추출  (위의 과정에서 문제가 없다면 수행)
        return claims.getBody().get("userIdx", Long.class);
    }








    /*
    Refresh Token의 유효성과 만료여부 확인 (+JWT에서 userIdx 추출)
    @return int
    @throws BaseException
     */
    public Object[] validRefreshToken() throws BasicException{

        //Header에서 이름("AccessToken" )으로 토큰 값 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String RefreshToken = request.getHeader("RefreshToken");  //Access Token 값 가져오기

        //accessToken 값의 null 체크
        if(RefreshToken == null || RefreshToken.length() == 0){
            throw new BasicException(EMPTY_REFRESH_TOKEN);  //"Refresh Token을 입력해 주세요."
        }

        // JWT 파싱
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()                //유효한 토큰인지 확인,  즉 로그인시 부여한 jwt 토큰인지 확인
                    .setSigningKey(Secret.REFRESH_TOKEN_KEY)   //서명키 입력
                    .parseClaimsJws(RefreshToken);
        } catch (Exception ignored) {             //오류발생시 리턴
            throw new BasicException(INVALID_REFRESH_TOKEN);  //"Refresh Token이 변조되었거나 만료되었습니다""
        }

        //  userIdx 추출  (위의 과정에서 문제가 없다면 수행)
        return new Object[]{claims.getBody().get("userIdx", Long.class),RefreshToken};
    }









    /*
    Access Token의 유효성과 만료여부 확인 (+JWT에서 userIdx 추출)
    @return int
    @throws BaseException
     */
    public int checkAccessToken() throws BasicException{

        //Header에서 이름("AccessToken" )으로 토큰 값 추출
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("AccessToken");  //Access Token 값 가져오기

        //accessToken 값의 null 체크
        if(accessToken == null || accessToken.length() == 0){
            throw new BasicException(EMPTY_ACCESS_TOKEN);  //"Access Token을 입력해 주세요."
        }

        // JWT 파싱
        Jws<Claims> claims;
        try{
            claims = Jwts.parser()                //유효한 토큰인지 확인,  즉 로그인시 부여한 jwt 토큰인지 확인
                    .setSigningKey(Secret.ACCESS_TOKEN_KEY)   //서명키 입력
                    .parseClaimsJws(accessToken);

            System.out.println(claims);

            return 1; //토큰이 만료되지 않았다면 1 반환
            //throw new BasicException(INVALID_ACCESS_TOKEN);  //"Access Token이 변조되었거나 만료되었습니다""
        }
        catch (Exception ignored){
            return 0;  //토큰이 만료 되었다면 0 반환
        }


    }




















//////////////////////////////////////////////////////////////////
    // Jwt Token에서 데이터를 전달
    public Claims getJwtInformation(String jwt) {
        Claims claims =Jwts.parser().setSigningKey(Secret.ACCESS_TOKEN_KEY).parseClaimsJws(jwt).getBody();
        return claims;
    }





///////////////////////////////////////////////////////////////////
    //Jwt  토큰 정보(만료시각) 확인 메서드
    public void getJwtContents(String jwt) {  //Claims
        Jws<Claims> claims;
        claims = Jwts.parser()  //유효한 토큰인지 확인,  즉 로그인시 부여한 jwt 토큰인지 확인
                .setSigningKey(Secret.ACCESS_TOKEN_KEY)
                .parseClaimsJws(jwt);
        //try문 추가해야 만료되도 오류 안난다!!


        System.out.println("\n 현재!! 날짜!! ");                         //현재 날짜 및 시간 확인
        System.out.println(new Date(System.currentTimeMillis()));
        System.out.println("\n 현재!! 날짜를 초로 표시!! ");                         //현재 날짜 및 시간 확인
        System.out.println(new Date(System.currentTimeMillis()).getTime()/1000);


        System.out.println("\n jwt 토큰 만료시각 (카카오 jwt는 현재 날짜와 6시간 차이)");
        System.out.println(claims.getBody().getExpiration());                    //토큰 정보 확인
        System.out.println("\n jwt 토큰 만료시각 (날짜를 초로 표시)!!");
        System.out.println(claims.getBody().getExpiration().getTime()/1000);   //토큰의 날짜를 초로 변환

        System.out.println("\n");
        System.out.println("\n");


//        System.out.println("\n 밀리세컨드 ");
//        System.out.println(System.currentTimeMillis());
//
//        System.out.println("\n 밀리세컨드 +1");
//        System.out.println(System.currentTimeMillis()+1);
//
//        System.out.println("\n 밀리세컨드 연산");
//        System.out.println(System.currentTimeMillis()+1*(1000*60*60*24*365));
//
//        System.out.println("\n 밀리세컨드 -> 날짜 변환");
//        System.out.println(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)));
//
//        System.out.println("\n 날짜 -> 밀리세컨드 변환");
//        System.out.println(new Date(System.currentTimeMillis()+1*(1000*60*60*24*365)).getTime());
//        System.out.println("\n");


//        return claims;
    }







}