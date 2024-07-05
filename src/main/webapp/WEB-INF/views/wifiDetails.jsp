<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.model.WifiInfo" %>
<%@ page import="com.example.repository.WifiInfoRepository" %>
<!DOCTYPE html>
<html>
<head>
    <title>WIFI 상세 정보</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f9f9f9;
            margin: 0;
            padding: 20px;
        }
        h1 {
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
            background-color: #fff;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #45a049;
            color: black;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        tr:hover {
            background-color: #ddd;
        }
        button {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        button:hover {
            background-color: #45a049;
        }
        select {
            padding: 10px;
            border-radius: 4px;
            border: 1px solid #ddd;
            margin-right: 10px;
        }
        a {
            color: #1a73e8;
            text-decoration: none;
            font-weight: bold;
            display: inline-block;
            margin-top: 20px;
        }
        a:hover {
            text-decoration: underline;
        }
        .form-container {
            margin-bottom: 20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script>
    function addBookmark(wifiId) {
        var groupId = $('#bookmarkGroup').val();
        if (groupId === "") {
            alert("북마크 그룹을 선택하세요.");
            return;
        }
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
                bookmarkGroupSelect.append('<option value="">북마크 그룹 선택:</option>');
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
<div class="form-container">
    <select id="bookmarkGroup">
        <!-- 옵션은 AJAX 요청 후 동적으로 추가됩니다. -->
        <option value="">북마크 그룹 이름 선택</option>
    </select>
    <button onclick="addBookmark(<%= wifi.getId() %>)">북마크 추가하기</button>
    <button onclick="goHome()">홈</button>
</div>
<table>
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
