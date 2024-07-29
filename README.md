# 🌏 프로젝트 - 공공 와이파이

> 서울 공공 데이터 API를 이용해 자신의 위치 및 특정 위치에서 주변에 존재하는 공공 와이파이를 찾는 프로젝트


🏙️ [**서울공공 와이파이 API**](https://data.seoul.go.kr/dataList/OA-20883/S/1/datasetView.do) <br>
📹 [**프로젝트 시연 영상**](https://youtu.be/2O3NiKBiteI)

![Front-end](https://skillicons.dev/icons?i=idea,java,maven,git)<br>

## ⚙ 기술 스택
- **🖥️ 언어:** Java
- **🛠️ 빌드 도구:** Maven
- **💾 데이터베이스:** MariaDB
- **🌐 서버:** Tomcat 8.5
- **☕ JDK:** JDK 1.8
- **🌐 웹 기술:** CSS, HTML5, JavaScript, Ajax, JSP
- **📚 라이브러리:** 
  - ✅ junit
  - ✅ javax.servlet-api
  - ✅ javax.servlet.jsp-api
  - ✅ jstl
  - ✅ mariadb-java-client
  - ✅ json
  - ✅ gson
  - ✅ logback-classic

## ❗ 프로젝트 작동 순서 및 기능

1. **📥 공공 와이파이 데이터 가져오기**
   - 서울시 공공 데이터 API를 활용하여 모든 공공 와이파이 정보를 가져옵니다.

2. **📍 주변 와이파이 표시**
   - 사용자가 입력한 위치 좌표를 기반으로 주변 공공 와이파이 정보 최대 20개를 보여줍니다.

3. **📝 검색 기록 관리**
   - 사용자가 입력한 위치 정보와 조회한 시점을 기준으로 검색 기록을 DB에 저장합니다.
   - 저장된 검색 기록을 조회할 수 있습니다.

4. **ℹ️ 와이파이 상세 정보**
   - 특정 공공 와이파이의 상세 정보를 제공합니다.

5. **📑 북마크 그룹 관리**
   - 사용자는 북마크 그룹을 생성하고, 그룹 목록을 확인하며, 그룹을 수정하거나 삭제할 수 있습니다.

6. **⭐ 와이파이 북마크**
   - 사용자는 공공 와이파이 정보를 북마크에 추가하거나 삭제할 수 있습니다.

## ❗DDL

```sql
CREATE TABLE `wifiinfo` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`mgrNo` varchar(50) DEFAULT NULL,
`wrdofc` varchar(50) DEFAULT NULL,
`mainNm` varchar(100) DEFAULT NULL,
`adres1` varchar(100) DEFAULT NULL,
`adres2` varchar(100) DEFAULT NULL,
`instlFloor` varchar(50) DEFAULT NULL,
`instlTy` varchar(50) DEFAULT NULL,
`instlMby` varchar(50) DEFAULT NULL,
`svcSe` varchar(50) DEFAULT NULL,
`cmcwr` varchar(50) DEFAULT NULL,
`cnstcYear` varchar(50) DEFAULT NULL,
`inoutDoor` varchar(50) DEFAULT NULL,
`remars3` varchar(100) DEFAULT NULL,
`lat` double DEFAULT NULL,
`lnt` double DEFAULT NULL,
`workDttm` varchar(50) DEFAULT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25576 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `searchhistory` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`lat` double DEFAULT NULL,
`lnt` double DEFAULT NULL,
`searchTime` timestamp NULL DEFAULT current_timestamp(),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=98 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci

CREATE TABLE `bookmark` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`wifiId` int(11) DEFAULT NULL,
`groupId` int(11) DEFAULT NULL,
`createdAt` timestamp NULL DEFAULT current_timestamp(),
`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
PRIMARY KEY (`id`),
KEY `groupId` (`groupId`),
KEY `wifiId` (`wifiId`),
CONSTRAINT `bookmark_ibfk_1` FOREIGN KEY (`wifiId`) REFERENCES `wifiinfo` (`id`),
CONSTRAINT `bookmark_ibfk_2` FOREIGN KEY (`groupId`) REFERENCES `bookmarkgroup` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci

CREATE TABLE `bookmarkgroup` (
`id` int(11) NOT NULL AUTO_INCREMENT,
`groupName` varchar(255) NOT NULL,
`sortOrder` int(11) NOT NULL,
`createdAt` timestamp NULL DEFAULT current_timestamp(),
`updatedAt` timestamp NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
```
