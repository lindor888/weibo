package com.ctvit.weibo.action;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.ctvit.weibo.entity.SearchBean;
import com.ctvit.weibo.entity.SentWeibo;
import com.ctvit.weibo.entity.SentWeiboExample;
import com.ctvit.weibo.entity.User;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.service.SentWeiboService;
import com.ctvit.weibo.service.WeiboService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 * @author Administrator
 * 
 */
public class SendAction extends ActionSupport {

	private static final long serialVersionUID = 3172461683182708318L;
	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(SendAction.class);
	private WeiboService weiboService;
	private SentWeiboService sentService;
	
	private SentWeibo bean;
	private SentWeiboExample example;
	private Map<String,Object> queryJson;
	private List<SentWeibo> list;
	private List<Weibo> weibos;
	
	/**
	 * 进入发送微博页
	 * @return
	 * @throws Exception
	 */
	public String sendPre() throws Exception {
		try {
			Weibo weibo = new Weibo();
			User user = (User) ActionContext.getContext().getSession().get("user");
			weibo.setUserId(user.getUserId());
			weibos = weiboService.selectByUserId(weibo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "sendPre";
	}
	
	/**
	 * 查询待发送微博数据
	 * @return
	 * @throws Exception
	 */
	public String getFromSearch(){
		try {
			queryJson = new HashMap<String, Object>();
			Map<String,Object> map = weiboService.getFromSearch(example);
			Integer total = (Integer) map.get("total");
			List<SearchBean> list = (List<SearchBean>) map.get("list");
			queryJson.put("total", total);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("rows", list);
		} catch (ConnectException e) {
			queryJson.put("total", 0);
			queryJson.put("pageSum", example.getPageSum(0));
			queryJson.put("rows", new ArrayList<SearchBean>());
			log.error("", e);
		} catch (Exception e) {
			queryJson.put("total", 0);
			queryJson.put("pageSum", example.getPageSum(0));
			queryJson.put("rows", new ArrayList<SearchBean>());
			log.error("", e);
		}
		return "dataList";
	}
	
	/**
	 * 准备发送微博
	 * @return
	 * @throws Exception
	 */
	public String pushPre() throws Exception {
		try {
			String title = example.getTitle();
			title = new String(title.getBytes("iso8859-1"),"UTF-8");
			example.setTitle(title);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "pushPre";
	}
	
	/**
	 * 进入已发送微博页
	 * @return
	 */
	public String sentPre() throws Exception {
		try {
			Weibo weibo = new Weibo();
			User user = (User) ActionContext.getContext().getSession().get("user");
			weibo.setUserId(user.getUserId());
			weibos = weiboService.selectByUserId(weibo);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "sentPre";
	}
	
	/**
	 * 进入已发送微博--定时任务页
	 * @return
	 */
	public String sentPreTime() throws Exception {
		try {
			Weibo weibo = new Weibo();
			User user = (User) ActionContext.getContext().getSession().get("user");
			weibo.setUserId(user.getUserId());
			weibos = weiboService.selectByUserId(weibo);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "sentPreTime";
	}
	
	/**
	 * 分页查询已发送微博
	 * @return
	 * @throws Exception
	 */
	public String selectSent() throws Exception {
		try {
			list = sentService.getAll(example);
			int total = sentService.getCount(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("total", total);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("rows", list);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 删除已发微博
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			sentService.delete(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 删除定时发微博任务
	 * @return
	 * @throws Exception
	 */
	public String deleteTime() throws Exception {
		try {
			sentService.deleteTime(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public void setSentService(SentWeiboService sentService) {
		this.sentService = sentService;
	}

	public SentWeibo getBean() {
		return bean;
	}

	public void setBean(SentWeibo bean) {
		this.bean = bean;
	}

	public SentWeiboExample getExample() {
		return example;
	}

	public void setExample(SentWeiboExample example) {
		this.example = example;
	}

	public List<Weibo> getWeibos() {
		return weibos;
	}

	public void setWeibos(List<Weibo> weibos) {
		this.weibos = weibos;
	}
	
	
}
