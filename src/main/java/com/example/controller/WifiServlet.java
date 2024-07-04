package com.example.controller;

import com.example.model.Bookmark;
import com.example.model.BookmarkGroup;
import com.example.model.SearchHistory;
import com.example.model.WifiInfo;
import com.example.repository.BookmarkGroupRepository;
import com.example.repository.BookmarkRepository;
import com.example.repository.SearchHistoryRepository;
import com.example.repository.WifiInfoRepository;
import com.google.gson.Gson;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/wifi")
public class WifiServlet extends HttpServlet {

    private final SearchHistoryRepository historyRepository = new SearchHistoryRepository();
    private final WifiInfoRepository wifiRepository = new WifiInfoRepository();
    private final BookmarkRepository bookmarkRepository = new BookmarkRepository();
    private final BookmarkGroupRepository bookmarkGroupRepository = new BookmarkGroupRepository();

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            Class.forName("org.mariadb.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("MariaDB JDBC Driver not found!", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("deleteHistory".equals(action)) {
            handleDeleteHistory(req, resp);
        } else if ("addBookmark".equals(action)) {
            handleAddBookmark(req, resp);
        } else if ("deleteBookmark".equals(action)) {
            handleDeleteBookmark(req, resp);
        } else if ("updateBookmarkGroup".equals(action)) {
            handleUpdateBookmarkGroup(req, resp);
        } else if ("deleteBookmarkGroup".equals(action)) {
            handleDeleteBookmarkGroup(req, resp);
        } else if ("addBookmarkGroup".equals(action)) {
            handleAddBookmarkGroup(req, resp);
        }

    }

    private void handleDeleteHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.parseLong(req.getParameter("id"));

        try {
            historyRepository.deleteSearchHistory(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // After deletion, redirect to the history list page to see the updated list
        resp.sendRedirect("wifi?action=getHistory");
    }

    private void handleAddBookmark(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long wifiId = Long.parseLong(req.getParameter("wifiId"));
        long groupId = Long.parseLong(req.getParameter("groupId"));

        try {
            bookmarkRepository.addBookmark(wifiId, groupId);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 추가 중 오류가 발생했습니다.\"}");
        }
    }

    private void handleDeleteBookmark(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        try {
            bookmarkRepository.deleteBookmark(id);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"success\"}");
            resp.sendRedirect("wifi?action=getBookmarks");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 삭제 중 오류가 발생했습니다.\"}");
        }
    }

    private void handleUpdateBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));
        String groupName = req.getParameter("groupName");
        int sortOrder = Integer.parseInt(req.getParameter("sortOrder"));

        try {
            bookmarkGroupRepository.updateGroup(id, groupName, sortOrder);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 그룹 수정 중 오류가 발생했습니다.\"}");
        }
    }

    private void handleDeleteBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        long id = Long.parseLong(req.getParameter("id"));

        try {
            // 먼저 bookmark 테이블에서 관련 행을 삭제합니다.
            bookmarkRepository.deleteBookmarksByGroupId(id);

            // 그런 다음 bookmarkgroup 테이블에서 행을 삭제합니다.
            bookmarkGroupRepository.deleteGroup(id);

            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 그룹 삭제 중 오류가 발생했습니다.\"}");
        }
    }

    private void handleAddBookmarkGroup(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String groupName = req.getParameter("groupName");
        int sortOrder = Integer.parseInt(req.getParameter("sortOrder"));

        try {
            bookmarkGroupRepository.addGroup(groupName, sortOrder);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"success\"}");
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 그룹 추가 중 오류가 발생했습니다.\"}");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");

        if ("getNearest".equals(action)) {
            handleGetNearest(req, resp);
        } else if ("getHistory".equals(action)) {
            handleGetHistory(req, resp);
        } else if ("getWifiDetails".equals(action)) {
            handleGetWifiDetails(req, resp);
        } else if ("getBookmarks".equals(action)) {
            handleGetBookmarks(req, resp);
        } else if ("getBookmarkgroups".equals(action)) {
            handleGetBookmarkgroups(req, resp);
        } else if ("getBookmarkGroupOptions".equals(action)) {
            handleGetBookmarkGroupOptions(req, resp);
        }
    }

    private void handleGetNearest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lat = req.getParameter("lat");
        String lnt = req.getParameter("lnt");

        try {
            historyRepository.saveSearchHistory(Double.parseDouble(lat), Double.parseDouble(lnt));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<WifiInfo> wifiList = null;
        try {
            wifiList = wifiRepository.findNearestWifi(Double.parseDouble(lat), Double.parseDouble(lnt));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("wifiList", wifiList);
        req.getRequestDispatcher("/WEB-INF/views/wifiList.jsp").forward(req, resp);
    }

    private void handleGetHistory(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<SearchHistory> historyList = historyRepository.getSearchHistory();
            req.setAttribute("historyList", historyList);
            req.getRequestDispatcher("/WEB-INF/views/historyList.jsp").forward(req, resp);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void handleGetWifiDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long wifiId = Long.parseLong(req.getParameter("id"));
        WifiInfo wifiInfo = null;
        try {
            wifiInfo = wifiRepository.findWifiById(wifiId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        req.setAttribute("wifi", wifiInfo);
        req.getRequestDispatcher("/WEB-INF/views/wifiDetails.jsp").forward(req, resp);
    }

    private void handleGetBookmarks(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<Bookmark> bookmarks = bookmarkRepository.getAllBookmarks();
            req.setAttribute("bookmarkList", bookmarks);
            req.getRequestDispatcher("/WEB-INF/views/bookmarkList.jsp").forward(req, resp);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "북마크 데이터를 가져오는 중 오류가 발생했습니다.");
        }
    }

    private void handleGetBookmarkgroups(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.getAllGroups();
            req.setAttribute("bookmarkGroups", bookmarkGroups);
            req.getRequestDispatcher("/WEB-INF/views/bookmarkGroupList.jsp").forward(req, resp);
        } catch (SQLException | ServletException e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "북마크 그룹 데이터를 가져오는 중 오류가 발생했습니다.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleGetBookmarkGroupOptions(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            List<BookmarkGroup> bookmarkGroups = bookmarkGroupRepository.getAllGroups();
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write(new Gson().toJson(bookmarkGroups));
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            resp.setContentType("application/json");
            resp.setCharacterEncoding("UTF-8");
            resp.getWriter().write("{\"status\":\"error\", \"message\":\"북마크 그룹 데이터를 가져오는 중 오류가 발생했습니다.\"}");
        }
    }
}