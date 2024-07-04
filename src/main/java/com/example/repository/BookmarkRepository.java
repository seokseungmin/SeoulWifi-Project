package com.example.repository;

import com.example.model.Bookmark;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkRepository {
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/seoul_wifi";
    private String jdbcUsername = "root";
    private String jdbcPassword = "zerobase";

    public void addBookmark(long wifiId, long groupId) throws SQLException {
        String sql = "INSERT INTO bookmark (wifiId, groupId, createdAt, updatedAt) VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, wifiId);
            pstmt.setLong(2, groupId);
            pstmt.executeUpdate();
        }
    }

    public void deleteBookmark(long id) throws SQLException {
        String sql = "DELETE FROM bookmark WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<Bookmark> getAllBookmarks() throws SQLException {
        List<Bookmark> bookmarkList = new ArrayList<>();
        String sql = "SELECT b.id, g.groupName, w.id as wifiId, w.mainNm, b.createdAt " +
                "FROM bookmark b " +
                "JOIN bookmarkgroup g ON b.groupId = g.id " +
                "JOIN wifiinfo w ON b.wifiId = w.id " +
                "ORDER BY g.sortOrder";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Bookmark bookmark = new Bookmark();
                bookmark.setId(rs.getLong("id"));
                bookmark.setGroupName(rs.getString("groupName"));
                bookmark.setWifiId(rs.getLong("wifiId"));
                bookmark.setMainNm(rs.getString("mainNm"));
                bookmark.setCreatedAt(rs.getTimestamp("createdAt"));
                bookmarkList.add(bookmark);
            }
        }
        return bookmarkList;
    }

    public void deleteBookmarksByGroupId(long groupId) throws SQLException {
        String sql = "DELETE FROM bookmark WHERE groupId = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, groupId);
            pstmt.executeUpdate();
        }
    }
}
