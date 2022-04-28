## 2022-04-27 진행상황
#### 1. '오늘과일' 서비스 도메인 분석 (100% 완료)
    - 도메인 파악 : SNS, Product 과일, 장바구니. 주문 , 알림, 검색, 사용자, 상품 후기, 팔로우, 좋아요
    - 도메인 분석 : [개발일지](링크 추가 예정)를 참고해 주시면 감사합니다.      

## 2022-04-28 진행상황
#### 1. '오늘과일' 서비스 ERD 설계 (70% 완료)
- ERD 설계 전 [MySQL DB 명명 규칙](https://velog.io/@ohsol/MySQL-DB-%EB%AA%85%EB%AA%85-%EA%B7%9C%EC%B9%99-%EC%A0%95%EB%A6%AC)  재확인
- [ERD 구조도](https://drive.google.com/file/d/1rR1QeKPXdiCvhH1K4VlrKqYN3tTJwhd5/view) 작성 완료
- ERD 설계 (진행 중)
    - DB 유형 : Mysql 
    - 담당 테이블
        - 뎁스 : 상품(과일), 장바구니, 알림, 상품 후기, 좋아요, 댓글  
        - 스티브 : 게시물(sns), 주문, 검색, 사용자, 팔로우,  답글 
    - ERD 설계도 확인 : [Aqeury tool 링크](https://aquerytool.com/aquerymain/index/?rurl=28b4d08a-e25b-40ff-8221-5bc99bffb2fb&)  (비밀번호 : kn348k) 
    - 완료된 사항 : 모든 테이블 설계 완료 (총 15개), 외래키 연결 완료
    - 부족한 사항 : varchar() 자료형 크기를 칼럼마다 적절히 조절, Null 허용 가능한 칼럼 파악, default 값이 필요한 칼럼 확인  
#### 2. '오늘과일' 서비스 API 리스트업 (70% 완료)
- API 명세서에 현재까지 49개 URI 작성 완료...
