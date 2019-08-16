# 단축URL 서비스
## 프로젝트 소개 및 문제 해결 전략
 1. 프로젝트 소개
  - 이 프로젝트는 http:// 또는 https:// 구성된 긴 URL을 단축된 URL로 변환해서 제공하여 가독성과 사용의 편리성을 높여줍니다.

 2. 프로젝트 문제 해결 전략
  1) 난수번호 저장 폴더 및 파일
  ```
  File Folder = new File("C:\\temp");
  if(!Folder.exists()) {
			try {
				Folder.mkdir();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
  ```
  : C드라이브에 temp 폴더 여부를 체크하여 없을 시 생성하고 최초 변환 시, 파일 생성 및 저장합니다.
  
  2) URL Shortening Key 8 Character 생성
  ```
  private String checkDuplicateRandomStr() throws IOException {

   String strRndFinal = "";
   List<String> rndList= new ArrayList<String>();
   while(true) {
    // 1. 난수번호 10개 생성
    for( int i=0 ; i < 10 ; i++) {
     String str = "";
     List<String> strList = new ArrayList<String>();

     IntStream rndStrm = new Random().ints(8,65,90);
     rndStrm.mapToObj(item -> (char)item).forEach(item -> strList.add(String.valueOf((char)item)));
     for(String s:strList){ str += s; }

     rndList.add(str);			
    }

    for(String s : rndList) {
     lines = Files.lines(path);
     opStr = lines.filter(item -> item.contains(s)).findFirst();
     if(!opStr.isPresent()) {
      strRndFinal = s;
      break;
     }
    }

    if(!StringUtils.isEmpty(strRndFinal)) {
     break;
    }
   }
   return strRndFinal;
  }
  ```

## 프로젝트 개발 정보 및 환경 설정
  1. 프로젝트 개발 정보
    - jdk1.8 / sts-4.3.1.RELEASE / Maven project
    
  2. 환경 설정
    - 자바 설치 및 환경 변수 셋팅 URL : https://limkydev.tistory.com/61 참고 필수!

## 프로젝트 빌드 및 실행 방법
  1. 프로젝트 빌드 
    1) https://github.com/nicediem/simpleRedirect.git 소스 다운로드
    2) 프로젝트와 share project로 연결
    3) 빌드 및 소스 확인
     
  2. 프로젝트 실행
    1) 첨부된 simpleRedirect-0.0.1-SNAPSHOT.war 파일을 C드라이브에 복사
    2) C드라이브 폴더에서 alt + shift + 우측마우스 클릭 > '여기서 명령 창 열기(W)' 클릭
    3) 명령창에 java -jar simpleRedirect-0.0.1-SNAPSHOT.war 입력 후 엔터키 입력
    4) 크롬 OR 익스플로러 실행하여 http://localhost:8080/main 입력하면 초기 화면 나타남
 
