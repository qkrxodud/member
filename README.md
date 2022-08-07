# 사전과제 - 회원인증 서비스

## 목차
[1. 개발환경](#개발환경)

[2. 빌드 및 실행하기](#빌드-및-실행하기)

[3. 기능 요구사항](#기능-요구사항)

[4. API 작성 방법](#API-작성-방법)

-----------------------
## 개발환경
* 기본환경
  * IDE: Intellij IDEA
  * OS: window10
  * GIT
* Server
  * java11
  * Spring Boot 2.7.2
  * JPA
  * H2 1.4.2
  * Gradle
  * junit5
---
## 빌드 및 실행하기
### 터미널 환경
* Git, JAVA는 설치되었다고 가정
```
$ git clone https://github.com/qkrxodud/member.git
$ cd able
$ ./gradlew clean build
$ java -jar build/libs/review-0.0.1-SNAPSHOT.jar
```
---
## 데이터 베이스 설정
H2 데이터 베이스 설치 후 실행
* H2 데이터 베이스 설치 방법 : https://phantom.tistory.com/59
* JPA 기반으로 실행시 application.yml을 통해 자동으로 테이블이 생성됩니다.

---
## 기능 요구사항
### 회원 가입 기능
* 전화번호 인증 후 회원가입이 가능해야 합니다.
* 로그인 기능
  * 식별 가능한 모든 정보로 로그인이 가능해야 합니다.
  * 식별 가능한 모든 정보가 무엇인지는 스스로 판단하여 결정하시면 됩니다.
  * 예) 아이디 혹은 전화번호 + 비밀번호를 입력하면 로그인이 가능합니다.
* 내 정보 보기 기능
* 비밀번호 찾기 ( 재설정 ) 기능
  * 로그인 되어 있지 않은 상태에서 비밀번호를 재설정하는 기능입니다.
  * 전화번호 인증 후 비밀번호 재설정이 가능해야 합니다.
---

## API 작성 방법
### swagger 실행링크 : <http://localhost:8080/swagger-ui.html>
#### 회원인증 랜덤 번호 전송 request
1. ### message 클릭
2. /send-message 클릭
3. Try it out 클릭
4. Parameters -> 핸드폰 입력
5. Execute 클릭

#### 회원인증 랜덤 번호 전송 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "phoneNum": "01034047799",
    "successMsg": "정상 접수(이통사로 접수 예정) "
  }
}
```
---

#### 회원인증 request
1. message-controller 클릭
2. /check-message 클릭
3. Try it out 클릭
4. Parameters -> 핸드폰 입력, SMS 랜덤번호 작성
5. Execute 클릭
#### 회원인증 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "phoneNum": "01034047799",
    "successMsg": "200 OK"
  }
}
```
------
#### 회원가입 request
1. Sign/Login 클릭
2. /signup 클릭
3. Try it out 클릭
4. Example value 작성
5. Execute 클릭
```json
{
  "mail": "qkrxodud00@gmail.com",
  "name": "박태영",
  "nickName": "typark",
  "password": "1Q2w3e4r!",
  "phoneNumber": "01034047799"
}
```
#### 회원가입 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "userNo": 1,
    "roles": [
      "ROLE_USER"
    ],
    "createDateTime": "2022-08-06T20:15:54.6105949",
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ]
  }
}
```
------
### 이메일 로그인 request
1. Sign/Login 클릭
2. /login-email 클릭
3. Try it out 클릭
4. Parameters -> 이메일, 패스워드작성
5. Execute 클릭

### 이메일 로그인 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "userNo": 1,
    "roles": [
      "ROLE_USER"
    ],
    "createDateTime": "2022-08-06T20:19:32.8370619",
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY1OTc4NDc3MiwiZXhwIjoxNjU5Nzg4MzcyfQ.dizxwfr-qZuDEIpsZ-vmeCCtpESLtiX7krjMdXP52u8"
  }
}
```
------
### 핸드폰 로그인 request
1. Sign/Login 클릭
2. /login-phone 클릭
3. Try it out 클릭
4. Parameters -> 핸드폰, 패스워드작성
5. Execute 클릭

### 핸드폰 로그인 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "userNo": 1,
    "roles": [
      "ROLE_USER"
    ],
    "createDateTime": "2022-08-06T20:19:32.8370619",
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ],
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOlsiUk9MRV9VU0VSIl0sImlhdCI6MTY1OTc4NDc3MiwiZXhwIjoxNjU5Nzg4MzcyfQ.dizxwfr-qZuDEIpsZ-vmeCCtpESLtiX7krjMdXP52u8"
  }
}
```
------
### 로그인 후 회원조회 request
1. User 클릭
2. /api/findUser/{email} 클릭
3. Try it out 클릭
4. Parameters -> 로그인시 전달받은 X-AUTH-TOKEN 입력, email 입력
5. Execute 클릭

### 로그인 후 회원조회 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "mail": "qkrxodud00@gmail.com",
    "nickName": "typark",
    "name": "박태영",
    "phoneNum": "01034047799",
    "roles": [
      "ROLE_USER"
    ],
    "authorities": [
      {
        "authority": "ROLE_USER"
      }
    ]
  }
}
```
------
#### 비로그인 핸드폰인증 request
1. message-controller 클릭
2. /update-message 클릭
3. Try it out 클릭
4. Parameters -> 핸드폰 입력
5. Execute 클릭

#### 비로그인 핸드폰인증 response
```json
{
  "success": true,
  "code": 0,
  "msg": "성공하였습니다.",
  "data": {
    "phoneNum": "01034047799",
    "successMsg": "정상 접수(이통사로 접수 예정) "
  }
}
```

---
