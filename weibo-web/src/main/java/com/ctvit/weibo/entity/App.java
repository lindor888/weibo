package com.ctvit.weibo.entity;

import java.util.Date;

import com.ctvit.weibo.util.Pagination;

/**
 *应用类 
 *@author Administrator
 */
public class App extends Pagination{
	
    private String appId;

    private String userId;

    private String appName;

    private String appKey;

    private String appSecret;

    private String appRedirectUri;
    /**
     * 0为基础权限 1为高级权限
     */
    private Integer appLevel;

    private Date appCreateTime;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey == null ? null : appKey.trim();
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    public String getAppRedirectUri() {
        return appRedirectUri;
    }

    public void setAppRedirectUri(String appRedirectUri) {
        this.appRedirectUri = appRedirectUri == null ? null : appRedirectUri.trim();
    }

    public Integer getAppLevel() {
        return appLevel;
    }

    public void setAppLevel(Integer appLevel) {
        this.appLevel = appLevel;
    }

    public Date getAppCreateTime() {
        return appCreateTime;
    }

    public void setAppCreateTime(Date appCreateTime) {
        this.appCreateTime = appCreateTime;
    }
}