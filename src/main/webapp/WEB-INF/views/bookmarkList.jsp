<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 목록</title>
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
                error: function () {
                    alert('북마크 삭제 중 오류가 발생했습니다.');
                }
            });
        }
    </script>
</head>
<body>
<h1>북마크 목록</h1>
<table border="1">
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
<a href="index.jsp">홈으로 가기</a>
</body>
</html>
