package com.ctvit.weibo.studio.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.mongodb.dao.CommentDao;
import com.ctvit.weibo.mongodb.dao.WeiboDao;
import com.ctvit.weibo.service.WeiboService;
import com.ctvit.weibo.studio.entity.BtvComment;
import com.ctvit.weibo.studio.entity.BtvWeibo;
import com.opensymphony.xwork2.ActionSupport;
import com.thoughtworks.xstream.XStream;

public class ShowAction extends ActionSupport {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6562298361619969808L;

	private Logger log = Logger.getLogger(ShowAction.class);

	private String taskWeiboId;

	private String startTime;

	private Integer rows;

	private String timesort;

	private Map<String, Object> queryJson;

	private CommentDao commentDao;

	private WeiboDao weiboDao;

	private int flag;

	/**
	 * 获取评论列表
	 * 
	 * @return
	 */
	public void getc() {

		// queryJson = new HashMap<String, Object>();
		// try {
		// List<BtvComment> list = commentDao.getCommentList(startTime, rows, timesort, taskWeiboId);
		// queryJson.put("total", list.size());
		// queryJson.put("rows", list);
		// } catch (Exception e) {
		// log.error("", e);
		// }
		// return "getc";
		//

		// ==================================================
		queryJson = new HashMap<String, Object>();
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			WeiboService weiboService = new WeiboService();
			List<Weibo> weibos = weiboService.getRossList();
			// queryJson.put("total", list.size());
			XStream xstream = new XStream();
			xstream.alias("WeiboData", Weibo.class);
			// xstream.alias("interacts", List.class);
			// xstream.alias("interacts", Interacts.class);
			String xml = "<?xml version='1.0' encoding='UTF-8' ?>";
			xml += xstream.toXML(weibos);
			// System.out.println(xml);
			// 解决历史数据问题，将图片的外网地址替换为内网地址
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			// new PrintWriter(new OutputStreamWriter(new (json, "UTF-8"));
			// out.print(xml);
			out.write(xml);
			out.flush();
			out.close();
			queryJson.put("rows", weibos);
		} catch (Exception e) {
			log.error("", e);
		}

	}

	/**
	 * 获取评论列表xml ross
	 * 
	 * @return
	 */
	public String getRoss() {
		queryJson = new HashMap<String, Object>();
		try {
			List<BtvComment> list = commentDao.getRossList();
			queryJson.put("total", list.size());
			queryJson.put("rows", list);
		} catch (Exception e) {
			log.error("", e);
		}
		return "getc";
	}

	/**
	 * 获取微博列表
	 * 
	 * @return
	 */
	public String getw() {
		queryJson = new HashMap<String, Object>();
		try {
			List<BtvWeibo> list = weiboDao.getWeiboList(startTime, rows, timesort, taskWeiboId);
			queryJson.put("total", list.size());
			queryJson.put("rows", list);
		} catch (Exception e) {
			log.error("", e);
		}
		return "getw";
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}

	public String getTimesort() {
		return timesort;
	}

	public void setTimesort(String timesort) {
		this.timesort = timesort;
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public WeiboDao getWeiboDao() {
		return weiboDao;
	}

	public void setWeiboDao(WeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}

	public String getTaskWeiboId() {
		return taskWeiboId;
	}

	public void setTaskWeiboId(String taskWeiboId) {
		this.taskWeiboId = taskWeiboId;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

}
