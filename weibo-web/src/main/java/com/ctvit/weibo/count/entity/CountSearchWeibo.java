package com.ctvit.weibo.count.entity;

import java.util.Date;

public class CountSearchWeibo {
    private Integer countId;

    private String taskId;

    private String q;

    private String lanmu;

    private Date countTime;

    private Integer countWeiboNum;

    private Integer countUserNum;

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

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q == null ? null : q.trim();
    }

    public String getLanmu() {
        return lanmu;
    }

    public void setLanmu(String lanmu) {
        this.lanmu = lanmu == null ? null : lanmu.trim();
    }

    public Date getCountTime() {
        return countTime;
    }

    public void setCountTime(Date countTime) {
        this.countTime = countTime;
    }

    public Integer getCountWeiboNum() {
        return countWeiboNum;
    }

    public void setCountWeiboNum(Integer countWeiboNum) {
        this.countWeiboNum = countWeiboNum;
    }

    public Integer getCountUserNum() {
        return countUserNum;
    }

    public void setCountUserNum(Integer countUserNum) {
        this.countUserNum = countUserNum;
    }
}