<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 목록</title>
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
        a {
            color: #1a73e8;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
        .home-link {
            color: #1a73e8;
            text-decoration: none;
            font-weight: bold;
            display: inline-block;
            margin-top: 20px;
        }
        .home-link:hover {
            text-decoration: underline;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function updateBookmark(id) {
            // Update bookmark logic
        }

        function deleteBookmark(id) {
            $.ajax({
                url: 'wifi',
                type: 'post',
                data: {
                    action: 'deleteBookmark',
                    id: id
                },
                success: function () {
                    alert('북마크가 삭제되었습니다.');
                    location.reload();
                },
                error: function () {
                    alert('북마크 삭제 중 오류가 발생했습니다.');
                }
            });
        }
    </script>
</head>
<body>
<h1>북마크 목록</h1>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>와이파이명</th>
        <th>등록일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="bookmark" items="${bookmarkList}">
        <tr>
            <td>${bookmark.id}</td>
            <td>${bookmark.groupName}</td>
            <td><a href="wifi?action=getWifiDetails&id=${bookmark.wifiId}">${bookmark.mainNm}</a></td>
            <td>${bookmark.createdAt}</td>
            <td>
                <form action="wifi" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="deleteBookmark">
                    <input type="hidden" name="id" value="${bookmark.id}">
                    <button type="submit">삭제</button>
                </form>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href="index.jsp" class="home-link">홈으로 가기</a>
</body>
</html>
