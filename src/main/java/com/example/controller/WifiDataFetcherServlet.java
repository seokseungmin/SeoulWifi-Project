package com.example.controller;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import org.json.JSONArray;
import org.json.JSONObject;

@WebServlet("/fetch-seoul-wifi-data")
public class WifiDataFetcherServlet extends HttpServlet {

    private static final String API_URL = "http://openapi.seoul.go.kr:8088";
    private static final String API_KEY = "506b73675173656f313943556a5343"; // OpenAPI 인증키
    private static final String SERVICE = "TbPublicWifiInfo";
    private static final String DATA_TYPE = "json";
    private static final int PAGE_SIZE = 1000; // 한번에 가져올 데이터 수
    private static final String DB_URL = "jdbc:mariadb://localhost:3306/seoul_wifi?useUnicode=true&characterEncoding=utf8mb4";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "zerobase";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int savedCount = 0;
        Connection dbConn = null;
        PreparedStatement pstmt = null;
        try {
            // JDBC 드라이버 등록
            Class.forName("org.mariadb.jdbc.Driver");

            // 데이터베이스 연결
            dbConn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            String sql = "INSERT INTO WifiInfo (mgrNo, wrdofc, mainNm, adres1, adres2, instlFloor, instlTy, instlMby, svcSe, cmcwr, cnstcYear, inoutDoor, remars3, lat, lnt, workDttm) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = dbConn.prepareStatement(sql);

            // API 호출 반복
            int startIndex = 1;
            int endIndex = PAGE_SIZE;
            boolean moreData = true;

            while (moreData) {
                StringBuilder urlBuilder = new StringBuilder(API_URL);
                urlBuilder.append("/" + URLEncoder.encode(API_KEY, "UTF-8"));
                urlBuilder.append("/" + URLEncoder.encode(DATA_TYPE, "UTF-8"));
                urlBuilder.append("/" + URLEncoder.encode(SERVICE, "UTF-8"));
                urlBuilder.append("/" + URLEncoder.encode(String.valueOf(startIndex), "UTF-8"));
                urlBuilder.append("/" + URLEncoder.encode(String.valueOf(endIndex), "UTF-8"));

                URL url = new URL(urlBuilder.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-type", "application/json");
                System.out.println("Response code: " + conn.getResponseCode());

                BufferedReader rd;
                if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                    rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                } else {
                    rd = new BufferedReader(new InputStreamReader(conn.getErrorStream(), "UTF-8"));
                }
                StringBuilder content = new StringBuilder();
                String line;
                while ((line = rd.readLine()) != null) {
                    content.append(line);
                }
                rd.close();
                conn.disconnect();

                System.out.println("API Response: " + content.toString());

                JSONObject json = new JSONObject(content.toString());
                JSONObject tbPublicWifiInfo = json.getJSONObject("TbPublicWifiInfo");
                JSONArray rows = tbPublicWifiInfo.getJSONArray("row");
                int totalDataCount = tbPublicWifiInfo.getInt("list_total_count");

                for (int i = 0; i < rows.length(); i++) {
                    JSONObject row = rows.getJSONObject(i);
                    System.out.println("Row Data: " + row.toString());
                    pstmt.setString(1, row.getString("X_SWIFI_MGR_NO"));
                    pstmt.setString(2, row.getString("X_SWIFI_WRDOFC"));
                    pstmt.setString(3, row.getString("X_SWIFI_MAIN_NM"));
                    pstmt.setString(4, row.getString("X_SWIFI_ADRES1"));
                    pstmt.setString(5, row.getString("X_SWIFI_ADRES2"));
                    pstmt.setString(6, row.getString("X_SWIFI_INSTL_FLOOR"));
                    pstmt.setString(7, row.getString("X_SWIFI_INSTL_TY"));
                    pstmt.setString(8, row.getString("X_SWIFI_INSTL_MBY"));
                    pstmt.setString(9, row.getString("X_SWIFI_SVC_SE"));
                    pstmt.setString(10, row.getString("X_SWIFI_CMCWR"));
                    pstmt.setString(11, row.getString("X_SWIFI_CNSTC_YEAR"));
                    pstmt.setString(12, row.getString("X_SWIFI_INOUT_DOOR"));
                    pstmt.setString(13, row.getString("X_SWIFI_REMARS3"));
                    pstmt.setBigDecimal(14, new BigDecimal(row.getString("LAT")));
                    pstmt.setBigDecimal(15, new BigDecimal(row.getString("LNT")));
                    pstmt.setString(16, row.getString("WORK_DTTM"));
                    pstmt.addBatch();
                    savedCount++;
                }

                pstmt.executeBatch();

                startIndex += PAGE_SIZE;
                endIndex += PAGE_SIZE;

                if (startIndex > totalDataCount) {
                    moreData = false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (dbConn != null) try {
                dbConn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        req.setAttribute("savedCount", savedCount);
        req.getRequestDispatcher("/WEB-INF/views/fetchResult.jsp").forward(req, resp);
    }
}
