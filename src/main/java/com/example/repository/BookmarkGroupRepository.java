package com.example.repository;

import com.example.model.BookmarkGroup;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookmarkGroupRepository {
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/seoul_wifi";
    private String jdbcUsername = "root";
    private String jdbcPassword = "zerobase";

    public List<BookmarkGroup> getAllGroups() throws SQLException {
        List<BookmarkGroup> groupList = new ArrayList<>();
        String sql = "SELECT * FROM bookmarkgroup ORDER BY id";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getLong("id"));
                group.setGroupName(rs.getString("groupName"));
                group.setSortOrder(rs.getInt("sortOrder"));
                group.setCreatedAt(rs.getTimestamp("createdAt"));
                group.setUpdatedAt(rs.getTimestamp("updatedAt"));
                groupList.add(group);
            }
        }

        return groupList;
    }

    public BookmarkGroup getGroupById(long id) throws SQLException {
        String sql = "SELECT id, groupName, sortOrder, createdAt, updatedAt FROM bookmarkgroup WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                BookmarkGroup group = new BookmarkGroup();
                group.setId(rs.getLong("id"));
                group.setGroupName(rs.getString("groupName"));
                group.setSortOrder(rs.getInt("sortOrder"));
                group.setCreatedAt(rs.getTimestamp("createdAt"));
                group.setUpdatedAt(rs.getTimestamp("updatedAt"));
                return group;
            }
        }
        return null;
    }

    public void addGroup(String groupName, int sortOrder) throws SQLException {
        String sql = "INSERT INTO bookmarkgroup (groupName, sortOrder, createdAt, updatedAt) VALUES (?, ?, NOW(), NOW())";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setInt(2, sortOrder);
            pstmt.executeUpdate();
        }
    }

    public void updateGroup(long id, String groupName, int sortOrder) throws SQLException {
        String sql = "UPDATE bookmarkgroup SET groupName = ?, sortOrder = ?, updatedAt = NOW() WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, groupName);
            pstmt.setInt(2, sortOrder);
            pstmt.setLong(3, id);
            pstmt.executeUpdate();
        }
    }

    public void deleteGroup(long id) throws SQLException {
        String sql = "DELETE FROM bookmarkgroup WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }
}
