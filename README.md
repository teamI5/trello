# Trello Clone Project
![enter image description here](https://appsumo2-cdn.appsumo.com/media/selfsubmissions/images/07b22af0-4412-4a50-8474-0446da605351.png?width=850&height=470)

![enter image description here](https://blog.kakaocdn.net/dn/KBEDi/btrmCBTaC4s/96iKK3eOL0WagUAS2aFQak/img.png)
<br><br>

## 👨‍👩‍👧‍👦 Hello Introduce US


|유현식|정이삭|장기현|여준서|양혜민|
|:----:|:----:|:----:|:----:|:----:|
|![enter image description here](https://avatars.githubusercontent.com/u/176876846?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/106715800?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/109169177?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/148769872?v=4)|![enter image description here](https://avatars.githubusercontent.com/u/117827625?v=4)|
|[@20240729](https://github.com/20240729)|[@golden-hamster](https://github.com/golden-hamster)|[@EtherXion](https://github.com/EtherXion)|[@duwnstj](https://github.com/duwnstj)|[@asitwas729](https://github.com/asitwas729)|
|WorkSpace, Board|CI/CD, Auth|Comment|List|Card|

<br>

### [💜 Let's Go Our GitHub](https://github.com/teamI5/trello)

<br>

## 📝 Technologies & Tools 📝

<img src="https://img.shields.io/badge/Java-17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> <img src="https://img.shields.io/badge/SpringBoot-3.3.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white"/> <img src="https://img.shields.io/badge/jpa-007396?style=for-the-badge&logo=jpa&logoColor=white"> 

<img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white"/> <img src="https://img.shields.io/badge/Flyway_mysql-CC0200?style=for-the-badge&logo=Flyway&logoColor=white"/> <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/> 

<img src="https://img.shields.io/badge/Amazon_EC2-FF9900?style=for-the-badge&logo=Amazon_EC2&logoColor=white"/>  <img src="https://img.shields.io/badge/Amazon_S3-569A31?style=for-the-badge&logo=Amazon_S3&logoColor=white"/>  

 <img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/> <img src="https://img.shields.io/badge/github_action-2088FF?style=for-the-badge&logo=github_action&logoColor=white"/> <img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white"/> 
 
<img src="https://img.shields.io/badge/IntelliJ_IDEA-000000?style=for-the-badge&logo=IntelliJIDEA&logoColor=white"/>  <img src="https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=Postman&logoColor=white"/>  <img src="https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white"/> <img src="https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=slack&logoColor=white"/> 

⭐ [Github Rules](https://github.com/teamI5/trello/wiki/Github-Rules)

⭐ [Code Convention](https://github.com/teamI5/trello/wiki/Code-Convention)

<br><br>


##  프로젝트 기능

### 🐇 Auth

> -   이메일 형식의 아이디와 비밀번호로 회원가입
> -   비밀번호 암호화 저장
> -   동일한 이메일로 중복 가입 방지
> -   탈퇴한 유저의 아이디(즉, 이메일) 재사용 방지

<br>

### 🍀 WorkSpace, Board

> -   회원가입시 부여받은 ADMIN권한으로만 생성 가능
> -   수정 권한이 있는 사용자(Read_Only 제외)는 멤버 초대 가능
> -  수정 권한이 있는 사용자(Read_Only 제외)만이 수정 가능
> -   권한(WorkSpace, Board, Read_Only)

<br>

### ⛓️ WorkSpace invite User

> -   하나의 유저를 여러 사용자가 동시에 초대하는 경우 케이스 동시성 제어
> -   낙관적 락(예상 사용 빈도와 동시성 문제 발생 가능성 낮음)
> -  예외 발생시, 10회 시도 후 예외 메세지 반환

<br>

### 📚 List

> -   Board내에서 List 순서 변경 가능
> -   수정 권한이 있는 사용자(Read_Only 제외)만이 수정 가능
> -   List 삭제 시, 연관된 Card도 모두 삭제됨

<br>

### 🔍 Card Search

> * JOIN FETCH를 사용하여 N+1문제 해결
> * INDEX를 활용하여 검색 기능 최적화 완료
> * Card의 제목, 설명, 마감일, 담당자로 검색가능

<br>

### 🔥 Comment

> * 수정 권한이 있는 사용자(Read_Only 제외)만이 댓글 생성 가능
> * 카드와 댓글, 사용자 아이디가 모두 일치할 경우, 댓글 변경 가능

<br>

### 📁 Attached File

> * AWS S3로 첨부파일 관리
>  * 수정 권한이 있는 사용자(Read_Only 제외)만이 첨부파일 업로드및 삭제 가능
> * UUID로 첨부파일명 저장

<br>

### 🛎️ Alerts

> * SLACK의 WEBHOOK API 사용
>  * JoinPoint(AOP)로 확장성 가짐






<br><br>
## 🚨 Trouble Shooting

#### ✨ [리스트 순서 변경 개선사항](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-%EB%A6%AC%EC%8A%A4%ED%8A%B8-%EC%88%9C%EC%84%9C-%EB%B3%80%EA%B2%BD-%EA%B0%9C%EC%84%A0%EC%82%AC%ED%95%AD)

#### ✨ [IntelliJ import 오류](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-IntelliJ-import-%EC%98%A4%EB%A5%98)


#### ✨ [첨부파일 구현시 S3와 연결 오류](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-%EC%B2%A8%EB%B6%80%ED%8C%8C%EC%9D%BC-%EA%B5%AC%ED%98%84%EC%8B%9C-S3%EC%99%80-%EC%97%B0%EA%B2%B0-%EC%98%A4%EB%A5%98)

#### ✨ [단건 조회시 예외처리 불명확](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-%EB%8B%A8%EA%B1%B4-%EC%A1%B0%ED%9A%8C%EC%8B%9C-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC-%EB%B6%88%EB%AA%85%ED%99%95)

#### ✨ [여러 사용자가 동일한 카드를 수정할때, 동시성 문제](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-%EC%97%AC%EB%9F%AC-%EC%82%AC%EC%9A%A9%EC%9E%90%EA%B0%80-%EB%8F%99%EC%9D%BC%ED%95%9C-%EC%B9%B4%EB%93%9C%EB%A5%BC-%EC%88%98%EC%A0%95%ED%95%A0%EB%95%8C,-%EB%8F%99%EC%8B%9C%EC%84%B1-%EB%AC%B8%EC%A0%9C)

#### ✨ [카드 검색 속도 저하](https://github.com/teamI5/trello/wiki/%5BTrouble-Shooting%5D-%EC%B9%B4%EB%93%9C-%EA%B2%80%EC%83%89-%EC%86%8D%EB%8F%84-%EC%A0%80%ED%95%98)


<br><br>


## 🌐 Architecture

![Service Architecture](https://github.com/user-attachments/assets/7f80be7b-09c4-40b9-a4b6-e66d0da3a49c)

<br><br>

## 📋 ERD Diagram

![erd](https://github.com/user-attachments/assets/576aec74-5364-4698-b83f-a1dd37a6aec5)


<br><br>

## 🚨 프로젝트 구조
```
│  README.md
│  .gitignore
│  appspec.yml
│  build.gradle
│  Dockerfile
│  gradlew
│  gradlew.bat
│  settings.gradle
│
│
└─src
    ├─main
    │  ├─java
    │  │  └─com
    │  │      └─sparta
    │  │          └─trellocopy
    │  │              │  TrellocopyApplication.java
    │  │              │
    │  │              ├─config
    │  │              │      JwtAuthenticationToken.java
    │  │              │      JwtSecurityFilter.java
    │  │              │      JwtUtil.java
    │  │              │      PersistenceConfig.java
    │  │              │      S3Config.java
    │  │              │      SecurityConfig.java
    │  │              │
    │  │              └─domain
    │  │                  ├─board
    │  │                  │  ├─controller
    │  │                  │  │      BoardController.java
    │  │                  │  │
    │  │                  │  ├─dto
    │  │                  │  │      BoardRequest.java
    │  │                  │  │      BoardResponse.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      Board.java
    │  │                  │  │
    │  │                  │  ├─exception
    │  │                  │  │      BoardNotFoundException.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      BoardRepository.java
    │  │                  │  │
    │  │                  │  └─service
    │  │                  │          BoardService.java
    │  │                  │
    │  │                  ├─card
    │  │                  │  ├─controller
    │  │                  │  │      CardController.java
    │  │                  │  │
    │  │                  │  ├─dto
    │  │                  │  │  ├─req
    │  │                  │  │  │      AddCardUserRequest.java
    │  │                  │  │  │      CardSaveRequest.java
    │  │                  │  │  │      CardSearchRequest.java
    │  │                  │  │  │      CardSimpleRequest.java
    │  │                  │  │  │
    │  │                  │  │  └─res
    │  │                  │  │          CardDetailResponse.java
    │  │                  │  │          CardSimpleResponse.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      Card.java
    │  │                  │  │
    │  │                  │  ├─exception
    │  │                  │  │      CardForbiddenException.java
    │  │                  │  │      CardNotFoundException.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      CardRepository.java
    │  │                  │  │
    │  │                  │  └─service
    │  │                  │          CardService.java
    │  │                  │
    │  │                  ├─comment
    │  │                  │  ├─Controller
    │  │                  │  │      CommentController.java
    │  │                  │  │
    │  │                  │  ├─Dto
    │  │                  │  │      CommentRequestDto.java
    │  │                  │  │      CommentResponseDto.java
    │  │                  │  │      CommentSaveRequestDto.java
    │  │                  │  │      CommentSaveResponseDto.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      Comment.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      CommentRepository.java
    │  │                  │  │
    │  │                  │  └─Service
    │  │                  │          CommentService.java
    │  │                  │
    │  │                  ├─common
    │  │                  │  ├─aop
    │  │                  │  │      Alarm.java
    │  │                  │  │      AlarmAspect.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      Timestamped.java
    │  │                  │  │
    │  │                  │  └─exception
    │  │                  │          BadRequestException.java
    │  │                  │          ForbiddenException.java
    │  │                  │          NotFoundException.java
    │  │                  │          UnauthorizedException.java
    │  │                  │
    │  │                  ├─file
    │  │                  │  ├─controller
    │  │                  │  │      FileController.java
    │  │                  │  │
    │  │                  │  ├─dto
    │  │                  │  │      FileDto.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      File.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      FileRepository.java
    │  │                  │  │
    │  │                  │  └─service
    │  │                  │          FileService.java
    │  │                  │
    │  │                  ├─list
    │  │                  │  ├─controller
    │  │                  │  │      ListController.java
    │  │                  │  │
    │  │                  │  ├─dto
    │  │                  │  │  ├─request
    │  │                  │  │  │      ListSaveRequest.java
    │  │                  │  │  │      ListUpdateRequest.java
    │  │                  │  │  │
    │  │                  │  │  └─response
    │  │                  │  │          ListSaveResponse.java
    │  │                  │  │          ListUpdateResponse.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      Lists.java
    │  │                  │  │
    │  │                  │  ├─exception
    │  │                  │  │      ListNotFoundException.java
    │  │                  │  │      ListNotInWorkSpaceException.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      ListRepository.java
    │  │                  │  │
    │  │                  │  └─service
    │  │                  │          ListService.java
    │  │                  │
    │  │                  ├─user
    │  │                  │  ├─controller
    │  │                  │  │      AuthController.java
    │  │                  │  │      UserController.java
    │  │                  │  │
    │  │                  │  ├─dto
    │  │                  │  │  │  AuthUser.java
    │  │                  │  │  │
    │  │                  │  │  ├─request
    │  │                  │  │  │      GrantRequest.java
    │  │                  │  │  │      LoginRequest.java
    │  │                  │  │  │      UserJoinRequest.java
    │  │                  │  │  │      WithdrawRequest.java
    │  │                  │  │  │
    │  │                  │  │  └─response
    │  │                  │  │          LoginResponse.java
    │  │                  │  │          UserJoinResponse.java
    │  │                  │  │          UserResponse.java
    │  │                  │  │          WorkspaceUserResponse.java
    │  │                  │  │
    │  │                  │  ├─entity
    │  │                  │  │      CardUser.java
    │  │                  │  │      User.java
    │  │                  │  │      UserRole.java
    │  │                  │  │      WorkspaceRole.java
    │  │                  │  │      WorkspaceUser.java
    │  │                  │  │
    │  │                  │  ├─exception
    │  │                  │  │      CardUserAlreadyExistsException.java
    │  │                  │  │      CardUserNotFoundException.java
    │  │                  │  │      DuplicateUserException.java
    │  │                  │  │      InvalidPasswordException.java
    │  │                  │  │      UserNotFoundException.java
    │  │                  │  │      WithdrawnUserException.java
    │  │                  │  │      WorkspaceRoleForbiddenException.java
    │  │                  │  │      WorkspaceUserNotFoundException.java
    │  │                  │  │
    │  │                  │  ├─repository
    │  │                  │  │      CardUserRepository.java
    │  │                  │  │      UserRepository.java
    │  │                  │  │      WorkspaceUserRepository.java
    │  │                  │  │
    │  │                  │  └─service
    │  │                  │          AuthService.java
    │  │                  │          UserService.java
    │  │                  │
    │  │                  └─workspace
    │  │                      ├─controller
    │  │                      │      WorkspaceController.java
    │  │                      │
    │  │                      ├─dto
    │  │                      │      WorkspaceRequest.java
    │  │                      │      WorkspaceResponse.java
    │  │                      │
    │  │                      ├─entity
    │  │                      │      Workspace.java
    │  │                      │
    │  │                      ├─exception
    │  │                      │      WorkspaceForbiddenException.java
    │  │                      │      WorkspaceNotFoundException.java
    │  │                      │
    │  │                      ├─repository
    │  │                      │      WorkspaceRepository.java
    │  │                      │
    │  │                      └─service
    │  │                              WorkspaceService.java
    │  │
    │  └─resources
    │      └─db
    │          └─migration
    │                  V1__init.sql
    │                  V2__add_file.sql
    │                  V3__add_timestamp_columns.sql
    │
    └─test
        └─java
            └─com
                └─sparta
                    └─trellocopy
                        │  TrellocopyApplicationTests.java
                        │
                        ├─comment
                        │      CommentServiceTest.java
                        │
                        └─domain
                            └─card
                                ├─controller
                                │      CardControllerTest.java
                                │
                                └─service
                                        CardServiceTest.java
                                        CardServiceWithLockTest.java
```
