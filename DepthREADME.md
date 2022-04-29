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

#### 3. 웹서버 환경 구축 
- EC2 인스턴스 생성  : 기존 인스턴스 활용
- Nginx 서버 구축 
- 도메인 & HTTPS 적용 : 가비아에서 도메인 구입후 적용 (https://today-fruit.shop/)
    - 이슈 발생
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
- 서브 도메인 추가 : 
- dev.today-fruit.shop , prod.today-fruit.shop
- 리다이렉션 적용 (ip to domain)
- RDS 인스턴스 생성 : 기존 RDS (Mysql 8.0.26) 활용

#### 4. Spring boot 개발환경 구축
- Spring boot 프로젝트 생성 (+starter dependency 추가)
- Spring boot와 RDS 연동
- Spring boot 최초 빌드 및 실행 (Test API)
- 프로젝트 파일을 깃허브에 업로드
- EC2에서 Test API 실행













