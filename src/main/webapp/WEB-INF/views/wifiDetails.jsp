<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.WifiInfo" %>
<%@ page import="com.example.repository.WifiInfoRepository" %>
<!DOCTYPE html>
<html>
<head>
    <title>WIFI 상세 정보</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function addBookmark(wifiId) {
        var groupId = $('#bookmarkGroup').val();
        $.ajax({
            url: 'wifi',
            type: 'post',
            data: {
                action: 'addBookmark',
                wifiId: wifiId,
                groupId: groupId
            },
            success: function (response) {
                alert('북마크가 추가되었습니다.');
            },
            error: function () {
                alert('오류가 발생했습니다.');
            }
        });
    }

    function loadBookmarkGroups() {
        $.ajax({
            url: 'wifi',
            type: 'get',
            data: {
                action: 'getBookmarkGroupOptions'
            },
            success: function (data) {
                var bookmarkGroupSelect = $('#bookmarkGroup');
                bookmarkGroupSelect.empty();
                $.each(data, function (index, group) {
                    bookmarkGroupSelect.append($('<option>', {
                        value: group.id,
                        text: group.groupName
                    }));
                });
            },
            error: function () {
                alert('북마크 그룹 데이터를 가져오는 중 오류가 발생했습니다.');
            }
        });
    }

    $(document).ready(function() {
        loadBookmarkGroups();
    });

    function goHome() {
        window.location.href = 'index.jsp';
    }
</script>
<body>
<%
    long id = Long.parseLong(request.getParameter("id"));
    WifiInfoRepository wifiRepo = new WifiInfoRepository();
    WifiInfo wifi = null;
    try {
        wifi = wifiRepo.findWifiById(id);
    } catch (Exception e) {
        e.printStackTrace();
    }
%>
<h1>WIFI 상세 정보</h1>
<div>
    <label for="bookmarkGroup">북마크 그룹 선택:</label>
    <select id="bookmarkGroup">
        <!-- 옵션은 AJAX 요청 후 동적으로 추가됩니다. -->
    </select>
    <button onclick="addBookmark(<%= wifi.getId() %>)">북마크 추가하기</button>
    <button onclick="goHome()">홈</button>
</div>
<table border="1">
    <tr>
        <th>거리(KM)</th>
        <td><%= wifi.getDistance() %></td>
    </tr>
    <tr>
        <th>관리번호</th>
        <td><%= wifi.getMgrNo() %></td>
    </tr>
    <tr>
        <th>자치구</th>
        <td><%= wifi.getWrdofc() %></td>
    </tr>
    <tr>
        <th>와이파이명</th>
        <td><%= wifi.getMainNm() %></td>
    </tr>
    <tr>
        <th>도로명주소</th>
        <td><%= wifi.getAdres1() %></td>
    </tr>
    <tr>
        <th>상세주소</th>
        <td><%= wifi.getAdres2() %></td>
    </tr>
    <tr>
        <th>설치위치(층)</th>
        <td><%= wifi.getInstlFloor() %></td>
    </tr>
    <tr>
        <th>설치유형</th>
        <td><%= wifi.getInstlTy() %></td>
    </tr>
    <tr>
        <th>설치기관</th>
        <td><%= wifi.getInstlMby() %></td>
    </tr>
    <tr>
        <th>서비스구분</th>
        <td><%= wifi.getSvcSe() %></td>
    </tr>
    <tr>
        <th>망종류</th>
        <td><%= wifi.getCmcwr() %></td>
    </tr>
    <tr>
        <th>설치년도</th>
        <td><%= wifi.getCnstcYear() %></td>
    </tr>
    <tr>
        <th>실내외구분</th>
        <td><%= wifi.getInoutDoor() %></td>
    </tr>
    <tr>
        <th>WIFI접속환경</th>
        <td><%= wifi.getRemars3() %></td>
    </tr>
    <tr>
        <th>X좌표</th>
        <td><%= wifi.getLat() %></td>
    </tr>
    <tr>
        <th>Y좌표</th>
        <td><%= wifi.getLnt() %></td>
    </tr>
    <tr>
        <th>작업일자</th>
        <td><%= wifi.getWorkDttm() %></td>
    </tr>
</table>
</body>
</html>
