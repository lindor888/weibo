package com.ctvit.weibo.action;

import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.App;
import com.ctvit.weibo.entity.AppExample;
import com.ctvit.weibo.entity.User;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.service.AppService;
import com.ctvit.weibo.util.KeyUtil;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 应用管理Action
 * 
 * @author Administrator
 */
public class AppAction extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(AppAction.class);
	private App bean; // 应用类
	private List<App> apps; // 应用集合
	private AppExample example; // 应用分页类
	private AppService appService; // 应用服务类
	private HashMap<String, Object> queryJson;
	private List<Weibo> weibos; // 应用对应的微博信息

	public String indexPre() throws Exception {
		return "indexPre";
	}

	/**
	 * 准备添加应用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String addPre() throws Exception {
		return "addPre";
	}

	/**
	 * 添加应用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			String appId = KeyUtil.getKey(bean);
			bean.setAppCreateTime(new Date());
			bean.setAppId(appId);
			appService.insert(bean);
			return refreshTableDataOfParentWindow();
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
	}

	/**
	 * 更新前先查出应用信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String updatePre() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			if (user != null) {
				example = new AppExample();
				example.setUserId(user.getUserId());
			}
			apps = appService.getByPaging(example);
			bean = appService.searchById(bean.getAppId());
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "updatePre";
	}

	/**
	 * 分页查询
	 * 
	 * @return
	 * @throws Exception
	 */
	public String getByPaging() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			example.setUserId(user.getUserId());
			apps = appService.getByPaging(example);
			int total = appService.getCount(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("total", total);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("rows", apps);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}

	/**
	 * 准备添加查询绑定的微博
	 * 
	 * @throws Exception
	 * @return
	 */
	public String queryBindWeiBoPre() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			bean.setAppName(new String(bean.getAppName().getBytes("iso8859-1"),"UTF-8"));
			if (user != null) {
				example = new AppExample();
				example.setUserId(user.getUserId());
			}
			apps = appService.getByPaging(example);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "queryBindWeiBoPre";
	}

	/**
	 * 检查应用是否已经添加、
	 * 
	 * @return
	 * @throws Exception
	 */
	public String searchByName() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get("user");
			bean.setUserId(user.getUserId());
			App app = appService.searchByName(bean);
			queryJson = new HashMap<String, Object>();
			if (app != null) {
				// 完全一致，证明已经存在该应用
				queryJson.put("msg", "1");
			} else {
				queryJson.put("msg", "2");
			}
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}

	/**
	 * 刷新父窗口表格里的数据
	 * 
	 * @return
	 * @throws Exception
	 */
	protected String refreshTableDataOfParentWindow() throws Exception {
		String html = "<script language='javascript'>parent.window.refreshTableData();</script>";
		HttpServletResponse response = ServletActionContext.getResponse();
		response.setContentType("text/html;charset=utf-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		out.println(html);
		out.flush();
		out.close();
		return null;
	}

	/**
	 * 删除应用
	 * 
	 * @return
	 * @throws Exception
	 */
	public String delete() throws Exception {
		try {
			apps = appService.getByPaging(null);
			appService.delete(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}

	/**
	 * 更新应用信息
	 * 
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		try {
			appService.update(bean);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return refreshTableDataOfParentWindow();
	}

	public App getBean() {
		return bean;
	}

	public void setBean(App bean) {
		this.bean = bean;
	}

	public AppExample getExample() {
		return example;
	}

	public void setExample(AppExample example) {
		this.example = example;
	}

	public AppService getAppService() {
		return appService;
	}

	public void setAppService(AppService appService) {
		this.appService = appService;
	}

	public HashMap<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(HashMap<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public List<App> getApps() {
		return apps;
	}

	public void setApps(List<App> apps) {
		this.apps = apps;
	}

	public List<Weibo> getWeibos() {
		return weibos;
	}

	public void setWeibos(List<Weibo> weibos) {
		this.weibos = weibos;
	}

}
