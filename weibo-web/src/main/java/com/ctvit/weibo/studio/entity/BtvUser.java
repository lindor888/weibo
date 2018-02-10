package com.ctvit.weibo.studio.entity;
/**
 * 维护的微博用户
 * @author tqc
 *
 */
public class BtvUser {
	
	private String userid;
	
	private String face;
	
	private String nickname;
	
	private String location;

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getFace() {
		return face;
	}

	public void setFace(String face) {
		this.face = face;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

}
