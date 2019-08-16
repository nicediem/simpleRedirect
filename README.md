# 단축URL 서비스
## 프로젝트 소개 및 문제 해결 전략
 1. 프로젝트 소개
  - 이 프로젝트는 http:// 또는 https:// 구성된 긴 URL을 단축된 URL로 변환해서 제공하여 가독성과 사용의 편리성을 높여줍니다.

 2. 프로젝트 문제 해결 전략
  1) 웹어플리케이션 개발을 위해 STS4 설치 및 셋팅
  2) 이클립스내 내장 Tomcat을 dependency 하여 실행가능한 war 파일 생성
  3) 

## 프로젝트 개발 정보 및 환경 설정
  1. 프로젝트 개발 정보
    - jdk1.8 / sts-4.3.1.RELEASE / Maven project
    
  2. 환경 설정
    - 자바 설치 및 환경 변수 셋팅 URL : https://limkydev.tistory.com/61 참고 필수!

## 프로젝트 빌드 및 실행 방법
  1. 프로젝트 빌드 
    1) Maven 프로젝트 생성
    2) Mahttps://github.com/nicediem/simpleRedirect.git 소스 다운로드
     
  2. 프로젝트 실행
    1) 첨부된 simpleRedirect-0.0.1-SNAPSHOT.war 파일을 C드라이브에 복사
    2) C드라이브 폴더에서 alt + shift + 우측마우스 클릭 > '여기서 명령 창 열기(W)' 클릭
    3) 명령창에 java -jar simpleRedirect-0.0.1-SNAPSHOT.war 입력 후 엔터키 입력
    4) 크롬 OR 익스플로러 실행하여 http://localhost:8080/main 입력하면 초기 화면 나타남
 
