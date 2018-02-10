package com.ctvit.weibo.entity;

import java.util.Date;

import com.ctvit.weibo.util.Pagination;

public class Weibo extends Pagination {
    private String weiboId;

    private String weiboUserName;
    private String weiboUserNameOld;

    private String weiboPassword;

    private String weiboUid;

    private String appId;

    private Date weiboCreateTime;

    private Integer deleteFlag;

    private String weiboToken;
    
    private String[] weiboIds;
    //其他类中的属性
    private String appKey;
    private String appSecret;
    private String appRedirectUri;
    private String appName;
    private String userId;//当前登录的ID
    
    private String contentId;//微博内容ID
    private String title;//微博标题---CMS发过来的标题
    private String url;//url----CMS发送过来的静态url
    //微博内容
    private String content;
    private String imageUrl;
    //微博来源名称
    private String sourceName;
    private String sourceUrl;
    private Integer commentsCount;
    private String userCcreenName;//微博昵称
    private String profileImageUrl;//用户头像地址50x50
    private Integer followersCount;//粉丝数
    private Integer friendsCount;//关注数
    private String description;//用户个人描述
    private Integer statusesCount;//微博数
    
    private Integer status;//0:删除 1:待发送 2:已发送
    private Date beginTime;
    private String beginTimeStr;
    private Date endTime;
    private String endTimeStr;
    
    //审核状态
    private int flag;
    
 
    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId == null ? null : weiboId.trim();
    }

    public String getWeiboUserName() {
        return weiboUserName;
    }

    public void setWeiboUserName(String weiboUserName) {
        this.weiboUserName = weiboUserName == null ? null : weiboUserName.trim();
    }

    public String getWeiboPassword() {
        return weiboPassword;
    }

    public void setWeiboPassword(String weiboPassword) {
        this.weiboPassword = weiboPassword == null ? null : weiboPassword.trim();
    }

    public String getWeiboUid() {
        return weiboUid;
    }

    public void setWeiboUid(String weiboUid) {
        this.weiboUid = weiboUid == null ? null : weiboUid.trim();
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    public Date getWeiboCreateTime() {
        return weiboCreateTime;
    }

    public void setWeiboCreateTime(Date weiboCreateTime) {
        this.weiboCreateTime = weiboCreateTime;
    }

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public String getWeiboToken() {
        return weiboToken;
    }

    public void setWeiboToken(String weiboToken) {
        this.weiboToken = weiboToken == null ? null : weiboToken.trim();
    }

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getAppRedirectUri() {
		return appRedirectUri;
	}

	public void setAppRedirectUri(String appRedirectUri) {
		this.appRedirectUri = appRedirectUri;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getWeiboUserNameOld() {
		return weiboUserNameOld;
	}

	public void setWeiboUserNameOld(String weiboUserNameOld) {
		this.weiboUserNameOld = weiboUserNameOld;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String[] getWeiboIds() {
		return weiboIds;
	}

	public void setWeiboIds(String[] weiboIds) {
		this.weiboIds = weiboIds;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	public Integer getCommentsCount() {
		return commentsCount;
	}

	public void setCommentsCount(Integer commentsCount) {
		this.commentsCount = commentsCount;
	}

	public String getUserCcreenName() {
		return userCcreenName;
	}

	public void setUserCcreenName(String userCcreenName) {
		this.userCcreenName = userCcreenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public void setProfileImageUrl(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public Integer getFollowersCount() {
		return followersCount;
	}

	public void setFollowersCount(Integer followersCount) {
		this.followersCount = followersCount;
	}

	public Integer getFriendsCount() {
		return friendsCount;
	}

	public void setFriendsCount(Integer friendsCount) {
		this.friendsCount = friendsCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatusesCount() {
		return statusesCount;
	}

	public void setStatusesCount(Integer statusesCount) {
		this.statusesCount = statusesCount;
	}

	public String getContentId() {
		return contentId;
	}

	public void setContentId(String contentId) {
		this.contentId = contentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public String getBeginTimeStr() {
		return beginTimeStr;
	}

	public void setBeginTimeStr(String beginTimeStr) {
		this.beginTimeStr = beginTimeStr;
	}

	public String getEndTimeStr() {
		return endTimeStr;
	}

	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}
    
}