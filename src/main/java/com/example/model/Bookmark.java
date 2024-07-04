package com.example.model;

import java.sql.Timestamp;

public class Bookmark {
    private Long id;
    private Long sortOrder;
    private Long wifiId;
    private Long groupId;
    private String mainNm;
    private String groupName;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWifiId() {
        return wifiId;
    }

    public void setWifiId(Long wifiId) {
        this.wifiId = wifiId;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getMainNm() {
        return mainNm;
    }

    public void setMainNm(String mainNm) {
        this.mainNm = mainNm;
    }

    public Long getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Long sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }
}
