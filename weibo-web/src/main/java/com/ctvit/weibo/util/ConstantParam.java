package com.ctvit.weibo.util;

public class ConstantParam {
	
	public static int TYPE_SINA = 0;
	
	public static int TYPE_TENCENT = 1;
	
	/**
	 * 状态有效
	 */
	public static int STATUS_VALID = 0;
	
	/**
	 * 状态无效
	 */
	public static int STATUS_INVALID = 1;
	
	/**
	 * 微博已删除状态
	 */
	public static int STATUS_DEL = 0;
	/**
	 * 微博定时发送状态
	 */
	public static int STATUS_TIME = 1;
	/**
	 * 微博已发送状态
	 */
	public static int STATUS_SENT = 2;
	
	/**
	 * mongodb微博表
	 */
	public static String MONGODB_WEIBO = "weibo";
	
	/**
	 * mongodb评论表
	 */
	public static String MONGODB_COMMENT = "comment";
	
	/**
	 * mongodb转发表
	 */
	public static String MONGODB_REPOST = "repost";
	
	/**
	 * mongodb用户表
	 */
	public static String MONGODB_USER = "user";
	
	/**
	 * mongodb搜索微博表
	 */
	public static String MONGODB_SEARCH_WEIBO = "searchweibo";
	
	/**
	 * mongodb中未统计过数据
	 */
	public static int NO_COUNT = 0;
	
	/**
	 * mongodb中已统计过数据
	 */
	public static int YES_COUNT = 1;
	
}
