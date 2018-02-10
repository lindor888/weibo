package com.ctvit.weibo.count.entity;

public class CountFollowerDistrict {
    private Integer countId;

    private String taskId;

    private String uid;

    private String district;

    private Integer countDistrictNum;

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

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    public Integer getCountDistrictNum() {
        return countDistrictNum;
    }

    public void setCountDistrictNum(Integer countDistrictNum) {
        this.countDistrictNum = countDistrictNum;
    }
}