package com.ctvit.weibo.task.action;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 基础任务设置->内容采集
 * 
 * @author Administrator
 * 
 */
public class ContentTaskAction extends ActionSupport {

	private static final long serialVersionUID = 3172461683182708318L;
	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(ContentTaskAction.class);

	public String indexPre() throws Exception {
		return "indexPre";
	}

	/**
	 * 添加内容采集定时任务
	 * @return
	 */
	public String add() {
		
		return "add";
	}

}
