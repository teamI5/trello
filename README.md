
# Trello Clone Project

팀원간의 협업을 효율적으로 수행할 수 있도록 하는 [Trello](https://trello.com/)를 Clone한 프로젝트

## 👨‍👩‍👧‍👦 Hello Introduce US


|유현식|정이삭|장기현|여준서|양혜민|
|:----:|:----:|:----:|:----:|:----:|
|![enter image description here](https://avatars.githubusercontent.com/u/176876846?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/106715800?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/109169177?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/148769872?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/117827625?v=4)|
|[@20240729](https://github.com/20240729)|[@golden-hamster](https://github.com/golden-hamster)|[@EtherXion](https://github.com/EtherXion)|[@duwnstj](https://github.com/duwnstj)|[@asitwas729](https://github.com/asitwas729)|
|WorkSpace, Board|CI/CD, Auth|Comment|List|Card|

<br>

### [💜 Let's Go Our GitHub](https://github.com/teamI5/trello)

<br>

## 📝 Technologies & Tools (BE) 📝

<img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
 <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/jpa-007396?style=for-the-badge&logo=jpa&logoColor=white"> 
<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Flyway_mysql-CC0200?style=for-the-badge&logo=Flyway&logoColor=white"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/> <img src="https://img.shields.io/badge/AmazonS3-569A31?style=for-the-badge&logo=AmazonS3&logoColor=white"/>  
 <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/> 
<img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> 
⭐ [Github Rules](https://github.com/likelion-project-README/README/wiki/%EC%BB%A4%EB%B0%8B-%EC%BB%A8%EB%B2%A4%EC%85%98)
⭐ [Code Convention](https://github.com/likelion-project-README/README/wiki/%EC%BD%94%EB%93%9C-%EC%BB%A8%EB%B2%A4%EC%85%98)

<br><br><br><br>


##  프로젝트 기능

### 🛡 Auth

> -   이메일 형식의 아이디와 비밀번호로 회원가입
> -   비밀번호 암호화 저장
> -   동일한 이메일로 중복 가입 방지
> -   탈퇴한 유저의 아이디(즉, 이메일) 재사용 방지

### 🛡 WorkSpace, Board

> -   회원가입시 부여받은 ADMIN권한으로만 생성 가능
> -   수정 권한이 있는 사용자(Read_Only 제외)는 멤버 초대 가능
> -  수정 권한이 있는 사용자(Read_Only 제외)만이 수정 가능
> -   권한(WorkSpace, Board, Read_Only)

### 🛡 WorkSpace invite User

> -   하나의 유저를 여러 사용자가 동시에 초대하는 경우 케이스 동시성 제어
> -   낙관적 락(예상 사용 빈도와 동시성 문제 발생 가능성 낮음)
> -  예외 발생시, 10회 시도 후 예외 메세지 반환

### 🛡 List

> -   Board내에서 List 순서 변경 가능
> -   수정 권한이 있는 사용자(Read_Only 제외)만이 수정 가능
> -   List 삭제 시, 연관된 Card도 모두 삭제됨

### 🔍 Card Search

> * JOIN FETCH를 사용하여 N+1문제 해결
> * INDEX를 활용하여 검색 기능 최적화 완료
> * Card의 제목, 설명, 마감일, 담당자로 검색가능

### 🔍 Comment

> * 수정 권한이 있는 사용자(Read_Only 제외)만이 댓글 생성 가능
> * 카드와 댓글, 사용자 아이디가 모두 일치할 경우, 댓글 변경 가능

### 🔍 Attached File

> * AWS S3로 첨부파일 관리
>  * 수정 권한이 있는 사용자(Read_Only 제외)만이 첨부파일 업로드및 삭제 가능
> * UUID로 첨부파일명 저장

### 🔍 Alerts

> * SLACK의 WEBHOOK API 사용
>  * JoinPoint(AOP)로 확장성 가짐


## 🚨 Trouble Shooting

#### [JPA N+1 문제]()


<br><br>


## 🌐 Architecture

![예시 아키텍쳐]()


## 📋 ERD Diagram

![ERD Diagram]()

<br>
