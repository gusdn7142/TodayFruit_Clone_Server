
# 📝 프로젝트 소개
> 백엔드 개발자 2명이서 진행한 [오늘과일 서비스](https://www.cuma.co.kr/) 클론 프로젝트입니다.  
- 제작 기간 : 2022년 4월 27일 ~ 6월 26일 
- 팀 프로젝트 협업자  
    - 서버 개발자 : 뎁스(본인), ~~스티브~~

</br>

## 💁‍♂️ Wiki
- 📌 Ground Rule
- ✍ [개발일지](추가예정)
- 📰 [API 명세서](https://docs.google.com/spreadsheets/d/1j0TwTTBAfpImfMHDNU-MxWzNfHTtMLbpIxqJPdOMmJY/edit#gid=1272810478)
- 📦 [ERD 설계도](https://user-images.githubusercontent.com/62496215/180647907-2412c611-a51b-47ac-b05d-91b174fe0f85.png)  
- 📁 [디렉토리 구조](https://github.com/gusdn7142/TodayFruit_Clone_Server/wiki/%F0%9F%93%81-Directory-Structure)
- 📽 시연 영상 : 추가예정


</br>

## 🛠 사용 기술
#### `Back-end`
  - Java 11
  - Spring Boot 2.6.7
  - Gradle 7.4.1
  - Spring Data JPA
  - Spring Security
#### `DevOps`  
  - AWS EC2 (Ubuntu 20.04)  
  - AWS RDS (Mysql 8.0)
  - AWS S3 
  - Nginx
  - GitHub
#### `Etc`  
  - JWT

</br>

## 📦 ERD 설계도
![TodayFruit_ERD1](https://user-images.githubusercontent.com/62496215/180647907-2412c611-a51b-47ac-b05d-91b174fe0f85.png)



</br>

## 🔎 핵심 기능 및 담당 기능
>오늘과일 서비스의 핵심기능은 과일 상품 판매 및 SNS 글 홍보입니다.  
>서비스의 세부적인 기능은 [API 명세서](https://docs.google.com/spreadsheets/d/1j0TwTTBAfpImfMHDNU-MxWzNfHTtMLbpIxqJPdOMmJY/edit#gid=1272810478)를 참고해 주시면 감사합니다.   
- 담당 기능  
    - 사용자 : 회원가입 API, 로그인 API, 프로필 조회∘편집 API, 회원탈퇴∘로그아웃 API, Access token 재발급 API 
    - 상품 정보 : 상품정보 등록∘수정∘조회∘삭제 API, 상품 옵션 조회 API     
    - 상품 구매 : 상품 구매 API, 주문한 상품 목록 조회 API, 상품 주문 취소 API
    - 상품 리뷰 : 상품리뷰 작성∘조회∘수정∘삭제 API
- 담당 API 분배 기준 : 다른 서버 개발자(스티브)와 Github으로 협업시 코드 충돌을 방지하기 위해 도메인별로 API를 분배하여 개발을 진행하였습니다.  



</br>

## 🌟 트러블 슈팅
추가 예정

<details>
<summary> 1. JPA native query 사용시 dto mapping 실패 </summary>
<div markdown="1">

- **Issue** : JPA native query 사용시 dto mapping에 실패하는 문제가 발생하였습니다.  
![Untitled (24)](https://user-images.githubusercontent.com/62496215/205444000-8700df2a-cd42-4f2b-8b36-a3258ed4998e.png)
- **Problem** : user 테이블의 칼럼들을 조회하는 과정에서 쿼리의 칼럼들과 GetUserRes.java (DTO 클래스)의 멤버 변수들이 매핑 되지 않는 문제가 발생하였습니다.  
- **Solution1** : GetUserRes.java(DTO 클래스) 파일을 interface 타입으로 변경 후 각 칼럼과 매핑될 getter() 함수를 직접 생성  
  ```sql
      public interface GetUserRes {
            Long getId();
            String getImage();
            String getNickName();
            String getIntroduction();
            String getEmail();
      }
  ```    
- **Solution2** :  @Query 쿼리문의 nativeQuery 속성을 false로 바꾸어 JPQL을 사용하고 new 키워드를 통해 GetUserRes.java(DTO 클래스)를 매핑할 수 있습니다.  
  ```sql
     @Query(value="select new com.todayfruit.src.user.model.GetUserRes(id, image, nickName, introduction, email) from User where id = :id", nativeQuery = false)   
     GetUserRes getUser(@Param("id") Long userId); 
  ```    
</div>
</details>



</br>

## ❕ 회고 / 느낀점
- 다른 백엔드 개발자와 함께 JPA에 대해 학습한 내용을 토대로 프로젝트를 진행하였는데, 사정이 생겨 혼자서 프로젝트를 마무리하게 되었습니다.
- 이 프로젝트를 통해 스프링 개발환경을 직접 구축해보는 경험을 할수 있었고 기존의 JDBC 방식의 넘어서서 Spring JPA의 JPQL 방식으로 CRUD를 구현하는 개발 역량을 쌓을수 있었습니다. 또한 S3 서비스를 연동해 파일 업로드를 구현했던것이 기억에 남습니다.
- JPQL을 사용하다보니 RDB 컬렉션 제약조건과 서브쿼리 처리가 문법상 불가능하기 때문에 복잡한 쿼리 작성이 가능한 QueryDSL을 적용해볼 계획입니다



## 👩‍💻 리팩토링 계획
- [ ] Access-Token을 활용한 사용자 인가 로직을 인터셉터를 활용해 공통 로직으로 처리
- [ ] 전체 상품 조회 API와 상품 리뷰 조회 API에 페이징 기능 적용 
- [ ] GET 방식의 PathVarilabe과 Query String으로 받아온 입력 값에 대한 유효성 검사 추가  
- [ ] Docker를 이용해 Spring Boot 애플리케이션 배포
- [ ] 모든 API에 Swagger 적용
