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

       <details>
        <summary>save() 함수 관련 관련 이슈 해결</summary>
            <div markdown="1">
            <b> Issue </b> : user 엔티티 클래스에 default 값을 지정해 주었지만, save() 함수로 칼럼 값을 저장시 create_at, status, update_at 칼럼들에 default값이 자동으로 들어가지 않음.  <br> 
            <b> Problem </b> : postUserReq (DTO) 객체를 User클래스 형식의 userCreate 객체에 복사할때 create_at, status, update_at 칼럼들은 null 값으로 지정되어 save() 함수로 DB 저장시 null 값이 그대로 들어갑니다..  <br>
            <b> Solution </b> : DB에 insert와 update시 null인 필드는 제외하도록 user 엔티티 클래스에 @DynamicInsert와 @DynamicUpdate 어노테이션을 추가하여 create_at, status, update_at 칼럼에는 default값이 적용되게 해주었습니다. 
            </div>
      </details>   
    
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

- DB에 User 정보 저장
    - UserController : createUser() 함수 구현
    - UserService : createUser() 함수 구현
    - UserDao : 이메일 중복체크 함수 checkByemail() 구현     

## 2022-05-04 진행상황
#### 1. 로그인 API 개발
- Dto에 형식적 Validation 적용 : 이메일과 비밀번호 형식 확인 
    - PostLoginReq에 Validation 적용 : @NotBlank, @Pattern 어노테이션 추가
    - UserController에 Validation 적용 : @Valid, BindingResult 어노테이션 활용

- 로그인시 사용자 인증 : 이메일을 통해 패스워드 일치 여부 확인 (+패스워드 복호화? no) 
    - UserService : checkByPassword() 함수와 BCryptPasswordEncoder 클래스의 matches() 함수 활용

- Jwt 토큰 발급 및 반환 (access token, refresh token)
    - build.gradle에 의존성 추가 
    - JwtService : acccess token(3시간 후 만료)과 refresh token(1개월 후 만료) 생성

      <details>
        <summary>Date() 함수 관련 이슈 해결</summary>
            <div markdown="1">
            <b> Issue </b> : refresh token의 발급시간을 지정하기 위해 new Date(now.getTime()+(1000 * 60 * 60 * 24 * 30)으로 셋팅한 후 API 테스트를 진행하면 “JWT expired at 2022-04-15T00:48:37Z”  라는 jwt 만료 메시지가 출력됩니다.  <br> 
            <b> Problem </b> : 24일 이하로 셋팅 (now.getTime()+(1000 * 60 * 60 * 24 * 30)해야 정상적으로 동작이 됩니다.   <br>
            <b> Solution </b> : 정확한 원인은 찾지 못하여 숫자 대신 Calendar 클래스로 한달을 지정하여 사용하였습니다.
            </div>
      </details>   

    - PostLoginRes : userId, accessToken, refreshToken 반환     
    - UserService : acccess token과 refresh token 발급

- Refresh Token DB에 저장 (+기존 Refresh Token DB에서 비활성화)
    - Logout 엔티티 클래스 추가 : user_id(외래키) 칼럼은 user 엔티티 클래스로 매핑
    - LogoutDao 추가 : user엔티티 클래스를 인자로 받는 refreshTokenInactive() 함수 정의
    - UserService :  refresh token DB에 저장  //이전에 발급된 refresh token은 logout DB에서 비활성화 처리


- gitignore에 Secret 패키지 추가 : JWT_ISSUER와 JWT_SECRET_KEY가 담겨 있음. 


## 2022-05-05 진행상황
#### 1. 프로필 편집 API 개발
- Dto에 형식적 Validation 적용 : 이름, 닉네임 형식 확인 
    - PatchUserReq에 Validation 적용 : @Pattern 어노테이션 추가  
    - UserController에  Validation 적용 : @Valid, BindingResult 어노테이션 활용
- 닉네임 중복 검사
    - UserService : 입력받은 닉네임을 통해 DB에서 중복 닉네임 조회
    - UserDao : checkNickName() 함수 구현
- 프로필 정보 수정
    - UserService : 이름, 닉네임, 소개글, 이미지 값이 한개라도 존재하면 프로필 수정 함수 실행  
    - UserDao : modifyName(), modifyNickName(), modifyIntroduction(), modifyImage() 함수 구현
- Access Token을 통한 사용자 인가 구현
    - JwtService : validAccessToken() 메서드를 구현하여 Access Token의 유효성과 만료여부 확인   
    - UserController : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여


#### 2. 회원탈퇴 API 개발 
- Access Token을 통한 사용자 인가 적용
    - UserController : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여 
- 회원 탈퇴 로직 구현
    - UserService : checkdeleteUser()를 통한 회원탈퇴 여부 확인 및 회원탈퇴 적용
    - UserDao : DB에서 해당 유저 레코드의 status 칼럼을 ‘INACTIVE’로 변경하는 deleteUser() 함수 구현, 회원 탈퇴 여부 확인하는 checkdeleteUser 함수 구현
    
#### 3. 로그아웃 API 개발
- Access Token을 통한 사용자 인가 적용
    - UserController : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여 
- 로그아웃 로직 구현  ~~(+로그아웃 여부 확인)~~
    - UserService : 해당 유저의 Logout 레코드 모두 비활성화
    - LogoutDao : DB에서 해당 유저의 Logout 레코드의 status 칼럼을 ‘INACTIVE’로 변경하는 logout() 함수 구현

      <details>
        <summary>checkLogout()  함수 관련 이슈 발생</summary>
            <div markdown="1">
            <b> Issue </b> : postman으로 같은 user에 대한 로그아웃을 2번 진행시 로그아웃이 되지 않음.  <br> 
            <b> Problem </b> : 기존에 Logout 테이블의 해당 유저 인덱스의 status 칼럼이 ‘INACTIVE’으로 되어 있다면 같은 유저에 대해 로그아웃이 되지 않는 문제 발생   <br>
            <b> Solution </b> : 해결을 하려면  Select 쿼리문에 userId외에 refresh token까지 전달을 해야 로그아웃 여부 확인이 가능한데,  refresh token을 보안상 입력받을 필요는 없습니다. 그래서 로그아웃 여부 확인 구현은 일단 보류하였습니다.
            </div>
      </details>   



## 2022-05-06 진행상황
#### 1. Product 엔티티 설계
- product 테이블 
    - ERD 수정사항 : seller_name (판매자 이름) 칼럼을 user_id (사용자 인덱스) 외래키 칼럼으로 변경
- product 클래스 생성 : @Entity, @Table(name = "product"), @Column 등 활용
- productStatus 클래스 생성 : status 칼럼 표현을 위해 enum 형태로 지정  (INACTIVE가 0, ACTIVE가 1)
- productOption 클래스 생성 : @Entity, @Table(name = "product"), @Column, @ManyToOne(fetch = FetchType.LAZY) 등 활용

#### 2. 상품 등록 API 개발
- Dto에 형식적 Validation 적용
    - PostProductReq (DTO 클래스) 구현  : @Pattern, @Max, @Min 어노테이션 추가
    - PostProductOptionReq (DTO 클래스) 구현 
    - ProductController 구현 : @Valid, BindingResult 어노테이션 활용
- 상품 등록 로직 구현
    - ProductService 구현 
        - userDao.findById(userId) : 판매자 인덱스로 판매자 객체 불러오기 
        - productDao.save(productCreate) : 상품 DB에 상품 정보 저장
        - productDao.findById(productCreate.getId() : 상품 인덱스를 통해 상품 객체를 불러옴 
        - productOptionDao.save(productOptionCreate) : 상품 옵션 DB에 상품 옵션 개수별로 등록  
    - ProductDao 구현 
    - ProductOptionDao 구현 
    <details>
        <summary>save() 함수 관련 이슈 발생 </summary>
        <div markdown="1">
        <b> Issue </b> : List<String>타입의 optionName 변수를 postman으로 입력받아 for문을 통해 JPA의 save() 함수로 productOption 테이블에 입력시 Insert 쿼리가 2번이 들어가야 하는데 1번만 발생하고 Update 쿼리로 덮어씌워지는 문제가 발생  <br> 
        <b> Problem </b> : productOption 테이블에 같은 idx로 2번 save() 함수가 동작하여 Insert 문 다음에 Update문이 수행되는것이 원인이였습니다.   <br>       
        <b> Solution </b> :  List<ProductOption> 객체를 하나 더 만들어 for문을 통해 List에 객체를 담아준 후 saveAll() 함수로 처리하여 해결하였습니다. 
        </div>
    </details>         

## 2022-05-07 진행상황
#### 1. EC2에 API 첫 배포
- Prod서버 root 디렉터리 경로 설정 & 프록시 패스 설정
- TodayFruit_Clone_Server 프로젝트 Clone 후 .jar 파일 실행                    
            
            
## 2022-05-08 진행상황
#### 1. Access 토큰 재발급 API 구현    
- Access Token을 통한 사용자 인가 적용  
    - UserController 구현 : AccessToken의 경우 만료되지 않았을 경우 에러메시지 반환, 입력받은 RefreshToken에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여, 로그아웃 상태 확인
    - JwtService 구현 :  Refresh Token의 유효성과 만료여부를 확인하는 checkAccessToken() 함수와 Access Token의 만료상태를 확인하는 validRefreshToken() 함수 활용  

    <details>
        <summary> Access token 변조 및 만료여부 확인 관련 이슈 </summary>
        <div markdown="1">
        <b> Issue </b> : 비즈니스 로직상 AccessToken이 만료되지 않았을때 에러메시지가 발생하는데, AccessToken에 아무 값이나 넣게되면 만료된것과 같이 처리가 되어서 정상 로직으로 코드가 돌아갑니다.  <br> 
        <b> Problem </b> : checkAccessToken() 함수안의 Claims 클래스에서 예외가 발생하는 경우가 토큰이 만료되거나 토큰이 변조되는 경우가 모두 포함되기 떄문에 토큰 만료시 정상 로직으로 동작합니다.     <br>       
        <b> Solution </b> : AccessToken을 입력받지 않는것을 생각해 볼수 있는데, 그렇게 되면 AccessToken의 만료여부를 확인할 수 있는 방법이 없기 때문에 다른 방법을 찾아봐야 할것 같습니다.
        </div>
    </details>         

- Access token 재발급 (+로그아웃 상태 확인)  
    - UserService 구현 : RefreshToken을 통해 로그아웃 상태 확인 + AccessToken 재발급            
    - LogoutDao 구현 :  Logout Table에서 해당 RefreshToken 칼럼과 status 칼럼이 ‘INACTIVE’인 레코드 조회  
            
    <details>
        <summary> RefreshToken 보안 이슈 </summary>
        <div markdown="1">
        <b> Issue </b> : 프로필 편집 API에서 Accesstoken 값에 Refreshtoken 값을 넣어도 정상적으로 APi가 동작하는데,  만료시간이 긴 Refreshtoken이 탈취되어 Accesstoken으로도 활용된다면 인가 절차가 필요한 기능들에 무단으로 접근 가능합니다.   <br> 
        <b> Problem </b> : Accesstoken과 Refreshtoken을 생성시 같은 암호화 키를 사용하고 있었습니다.  <br>       
        <b> Solution </b> : Accesstoken과 Refreshtoken 토큰의 암호화 키를 다르게 줌으로써 해결완료 했습니다.   
        </div>
    </details> 
                        
#### 2. 사용자 인가 절차에 로그아웃 상태 확인 코드 추가                  
- 프로필 편집 API, 회원 탈퇴 API 등 사용자 인가가 구현된 모든 코드에 로그아웃 상태를 확인하는 코드 추가
- 기존 사용자 인가 절차에서는 로그아웃을 한 상태에서도 AccessToken이 만료되지 않았다면 주요 기능에 접근할 수 있었기 때문에 userId를 통해 Logout 테이블에서 해당 레코드의 status 칼럼 상태를 확인하여 로그아웃 여부를 알수있도록 코드를 구현하였습니다.  
            
            
## 2022-05-09 진행상황
#### 1. 상품 정보 수정 API 개발
- Dto에 형식적 Validation 적용  
    - 배송타입, 상품제목, 상품가격, 할인율,  판매수량, 배송일, 상품 옵션 인덱스, 상품 옵션 이름을 입력 받아 정규표현식 적용.배송타입, 상품제목, 상품가격, 할인율,  판매수량, 배송일, 상품 옵션 인덱스, 상품 옵션 이름을 입력 받아 정규표현식 적용.  
    - DTO의 멤버변수엔 enum 타입을 사용하면 안됩니다. (클라이언트를 고려하여 String 사용을 권장합니다.)  
    - @Size 어노테이션은 String 자료형만 가능하고 int형은 @Max @Min만 사용 가능  
    - PatchProductReq (DTO 클래스) 구현  : @Pattern, @Max, @Min 어노테이션 추가
    <details>
        <summary> @Min 어노테이션 관련 이슈 발생 </summary>
        <div markdown="1">
        <b> Issue </b> : 상품 정보 수정 API 로직이 한개의 변수씩 입력받아도 DB에 Update가 되도록 설계하였는데,  saleCount (판매수량) 변수를 필수로 입력하지 않으면 Validation 처리 메시지가 출력됨 (”판매수량 형식을 확인해 주세요. (최소 10개, 최대 3000개)”)   <br> 
        <b> Problem </b> : saleCount 변수 자체를 @Min(value = 10)으로 선언해 주었기 때문에  값이 없을떄 0으로 인식되어 자동으로 Validation 메시지가 출력됨.  <br>       
        <b> Solution </b> : @Min 어노테이션 대신 @Pattern 을 사용해야 할지 고심중에 있습니다.
        </div>
    </details> 
              
    - ProductController 구현 : @Valid, BindingResult 어노테이션 활용
    <details>
        <summary> deliveryType (배송타입) 형식 관련 이슈 발생 </summary>
        <div markdown="1">
        <b> Issue </b> : 배송타입 (”쿠배송” or “무료배송”) 변수 deliveryType를 DTO에서 enum 타입으로 입력받을수 없습니다.  <br> 
        <b> Problem </b> : 클라이언트는 enum type으로 변수를 보내지 않기 때문에 deliveryType을 String Type으로 변경이 필요하다고 생각되었습니다.   <br>       
        <b> Solution </b> :클라이언트와의 협업을 대비하여 기존 deliveryType의 enum 타입을 String 형식으로 변경하였습니다
        </div>
    </details> 
- 상품 정보 수정 로직 구현
    - ProductService 구현 : price와 discountRate의 경우는 같이 입력받아야 deiscountPrice가 계산되어 DB에 저장이 됩니다
    - ProductDao 구현 : 배송 타입 변경 / 상품 제목 변경 / 상품 가격 변경 / 상품 할인율 변경 / 상품 할인가격 변경 / 상품 판매수량 변경 / 상품 설명 변경 / 상품 배송일 변경 함수 구현    
    - ProductOptionDao 구현 : 상품 옵션 변경 함수 구현
            
            
- Access Token을 통한 사용자 인가 구현 (+로그아웃 상태 확인)
    - ProductController 구현 : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여 + 로그아웃 상태 확인            
            
            
#### 2. 전체 상품 조회 API 개발
- Dto 설계
    - GetProductsRes (DTO 클래스) 구현 : 1. 상품 인덱스, 배송타입, 상품제목, 상품가격, 할인율,  할인된 가격, 판매수량 변수 선언   (주문자 수는 추후 추가 예정)
- 전체상품 조회 로직 구현
    - ProductController 구현 
    - ProductService 구현 
    - ProductDao 구현 : 상품 정보를 조회하는 엔티티 기반의 쿼리문(JPQL) 작성 후 DTO로 반환        
    <details>
        <summary> discountPrice (할인 가격) 조회 관련 이슈 발생 </summary>
        <div markdown="1">
        <b> Issue </b> : 기존에는 price(상품가격)과 discountRate (할인율)을 입력받고 DB에 저장하여 discountPrice (상품 할인가격)를 쿼리문에서 계산을 통해 출력을 하려고 했으나. CASE THEN 절이 Entity 기반의 쿼리문(JPQL)에서 문법적 오류가 발생하였습니다  <br> 
        <b> Problem </b> : 구글링을 계속 해보았지만, 해답을 찾지못했습니다. 그래서 차선책을 생각해 보았습니다.  <br>       
        <b> Solution </b> : discountPrice (상품 할인가격)을 기존처럼 입력 받지는 않지만 Service 로직에서 수식을 통해 DB에 저장하는 방식으로 변경하였습니다. 
        </div>
    </details>             

            
#### 3. 특정 상품 조회 API 개발
- Dto 설계
    - GetProductsRes (DTO 클래스) 구현 : 상품 인덱스, 배송타입, 상품제목, 상품가격, 할인율,  할인된 가격, 판매수량,  상품 설명, 판매자 이름 변수 선언 (주문자 수는 추후 추가 예정)
    - GetProductRes (DTO 클래스) 구현
    - GetProductAndUserRes (DTO 클래스) 구현 : getProductRes 객체와 판매자 이름 변수 선언 
- 특정 상품 조회 구현
    - ProductController 구현  
    - ProductService 구현 
    - ProductDao 구현 : 상품 정보를 조회하는 엔티티 기반의 쿼리문(JPQL) 작성 후 DTO로 반환
           

## 2022-05-10 진행상황
#### 1. 상품 삭제 API 개발
- 상품 삭제 로직 구현
    - ProductService 구현 
    - ProductDao 구현 : 상품 정보를 삭제하는 deleteProductOption() 함수, 상품 옵션 정보를 삭제하는 deleteProduct() 함수 구현
    - ProductOptionDao 구현 : 상품 삭제 여부를 조회하는 checkStatusPrdouct() 함수 구현        
    - ProductDao 구현 : 상품 정보를 조회하는 엔티티 기반의 쿼리문(JPQL) 작성 후 DTO로 반환      
- Access Token을 통한 사용자 인가 구현 (+로그아웃 상태 확인)
    - ProductController 구현 : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여 + 로그아웃 상태 확인            

#### 2. 상품 옵션 조회 API 개발
- 상품 옵션 조회 로직 구현
    - ProductService 구현 
    - ProductOptionDao 구현 : 상품 옵션을 조회하는 getProductOptions() 함수 구현
             
            
#### 3. Purchase 엔티티 설계
- product 테이블 
    - ERD 수정사항 : 테이블명을 order에서 purchase로 변경
    - 테이블명 변경 이유 : order의 경우 SQL구문에서 사용되어 엔티티 기반의 DDL이 실행될시 오류가 발생하기 때문에 purchase로 테이블명 변경 
- Purchase 클래스 생성 : @Entity, @Table(name = "purchase"), @Column 등 활용
- PurchaseStatus 클래스 생성 : status 칼럼 표현을 위해 enum 형태로 지정  (INACTIVE가 0, ACTIVE가 1)

            
#### 4. 상품 구매 API 개발
- Dto 설계
    - PostPurchaseReq (DTO 클래스) 구현 : 구매개수와 배송지 변수를 선언하고+ 구매자, 상품, 상품 옵션은 인덱스를 입력받아 객체로 변환하기 위해 객체로 선언
    - PurchaseController 구현 : @Valid, BindingResult 어노테이션 활용
- 상품 구매 로직 구현
    - PurchaseService 구현  
        - checkStatusUser() 함수 : userId를 통해 사용자 객체 조회 후 DTO에 Input
        - checkStatusPrdouct() 함수 : productId를 통해 상품 객체 조회 후 DTO에 Input 
        - checkStatusPrdocutOption() 함수 : productOptionId를 통해 상품 옵션 객체 조회 후 DTO에 Input 
        - save()함수 활용 : DTO 객체에서 복사된 Purchase 객체를 DB에 저장  
- Access Token을 통한 사용자 인가 구현
    - PurchaseController : 입력받은 Access Token에서 userId 추출 후 입력받은 userId와 비교하여 사용자 접근 권한 부여 + 로그아웃 상태 확인
 <details>
    <summary> 포트 중복 실행 관련 이슈  </summary>
    <div markdown="1">
    <b> Issue </b> : .jar 파일을 실행하는 과정에서 9090번에 대해 포트 충돌이 발생하였습니다. <br> 
    <b> Problem </b> : 이미 9090포트로 프로세스가 실행중인 상태였습니다. <br>       
    <b> Solution </b> : 로컬에서 진행하였기 때문에 netstat -ano | findstr 9090 명령어러 프로세스 번호를 조회하고 taskkill /f /pid “프로세스 ID”를 입력하여 해당 프로세스를 종료 시켜줌으로써 해결되었습니다.
    </div>
 </details>             
            
