package com.ctvit.weibo.entity;

public class SentWeiboRelation {
	
	private String id;
	/**
	 * 已发送微博表主键
	 */
	private String sentId;
	/**
	 * 微博表主键
	 */
	private String weiboId;
	
    private String appKey;
    private String appSecret;
    private String appRedirectUri;
    private String weiboUserName;
    private String weiboPassword;
    private String weiboContentId;//所发布微博内容的主键ID

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getSentId() {
		return sentId;
	}

	public void setSentId(String sentId) {
		this.sentId = sentId == null ? null : sentId.trim();
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId == null ? null : weiboId.trim();
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

	public String getWeiboContentId() {
		return weiboContentId;
	}

	public void setWeiboContentId(String weiboContentId) {
		this.weiboContentId = weiboContentId;
	}
	
}