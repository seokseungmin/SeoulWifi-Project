package com.example.repository;

import com.example.model.SearchHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchHistoryRepository {
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/seoul_wifi";
    private String jdbcUsername = "root";
    private String jdbcPassword = "zerobase";

    public void saveSearchHistory(double lat, double lnt) throws SQLException {
        String sql = "INSERT INTO SearchHistory (lat, lnt) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.executeUpdate();
        }
    }

    public void deleteSearchHistory(long id) throws SQLException {
        String sql = "DELETE FROM SearchHistory WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            pstmt.executeUpdate();
        }
    }

    public List<SearchHistory> getSearchHistory() throws SQLException {
        List<SearchHistory> historyList = new ArrayList<>();
        String sql = "SELECT * FROM SearchHistory ORDER BY searchTime DESC";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                SearchHistory history = new SearchHistory();
                history.setId(rs.getLong("id"));
                history.setLat(rs.getDouble("lat"));
                history.setLnt(rs.getDouble("lnt"));
                history.setSearchTime(rs.getTimestamp("searchTime"));
                historyList.add(history);
            }
        }

        return historyList;
    }


}
