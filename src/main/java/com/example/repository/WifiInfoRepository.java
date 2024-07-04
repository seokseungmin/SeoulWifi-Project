package com.example.repository;

import com.example.model.WifiInfo;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WifiInfoRepository {
    private String jdbcUrl = "jdbc:mariadb://localhost:3306/seoul_wifi";
    private String jdbcUsername = "root";
    private String jdbcPassword = "zerobase";

    public List<WifiInfo> findNearestWifi(double lat, double lnt) throws SQLException {
        List<WifiInfo> wifiList = new ArrayList<>();
        String sql = "SELECT *, (6371 * acos(cos(radians(?)) * cos(radians(lat)) * cos(radians(lnt) - radians(?)) + sin(radians(?)) * sin(radians(lat)))) AS distance FROM WifiInfo ORDER BY distance LIMIT 20";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setDouble(1, lat);
            pstmt.setDouble(2, lnt);
            pstmt.setDouble(3, lat);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                WifiInfo wifi = new WifiInfo();
                wifi.setId(rs.getLong("id"));
                wifi.setMgrNo(rs.getString("mgrNo"));
                wifi.setWrdofc(rs.getString("wrdofc"));
                wifi.setMainNm(rs.getString("mainNm"));
                wifi.setAdres1(rs.getString("adres1"));
                wifi.setAdres2(rs.getString("adres2"));
                wifi.setInstlFloor(rs.getString("instlFloor"));
                wifi.setInstlTy(rs.getString("instlTy"));
                wifi.setInstlMby(rs.getString("instlMby"));
                wifi.setSvcSe(rs.getString("svcSe"));
                wifi.setCmcwr(rs.getString("cmcwr"));
                wifi.setCnstcYear(rs.getString("cnstcYear"));
                wifi.setInoutDoor(rs.getString("inoutDoor"));
                wifi.setRemars3(rs.getString("remars3"));
                wifi.setLat(BigDecimal.valueOf(rs.getDouble("lat")));
                wifi.setLnt(BigDecimal.valueOf(rs.getDouble("lnt")));
                wifi.setWorkDttm(rs.getString("workDttm"));
                wifi.setDistance(rs.getDouble("distance"));
                wifiList.add(wifi);
            }
        }

        return wifiList;
    }

    public WifiInfo findWifiById(long wifiId) throws SQLException {
        WifiInfo wifi = null;
        String sql = "SELECT * FROM WifiInfo WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, wifiId);

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                wifi = new WifiInfo();
                wifi.setId(rs.getLong("id"));
                wifi.setMgrNo(rs.getString("mgrNo"));
                wifi.setWrdofc(rs.getString("wrdofc"));
                wifi.setMainNm(rs.getString("mainNm"));
                wifi.setAdres1(rs.getString("adres1"));
                wifi.setAdres2(rs.getString("adres2"));
                wifi.setInstlFloor(rs.getString("instlFloor"));
                wifi.setInstlTy(rs.getString("instlTy"));
                wifi.setInstlMby(rs.getString("instlMby"));
                wifi.setSvcSe(rs.getString("svcSe"));
                wifi.setCmcwr(rs.getString("cmcwr"));
                wifi.setCnstcYear(rs.getString("cnstcYear"));
                wifi.setInoutDoor(rs.getString("inoutDoor"));
                wifi.setRemars3(rs.getString("remars3"));
                wifi.setLat(BigDecimal.valueOf(rs.getDouble("lat")));
                wifi.setLnt(BigDecimal.valueOf(rs.getDouble("lnt")));
                wifi.setWorkDttm(rs.getString("workDttm"));
            }
        }

        return wifi;
    }
}
