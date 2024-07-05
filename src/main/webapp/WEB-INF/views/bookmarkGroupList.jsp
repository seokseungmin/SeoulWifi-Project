<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>북마크 그룹 목록</title>
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: center;
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
        .action-link {
            color: #1a73e8;
            cursor: pointer;
            text-decoration: none;
        }
        .action-link:hover {
            text-decoration: underline;
        }
    </style>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        function updateBookmarkGroup(id) {
            var groupName = prompt("새로운 북마크 그룹 이름을 입력하세요:");
            var sortOrder = prompt("새로운 순서를 입력하세요:");

            if (groupName && sortOrder) {
                $.ajax({
                    url: 'wifi',
                    type: 'post',
                    data: {
                        action: 'updateBookmarkGroup',
                        id: id,
                        groupName: groupName,
                        sortOrder: sortOrder
                    },
                    success: function () {
                        alert('북마크 그룹이 수정되었습니다.');
                        location.reload();
                    },
                    error: function () {
                        alert('북마크 그룹 수정 중 오류가 발생했습니다.');
                    }
                });
            }
        }

        function deleteBookmarkGroup(id) {
            if (confirm('정말로 이 북마크 그룹을 삭제하시겠습니까?')) {
                $.ajax({
                    url: 'wifi',
                    type: 'post',
                    data: {
                        action: 'deleteBookmarkGroup',
                        id: id
                    },
                    success: function () {
                        alert('북마크 그룹이 삭제되었습니다.');
                        location.reload();
                    },
                    error: function () {
                        alert('북마크 그룹 삭제 중 오류가 발생했습니다.');
                    }
                });
            }
        }

        function addBookmarkGroup() {
            var groupName = prompt("추가할 북마크 그룹 이름을 입력하세요:");
            var sortOrder = prompt("추가할 순서를 입력하세요:");

            if (groupName && sortOrder) {
                $.ajax({
                    url: 'wifi',
                    type: 'post',
                    data: {
                        action: 'addBookmarkGroup',
                        groupName: groupName,
                        sortOrder: sortOrder
                    },
                    success: function () {
                        alert('북마크 그룹이 추가되었습니다.');
                        location.reload();
                    },
                    error: function () {
                        alert('북마크 그룹 추가 중 오류가 발생했습니다.');
                    }
                });
            }
        }
    </script>
</head>
<body>
<h1>북마크 그룹 목록</h1>
<button onclick="addBookmarkGroup()">북마크 그룹 이름 추가</button>
<table>
    <thead>
    <tr>
        <th>ID</th>
        <th>북마크 이름</th>
        <th>순서</th>
        <th>등록일자</th>
        <th>수정일자</th>
        <th>비고</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="group" items="${bookmarkGroups}">
        <tr>
            <td>${group.id}</td>
            <td>${group.groupName}</td>
            <td>${group.sortOrder}</td>
            <td>${group.createdAt}</td>
            <td>${group.updatedAt}</td>
            <td>
                <a href="javascript:void(0);" class="action-link" onclick="updateBookmarkGroup(${group.id})">수정</a> |
                <a href="javascript:void(0);" class="action-link" onclick="deleteBookmarkGroup(${group.id})">삭제</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
