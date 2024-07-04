<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>위치 히스토리</title>
</head>
<body>
<h1>위치 히스토리</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>위도</th>
        <th>경도</th>
        <th>검색 시간</th>
        <th>비고</th>
    </tr>
    <c:forEach var="history" items="${historyList}">
        <tr>
            <td>${history.id}</td>
            <td>${history.lat}</td>
            <td>${history.lnt}</td>
            <td>${history.searchTime}</td>
            <td>
                <form action="wifi" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="deleteHistory">
                    <input type="hidden" name="id" value="${history.id}">
                    <button type="submit">삭제</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<a href="index.jsp">홈으로 가기</a>
</body>
</html>
