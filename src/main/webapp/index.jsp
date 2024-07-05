<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
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
        nav {
            margin-bottom: 20px;
        }
        nav a {
            margin-right: 15px;
            text-decoration: none;
            color: #1a73e8;
            font-weight: bold;
        }
        nav a:hover {
            text-decoration: underline;
        }
        form {
            margin-bottom: 20px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
        label {
            margin-right: 10px;
            font-weight: bold;
        }
        input[type="text"] {
            margin-right: 10px;
            padding: 5px;
            border: 1px solid #ddd;
            border-radius: 4px;
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
        #result {
            margin-top: 20px;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function getLocation() {
            if (navigator.geolocation) {
                navigator.geolocation.getCurrentPosition(showPosition);
            } else {
                alert("Geolocation is not supported by this browser.");
            }
        }

        function showPosition(position) {
            document.getElementById("lat").value = position.coords.latitude;
            document.getElementById("lnt").value = position.coords.longitude;
        }

        function getNearestWifi() {
            const lat = $('#lat').val();
            const lnt = $('#lnt').val();
            $.ajax({
                url: 'wifi',
                type: 'get',
                data: {
                    action: 'getNearest',
                    lat: lat,
                    lnt: lnt
                },
                success: function (data) {
                    console.log('근처 와이파이 20개 데이터를 성공적으로 가져왔습니다.');
                    $('#result').html(data);
                },
                error: function () {
                    console.log('근처 와이파이 20개 데이터를 가져오는 중 오류가 발생했습니다.');
                    alert('위치 정보를 입력한 후에 조회해 주세요.');
                }
            });
        }

        function getHistory() {
            $.ajax({
                url: 'wifi',
                type: 'get',
                data: {
                    action: 'getHistory'
                },
                success: function (data) {
                    console.log('히스토리 데이터를 성공적으로 가져왔습니다.');
                    $('#result').html(data);
                },
                error: function () {
                    console.log('히스토리 데이터를 가져오는 중 오류가 발생했습니다.');
                    alert('히스토리 데이터를 가져오는 중 오류가 발생했습니다.');
                }
            });
        }

        function getBookmarks() {
            $.ajax({
                url: 'wifi',
                type: 'get',
                data: {
                    action: 'getBookmarks'
                },
                success: function (data) {
                    console.log('북마크 데이터를 성공적으로 가져왔습니다.');
                    $('#result').html(data);
                },
                error: function () {
                    console.log('북마크 데이터를 가져오는 중 오류가 발생했습니다.');
                    alert('북마크 데이터를 가져오는 중 오류가 발생했습니다.');
                }
            });
        }

        function getBookmarkgroups() {
            $.ajax({
                url: 'wifi',
                type: 'get',
                data: {
                    action: 'getBookmarkgroups'
                },
                success: function (data) {
                    console.log('북마크 그룹 데이터를 성공적으로 가져왔습니다.');
                    $('#result').html(data);
                },
                error: function () {
                    console.log('북마크 그룹 데이터를 가져오는 중 오류가 발생했습니다.');
                    alert('북마크 그룹 데이터를 가져오는 중 오류가 발생했습니다.');
                }
            });
        }

    </script>
</head>
<body>
<h1>와이파이 정보 구하기</h1>
<nav>
    <a href="index.jsp">홈</a> |
    <a href="javascript:void(0);" onclick="getHistory()">위치 히스토리 목록</a> |
    <a href="fetch-seoul-wifi-data">Open API 와이파이 정보 가져오기</a> |
    <a href="javascript:void(0);" onclick="getBookmarks()">북마크 보기</a> |
    <a href="javascript:void(0);" onclick="getBookmarkgroups()">북마크 그룹 관리</a>
</nav>
<form id="locationForm">
    <input type="hidden" name="action" value="getNearest">
    <label for="lat">위도:</label>
    <input type="text" id="lat" name="lat">
    <label for="lnt">경도:</label>
    <input type="text" id="lnt" name="lnt">
    <button type="button" onclick="getLocation()">내 위치 가져오기</button>
    <button type="button" onclick="getNearestWifi()">근처 WIFI 정보 보기</button>
</form>
<div id="result"></div>
</body>
</html>
