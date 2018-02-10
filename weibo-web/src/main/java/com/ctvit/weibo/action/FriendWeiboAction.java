package com.ctvit.weibo.action;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.FriendWeibo;
import com.ctvit.weibo.entity.FriendWeiboExample;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.service.FriendWeiboService;
import com.ctvit.weibo.service.WeiboService;
import com.opensymphony.xwork2.ActionSupport;

/**
 * @author Administrator
 * 
 */
public class FriendWeiboAction extends ActionSupport {

	private static final long serialVersionUID = 3172461683182708318L;
	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(FriendWeiboAction.class);
	
	private FriendWeiboService friendWeiboService;//我的关注微博服务类
	private WeiboService weiboService;//微博服务类
	
	private List<FriendWeibo> friendWeibos;//我的关注微博信息
	private Map<String,Object> queryJson;
	
	private FriendWeibo bean;//我的关注微博实体类
	private Weibo weibo;//微博实体类
	private FriendWeiboExample example;//我的关注微博分页类
	
	
	public String indexPre() throws Exception {
		return "indexPre";
	}
	/**
	 * 获取我的关注微博
	 * @return
	 * @throws Exception
	 */
	public String getByPaging() throws Exception {
		try {
			friendWeibos = friendWeiboService.getByPaging(example);
			int total = friendWeiboService.getCount(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("total", total);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("rows", friendWeibos);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 删除我的关注
	 * @return
	 * @throws Eception
	 */
	public String delete() throws Exception {
		try {
			int res = friendWeiboService.delete(bean);
			queryJson = new HashMap<String, Object>();
			if(res == 1) {
				queryJson.put("result", "删除成功");
			} else if(res == 2) {
				queryJson.put("result", "该小时内调用新浪接口次数已超，请等待。");
			} else {
				queryJson.put("result", "删除失败!");
			}
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList"; 
	}
	
	/**
	 * 添加关注准备
	 * @return
	 * @throws Exception
	 */
	public String addPre() throws Exception {
		try {
			weibo = weiboService.searchById(bean.getWeiboId());
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "addPre";
	}
	
	/**
	 * 添加关注
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			friendWeiboService.add(bean);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return refreshTableDataOfParentWindow();
	}
	
	/**
	 * 刷新父窗口表格里的数据
	 * @return
	 * @throws Exception
	 */
	protected String refreshTableDataOfParentWindow() throws Exception{
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
	
	
	
	
// getter&setter================================================================
	public Map<String, Object> getQueryJson() {
		return queryJson;
	}
	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}
	public FriendWeibo getBean() {
		return bean;
	}
	public void setBean(FriendWeibo bean) {
		this.bean = bean;
	}
	public FriendWeiboExample getExample() {
		return example;
	}
	public void setExample(FriendWeiboExample example) {
		this.example = example;
	}
	public void setFriendWeiboService(FriendWeiboService friendWeiboService) {
		this.friendWeiboService = friendWeiboService;
	}
	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}
	public Weibo getWeibo() {
		return weibo;
	}
	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}
	
}
