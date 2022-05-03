## 2022-04-27 진행상황
#### 1. '오늘과일' 서비스 도메인 분석 (100% 완료)
    - 도메인 파악 : 게시물(SNS), Product 과일, 장바구니. 주문 , 알림, 검색, 사용자, 상품 후기, 팔로우, 좋아요
    - 도메인 분석 : [개발일지](링크 추가 예정)를 참고해 주시면 감사합니다.      

## 2022-04-28 진행상황
#### 1. '오늘과일' 서비스 ERD 설계 (70% 완료)
- ERD 설계 전 [MySQL DB 명명 규칙](https://velog.io/@ohsol/MySQL-DB-%EB%AA%85%EB%AA%85-%EA%B7%9C%EC%B9%99-%EC%A0%95%EB%A6%AC)  재확인
- [ERD 구조도](https://drive.google.com/file/d/1rR1QeKPXdiCvhH1K4VlrKqYN3tTJwhd5/view) 작성 완료
- ERD 설계도 : [Aqeury tool 링크](https://aquerytool.com/aquerymain/index/?rurl=28b4d08a-e25b-40ff-8221-5bc99bffb2fb&)  (비밀번호 : kn348k) 
- ERD 설계 (진행 중)
    - DB 유형 : Mysql 
    - 담당 테이블 
        - 뎁스 : 상품(과일), 장바구니, 알림, 상품 후기, 좋아요, 댓글  
        - 스티브 : 게시물(sns), 주문, 검색, 사용자, 팔로우,  답글 
    - 완료된 사항 : 모든 테이블 설계 완료 (총 13개), 외래키 연결 완료
    - 부족한 사항 : varchar() 자료형 크기를 칼럼마다 적절히 조절, Null 허용 가능한 칼럼 파악, default 값이 필요한 칼럼 확인  
#### 2. 깃허브 협업 중 이슈 발생
    - Issue : 스티브가 개발일지(SteveREADME.md) 작성 후 제가 pull Request를 해야 merge가 되는 상황이 발생하였습니다.
    - Problem : 그 이유에 대해 [구글링](https://proglish.tistory.com/80) 해본 결과 이 깃허브에 스티브가 협업자가 아닌 초대 대기자로 권한이 되어 있는것을 확인 후 알려주었습니다.
    - Solution : 스티브가 이메일을 통해 깃허브 협업 승인을 하여 권한이 협업자로 변경됨으로써 문제가 해결되었습니다.


## 2022-04-29 진행상황
#### 1. '오늘과일' 서비스 ERD 설계 (90% 완료)
- 총 14개의 테이블 설계 완료
- ERD 설계를 100% 완료할 수 없는 이유 : API 개발시 상황에 따라 변경할 사항이 생기기 떄문
- 보완한 사항 
    - post 테이블 : 여러개의 이미지 추가를 위해 image 칼럼을 없애고 image 테이블을 새로 생성하여 왜래키로 연결
    - product 테이블 : 판매자 정보에 대해 일단 판매자 명으로 적용, 배송 일자 칼럼 추가
    - varchar() 자료형 크기를 칼럼마다 적절히 조절  (한글 한 자리에 3바이트.)
    - Null 허용 가능한 칼럼 파악 후 적용
    - default 값이 필요한 칼럼 확인 후 적용
- 추후 고민해봐야할 사항
    - purchase_notice 테이블 : 실제 구매를 해서 알림을 받아 보아야 어떤 칼럼으로 구성되는지 알 수 있어서 보류. 


#### 2. '오늘과일' 서비스 API 리스트업 (90% 완료)
- API 명세서 : [google spreadsheet 링크](https://docs.google.com/spreadsheets/d/1j0TwTTBAfpImfMHDNU-MxWzNfHTtMLbpIxqJPdOMmJY/edit#gid=1272810478)
- RestAPI 설계 규칙과 설계된 ERD를 바탕으로 API 명세서에 총 50개의 URI 리스트업
- API 리스트업을 100% 완료할 수 없는 이유 : API 개발시 상황에 따라 변경할 사항이 생기기 떄문
- 완료된 사항 
    - 사용자 관련 API : 회원가입 /로그인 / 프로필 조회 / 프로필 편집 /자동 로그인 / 로그아웃   
    - 상품 관련 API : 상품 등록 / 상품 정보 수정 / 전체 상품 조회 / 특정 상품 조회 / 상품 삭제 /상품 옵션 불러오기
    - 장바구니 관련 API : 장바구니 등록 / 장바구니 목록 조회 / 장바구니 항목 삭제
    - 주문 관련 API : 주문하기 / 주문 조회 / 주문 취소
    - 상품 리뷰 관련 API : 상품 리뷰 작성 / 상품 리뷰 조회 / 상품 리뷰 수정 / 상품 리뷰 삭제
    - 검색 관련 API : 검색어 등록 / 인기 검색어 조회 / 상품 검색
    - (sns) 게시물 관련 API : 게시물 등록 / 전체 게시물 조회 / 특정 게시물 조회 / 특정 게시물 수정 / 특정 게시물 삭제
    - 댓글 관련 API : 댓글 등록 / 전체 댓글 조회 / 댓글 수정 / 댓글 삭제 
    - 답글 관련 API : 답글 등록 / 답글 조회 / 답글 수정 / 답글 삭제
    - 게시물 관련 API : 게시물 알림 설정 추가 / 게시물 알림 등록 / 게시물 알림 내역 조회 / 게시물 알림 설정 수정
    - 팔로우 관련 API :  팔로우 등록 /  팔로워 조회 / 팔로잉 조회 / 팔로우 취소
    - 좋아요 관련 API : 좋아요 등록 / 좋아요 누른이 조회 / 좋아요 취소

#### 3. 웹서버 환경 구축 (100% 완료) 
- EC2 인스턴스 생성  : 기존 인스턴스 활용
- Nginx 서버 구축 
- 도메인 & HTTPS 적용 : 가비아에서 도메인 구입후 적용 (https://today-fruit.shop/)
  <details>
  <summary>이슈1</summary>
    <div markdown="1">
    <b> Issue </b> :  EC2의 공인 IP로 도메인(today-fruit.shop)을 새로 적용했는데, 홈페이지 연결이 되지않는다. <br> 
    <b> Problem </b>  : 공인 ip와 도메인간의 연결은 되었는데, 아직 도메인 주소 활성화 되지 않아서 그런것 같다.  <br>
    <b> Solution </b>: 1시간 정도 기다리니 도메인(today-fruit.shop)이 연결되어 nignx 첫 화면을 볼 수 있었다.   
    </div>
  </details>
        
  <details>
  <summary>이슈2</summary>
    <div markdown="1">
    <b> Issue </b> : nginx 화면은 볼 수 있지만, 어떤 경로가 root 경로로 설정되어 있는지 알 수 없었다. <br> 
    <b> Problem </b> : nginx 설정 (vim /etc/nginx/sites-available/default)을 변경하여야 했다.  <br>
    <b> Solution </b> : http://today-fruit.shop 사이트와 연관된 root 경로를 재설정 하였다. 
    </div>
  </details>
- 서브 도메인 추가 : dev.today-fruit.shop , prod.today-fruit.shop
- 리다이렉션 적용 (ip to domain)
- RDS 인스턴스 생성 : 기존 RDS (Mysql 8.0.26) 활용

#### 4. Spring boot 개발환경 구축 (100% 완료)
- Spring boot 프로젝트 생성 (+starter dependency 추가)
- Spring boot와 RDS 연동
- Spring boot 최초 빌드 및 실행 (Test API)
- 프로젝트 파일을 깃허브에 업로드
- EC2에서 Test API 실행

#### 5. 위클리 스크럼 (1차)
- 회의 결과 
    - 상품 도메인 : 상품별 카테고리 세분화는 목표로한 API가 모두 구현한 후 진행
    - API 명세서에 특정 유저가 작성한 게시물 조회 API 추가 필요 : /posts/userId 
    - 협업 방식 조율 : 하루 동안 개발한 APi를 local에서 테스트 → Github의 depth 브랜치에 push → EC2 (프로젝트 폴더)에서 API 테스트 → Github의 main 브랜치에 pull Request



## 2022-05-01 진행상황
#### 1. 기본 패키지 추가 및 엔티티 클래스 생성
- ~~controller, model (+dto), repository (or Dao), service 패키지에서~~ 도메인 중심의 패키지 설계로 변경
  <details>
  <summary>이슈</summary>
    <div markdown="1">
    <b> Issue </b> : 향후 DTO 클래스까지 생각했을때  model 패키지가 엄청 복잡해 질수 있습니다. <br> 
    <b> Problem </b> : 가독성을 위해 model 패키지의 클래스 파일 과포화를 방지해야 합니다.  <br>
    <b> Solution </b> : 기존의 Controller ~ Dao 패키지 중심의 설계 방식에서 도메인 중심의 패키지 설계방식으로 변경 
    </div>
  </details>

#### 2. Spring boot SQL 로그 확인 코드 추가
- application.yml 파일에 [다음 코드](링크 추가 예정) 추가


#### 3. user 엔티티 설계
- ERD 수정사항 
    - user 테이블 : status 칼럼을 VARCHAR(10) 타입으로 변경, Value는 0/1 형식에서 INACTIVE/ACTIVE으로 변경
- User클래스 생성 : @Entity, @Table(name = "user"), @Column 등 활용
- UserStatus 클래스 생성 : status 칼럼 표현을 위해 enum 형태로 지정  (INACTIVE가 0, ACTIVE가 1)


#### 4. user 관련 API 개발
- 프로필 조회 API  
    - UserController.java, UserService.java, UserDao.java 클래스 생성
    - Pathvariable 값으로 userId를 입력받아 해당 사용자 조회
    - findById(userId) 함수 활용 

## 2022-05-02 진행상황
#### 1. 형식적⋆의미적 Validation 처리 코드 구현
- @ResponseEntity, @RestControllerAdvice 등을 활용한 Validation 적용 방법을 찾아보았지만, 
- controller단계에서의 형식적 validation 처리와, service 단계에서의 의미적 validation 처리를 Json 형식으로 보기좋게 (ex, 오류별 상태코드 번호 지정) 처리하기에는 어렵다는 판단을 하여 Exception 클래스를 상속받아 커스텀한 코드를 사용하였습니다.
- 오류별 상태코드 지정 : 1000번(Request 성공), 2000번(Request 오류), 3000번(Response 오류), 4000번(Database, Server 오류)   

#### 2. (Update) 프로필 조회 API
- JPA에서 제공하는 findById(userId) 메서드를 @Query를 활용한 JPQL 방식으로 변경
- JPQL로 변경 이유 : 전체 칼럼이 아닌 이미지, 닉네임 등의 세부적인 요소만 조회하기 위함  
    <details>
    <summary>이슈1</summary>
        <div markdown="1">
        <b> Issue </b> :  JPA native query 사용시 dto mapping 실패 <br> 
        <b> Problem </b>  : user 칼럼을 조회하는 과정에서 쿼리의 칼럼들과 GetUserRes.java (DTO 클래스)의 멤버 변수들이 매칭이 되지 않는 문제가 발생  <br>
        <b> Solution </b>: GetUserRes.java(DTO 클래스) 파일을 interface 타입으로 변경 후 각 칼럼과 매핑될 getter() 함수들 직접 생성  
        </div>
    </details>
     
    <details>
    <summary>이슈2</summary>
        <div markdown="1">
        <b> Issue </b> : 이슈1을 통해 임시적인 해결을 하였지만, GetUserRes.java가 interface 타입으로 getter()함수만 정의되어 setter() 함수를 사용하지 못합니다.  <br> 
        <b> Problem </b> : GetUserRes.java 파일을 클래스 타입으로 다시 변환시켜 사용할 수 있는 방법을 찾아야 합니다.  <br>
        <b> Solution </b> : @Query 쿼리문의 nativeQuery 속성을 false로 바꾸어 Mysql에서 엔티티 중심의 쿼리문(new 키워드 활용)으로 변환
        </div>
   </details>   
#### 3. 인프런에서 김영한 강사님의 강의를 보며 JPQL 공부 진행
    - 실무에서는 Query DSL를 많이 사용한다는 정보를 얻었지만, 이 프로젝트에서는 JPQL을 중심으로 학습하여 활용하는것을 목표로 잡음

## 2022-05-03 진행상황
#### 1. 회원가입 API 개발
- Dto에 형식적 Validation 적용
    - build.gradle에 의존성 추가 : org.springframework.boot:spring-boot-starter-validation  
    - PostUserReq에 Validation 적용 : @NotBlank, @Pattern 어노테이션 추가
    - UserController에 Validation 적용 : @Valid, BindingResult 어노테이션 활용
- DB에 User 정보 저장
    - UserController : createUser() 함수 구현
    - UserService : createUser() 함수 구현
    - UserDao : 이메일 중복체크 함수 checkByemail() 구현     

- 패스워드 암호화 적용
    - build.gradle에 의존성 추가 : org.springframework.boot:spring-boot-starter-security
    - UserService : 패스워드 암호화를 위해 BCryptPasswordEncoder클래스의 encode() 함수 활용
    - SecurityConfig : csrf disable 설정 적용!

      <details>
        <summary>Spring Security 관련 이슈 해결</summary>
            <div markdown="1">
            <b> Issue </b> : SecurityConfig 적용후 postman으로 API 실행시 403 Forbidden 에러 발생  <br> 
            <b> Problem </b> : SpringSecurity 의존성을 설정하는 순간 CSRF protection이 deafult로 설정되어 GET 요청을 제외한 POST, PUT, DELETE 요청에 대한 모든 호출은 403 에러로 처리합니다.  <br>
            <b> Solution </b> : WebSecurityConfigurerAdapter 클래스를 상속받는 SecurityConfig 클래스를 새로 생성 후 http.csrf().disable(); 설정 적용
            </div>
      </details>   





