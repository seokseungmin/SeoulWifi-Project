# 🌏 프로젝트 - 공공 와이파이

서울 공공 데이터 API를 이용해 자신의 위치 및 특정 위치에서 1km 내에 존재하는 공공 와이파이를 찾는 프로젝트

## ⚙ 기술 스택
- **언어:** Java
- **빌드 도구:** Maven
- **데이터베이스:** MariaDB
- **서버:** Tomcat 8.5
- **JDK:** JDK 1.8
- **웹 기술:** CSS, HTML5, JavaScript, Ajax, JSP
- **라이브러리:** 
  - junit
  - javax.servlet-api
  - javax.servlet.jsp-api
  - jstl
  - mariadb-java-client
  - json
  - gson
  - logback-classic

## ❗ 프로젝트 작동 순서 및 기능

1. **공공 와이파이 데이터 가져오기**
   - 서울시 공공 데이터 API를 활용하여 모든 공공 와이파이 정보를 가져옵니다.

2. **주변 와이파이 표시**
   - 사용자가 입력한 위치 좌표를 기반으로 주변 공공 와이파이 정보 최대 20개를 보여줍니다.

3. **검색 기록 관리**
   - 사용자가 입력한 위치 정보와 조회한 시점을 기준으로 검색 기록을 DB에 저장합니다.
   - 저장된 검색 기록을 조회할 수 있습니다.

4. **와이파이 상세 정보**
   - 특정 공공 와이파이의 상세 정보를 제공합니다.

5. **북마크 그룹 관리**
   - 사용자는 북마크 그룹을 생성하고, 그룹 목록을 확인하며, 그룹을 수정하거나 삭제할 수 있습니다.

6. **와이파이 북마크**
   - 사용자는 공공 와이파이 정보를 북마크에 추가하거나 삭제할 수 있습니다.