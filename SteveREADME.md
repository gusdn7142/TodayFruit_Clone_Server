# 스티브의 개발일지

## 2022년 4월 27일 

### 도메인 선정
    - 각 화면별 무슨 도메인을 써야할지 써보았다.


## 2022월 04월 28일

### ERD 설계 

    Aquery tool로 협업을 함
    - 스티브 담당 도메인: POST, 주문, 검색, 사용자, 팔로우,  답글
    - 검색 같은 경우 어떻게 구현할지 고민해 봐야겠다. 
    - POST 테이블 같은 경우도 최선의 방법을 찾아야 한다. (팀장이 작성자 id를 언급했는데 그게 PK를 의미하시는 건지 물어봐야 겠다.)
### 문제점

    - 검색이랑 POST 부분을 더 생각해야 될 것 같다.
    - 검색을 어떻게 구현해야 할지 ERD 설계상으로 감이 안온다. ( 단순한 테이블 구현은 아닌거 같다)
    - POST는 지적 받은 부분을 뭔지 구체적으로 알고 해결해가야겠다.

## 2022월 04월 29일

### ERD 설계 + API 명세서

    - ERD 설계한 것을 오늘 1~2시간 본 것 같다. 
    - API를 추가할 것이 몇개 보였다. (예를들면 상품별로 카테고리를 세분화 하는 것)
    - 팀장과 협의 끝에 단순화하여 카테고리를 세분화 하지 말자고 하였다.
    - API 경우 사용자별 POST(게시물)도 조회하면 좋겠다는 생각을 하였다.
    
    - dev, prod , local을 어떻게 활용하면 좋을지 생각해 보았다.
        dev: API를 팀 단위로 테스트 하는 서버로 두면 좋을 것 같다.
        prod: 실제 API를 배포하는 서버로 두면 좋을 것 같다.
        local: API를 개발할 떄 개인적으로 개발하는 서버로 두면 좋을 것 같다.
### 문제점
    - ERD와 API를 확인하는데 많은 효율을 내지 못하는 것 같다. 
    - 오늘의 느낀점을 조금 추가하면 힘들고 지치고 하지만 주워진 환경에서 한 걸음이라도 더 내딛자!! 
    
## 2022월 04월 30일

### JPA 공부
    - JPA 대해 공부하였다.
    
## 2022월 05월 01일

### JPA 공부
    - JPA 대해 공부하였다.

## 2022월 05월 02일

### JPA 공부
    - JPA 대해 공부하였다.
    
## 2022월 05월 03일

### JPA 공부
    - JPA 대해 공부하였다.
    
## 2022월 05월 04일

### JPA 공부
    - JPA 대해 공부하였다.    
    
## 2022월 05월 05일

### JPA 공부
    - JPA 대해 공부하였다.    

## 2022월 05월 06일

### JPA 공부
    - JPA 대해 공부하였다.    

## 2022월 05월 07일

### JPA 공부
    - JPA 대해 공부하였다.   

## 2022월 05월 08일

### JPA 공부
    - JPA 대해 공부하였다.  

## 2022월 05월 09일

### JPA 공부
    - 오전에 JPA 공부, Product 테이블 파악
    - 오후에 JPA 공부, Post 테이블 만들기 - Postmapping 
    
## 2022월 05월 10일

### JPA 공부
    - Post 테이블 만들기 - Postmapping 
    
## 2022월 05월 11일

### JPA 공부
    - Post 테이블 만들기 - Postmapping 

## 2022월 05월 12일

### 특정 게시물 조회 API
    - Post 테이블 만들기 - Postmapping 
    
## 2022월 05월 13일

### 특정 게시물 조회 API
    - Post 테이블 만들기 - Postmapping
