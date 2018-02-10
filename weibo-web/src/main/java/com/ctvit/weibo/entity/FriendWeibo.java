package com.ctvit.weibo.entity;

import java.util.Date;

import com.ctvit.weibo.util.Pagination;

public class FriendWeibo extends Pagination{
    private String friendWeiboId;

    private String weiboId;

    private String friendWeiboUid;

    private String friendWeiboName;

    private Date friendWeiboCreateTime;
    
    private String weiboToken;
    
    private String weiboUserName;//微博用户名
    private String weiboPassword;//微博密码
    private String appId;//应用ID

    public String getFriendWeiboId() {
        return friendWeiboId;
    }

    public void setFriendWeiboId(String friendWeiboId) {
        this.friendWeiboId = friendWeiboId == null ? null : friendWeiboId.trim();
    }

    public String getWeiboId() {
        return weiboId;
    }

    public void setWeiboId(String weiboId) {
        this.weiboId = weiboId == null ? null : weiboId.trim();
    }

    public String getFriendWeiboUid() {
        return friendWeiboUid;
    }

    public void setFriendWeiboUid(String friendWeiboUid) {
        this.friendWeiboUid = friendWeiboUid == null ? null : friendWeiboUid.trim();
    }

    public String getFriendWeiboName() {
        return friendWeiboName;
    }

    public void setFriendWeiboName(String friendWeiboName) {
        this.friendWeiboName = friendWeiboName == null ? null : friendWeiboName.trim();
    }

    public Date getFriendWeiboCreateTime() {
        return friendWeiboCreateTime;
    }

    public void setFriendWeiboCreateTime(Date friendWeiboCreateTime) {
        this.friendWeiboCreateTime = friendWeiboCreateTime;
    }

	public String getWeiboToken() {
		return weiboToken;
	}

	public void setWeiboToken(String weiboToken) {
		this.weiboToken = weiboToken;
	}

	public String getWeiboUserName() {
		return weiboUserName;
	}

	public void setWeiboUserName(String weiboUserName) {
		this.weiboUserName = weiboUserName;
	}

	public String getWeiboPassword() {
		return weiboPassword;
	}

	public void setWeiboPassword(String weiboPassword) {
		this.weiboPassword = weiboPassword;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}
    
}