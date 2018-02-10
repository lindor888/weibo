package com.ctvit.weibo.count.entity;

public class CountFollowerSex {
    private Integer countId;

    private String taskId;

    private String uid;

    private Integer countMaleNum;

    private Integer countFemaleNum;

    public Integer getCountId() {
        return countId;
    }

    public void setCountId(Integer countId) {
        this.countId = countId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId == null ? null : taskId.trim();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public Integer getCountMaleNum() {
        return countMaleNum;
    }

    public void setCountMaleNum(Integer countMaleNum) {
        this.countMaleNum = countMaleNum;
    }

    public Integer getCountFemaleNum() {
        return countFemaleNum;
    }

    public void setCountFemaleNum(Integer countFemaleNum) {
        this.countFemaleNum = countFemaleNum;
    }
}