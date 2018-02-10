package com.ctvit.weibo.count.entity;

import java.util.Date;

public class CountWeibo {
    private Integer countId;

    private String taskId;

    private String uid;

    private Date countTime;

    private Integer countCommentNum;

    private Integer countRepostNum;

    private Integer countWeiboNum;

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

    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public Integer getCountCommentNum() {
        return countCommentNum;
    }

    public void setCountCommentNum(Integer countCommentNum) {
        this.countCommentNum = countCommentNum;
    }

    public Integer getCountRepostNum() {
        return countRepostNum;
    }

    public void setCountRepostNum(Integer countRepostNum) {
        this.countRepostNum = countRepostNum;
    }

    public Integer getCountWeiboNum() {
        return countWeiboNum;
    }

    public void setCountWeiboNum(Integer countWeiboNum) {
        this.countWeiboNum = countWeiboNum;
    }
}