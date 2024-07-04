<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>와이파이 정보 구하기</title>
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
                    alert('근처 와이파이 20개 데이터를 가져오는 중 오류가 발생했습니다.');
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
