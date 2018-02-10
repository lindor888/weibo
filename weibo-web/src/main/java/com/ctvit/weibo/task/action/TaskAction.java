package com.ctvit.weibo.task.action;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.ctvit.weibo.count.entity.CountFollowerDistrict;
import com.ctvit.weibo.count.entity.CountFollowerSex;
import com.ctvit.weibo.count.entity.CountSearchWeibo;
import com.ctvit.weibo.count.entity.CountWeibo;
import com.ctvit.weibo.count.service.CountFollowerDistrictService;
import com.ctvit.weibo.count.service.CountFollowerSexService;
import com.ctvit.weibo.count.service.CountSearchWeiboService;
import com.ctvit.weibo.count.service.CountWeiboService;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.mongodb.dao.CommentDao;
import com.ctvit.weibo.mongodb.dao.RepostDao;
import com.ctvit.weibo.mongodb.dao.SearchWeiboDao;
import com.ctvit.weibo.mongodb.dao.UserDao;
import com.ctvit.weibo.mongodb.dao.WeiboDao;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.task.service.TaskService;
import com.ctvit.weibo.util.QuartzRuleUtil;
import com.opensymphony.xwork2.ActionSupport;

public class TaskAction extends ActionSupport {

	private static final long serialVersionUID = 8571217475064202923L;
	
	private Logger log = Logger.getLogger(TaskAction.class);
	
	private Weibo weibo;//微博
	private Task bean;//任务
	private Task example;//任务
	
	private Map<String,Object> queryJson;
	private List<Task> tasks;//任务集合
	
	//临时字段
	private String tempHour;     //小时
	private String tempMinute;   //分钟
	private String tempSecond;   //秒
	private String tempWeek;     //星期
	private String tempDay;      //天
	private String tempMonth;    //月
	
	private TaskService taskService;//任务服务类
	private QuartzRuleUtil ruleUtil;//Quartz时间处理工具类
	
	private WeiboDao weiboDao;
	private UserDao userDao;
	private SearchWeiboDao searchWeiboDao;
	private RepostDao repostDao;
	private CommentDao commentDao;
	
	private CountSearchWeiboService countSearchWeiboService;
	private CountFollowerSexService countFollowerSexService;
	private CountFollowerDistrictService countFollowerDistrictService;
	private CountWeiboService countWeiboService;
	
	/**
	 * 进入基础任务列表页
	 * @return
	 * @throws Exception
	 */
	public String indexPre() throws Exception {
		return "indexPre";
	}
	/**
	 * 进入高级任务列表
	 * @return
	 * @throws Exception
	 */
	public String indexSuperPre() throws Exception {
		return "indexSuperPre";
	}
	
	/**
	 * 基础任务列表(为了统一使用前台展示模版,此处用了分页)
	 * @return
	 * @throws Exception
	 */
	public String searchBasic() throws Exception {
		try {
			tasks = taskService.selectBasic(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("rows", tasks);
			queryJson.put("total", 1);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 高级任务列表
	 * @return
	 * @throws Exception
	 */
	public String searchSuper() throws Exception {
		try {
			tasks = taskService.selectByCondition(example);
			if(tasks != null && tasks.size() > 0) {
				for(Task task : tasks) {
					parseConfigXml(task);
					convertXml(task);
				}
			}
			int total = taskService.selectCount(example);
			queryJson = new HashMap<String, Object>();
			queryJson.put("rows", tasks);
			queryJson.put("pageSum", example.getPageSum(total));
			queryJson.put("total", total);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 准备启动任务
	 * @return
	 * @throws Exception
	 */
	public String startPre() throws Exception {
		return "startPre";
	}
	
	/**
	 * 启动任务(第一次启动任务，需填写任务信息)
	 * @return
	 * @throws Exception
	 */
	public String startFirst() throws Exception {
		try {
			convertTime(bean);
			taskService.startFirst(bean);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return refreshTableDataOfParentWindow();
	}
	
	/**
	 * 启动任务
	 */
	public String start() throws Exception {
		try {
			taskService.start(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 暂停任务
	 */
	public String pause() throws Exception {
		try {
			taskService.pause(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 恢复任务
	 */
	public String regain() throws Exception {
		try {
			taskService.regain(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 还原任务
	 */
	public String revert() throws Exception {
		try {
			taskService.revert(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 删除任务
	 */
	public String delete() throws Exception {
		try {
			taskService.delete(bean);
			queryJson = new HashMap<String, Object>();
			queryJson.put("result", "success");
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "dataList";
	}
	
	/**
	 * 准备添加高级任务
	 * @return
	 * @throws Exception
	 */
	public String addPre() throws Exception {
		return "addPre";
	}
	
	/**
	 * 添加高级任务
	 * @return
	 * @throws Exception
	 */
	public String add() throws Exception {
		try {
			convertTime(bean);
			taskService.add(bean);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return refreshTableDataOfParentWindow();
	}
	
	/**
	 * 准备修改任务
	 */
	public String updatePre() throws Exception {
		try {
			bean = taskService.selectByPrimaryKey(bean.getTaskId());
			parseQuartzTime(bean.getTaskDetailTime());//解析Quartz时间
			parseConfigXml(bean);//解析config_xml
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "updatePre";
	}
	
	/**
	 * 修改任务
	 * @return
	 * @throws Exception
	 */
	public String update() throws Exception {
		try {
			convertTime(bean);
			taskService.update(bean);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return refreshTableDataOfParentWindow();
	}
	
	/**
	 * 查看任务
	 * @return
	 * @throws Exception
	 */
	public String look() throws Exception {
		try {
			bean = taskService.selectByPrimaryKey(bean.getTaskId());
			int dataNum = 0;
			List<Object> countList = new ArrayList<Object>();
			Object sex = new Object();
			//目前任务状态,任务抓取了多少条数据
			if(Task.TASK_TYPE_MY_WEIBO_CONTENT_COLLECT == bean.getTaskType() || Task.TASK_TYPE_MY_FRIEND_WEIBO_CONTENT_COLLECT == bean.getTaskType() || 
					Task.TASK_TYPE_THIRD_WEIBO_CONTENT_COLLECT == bean.getTaskType()){
				dataNum = this.getWeiboDao().getCount(bean.getTaskId());
				if(Task.TASK_TYPE_THIRD_WEIBO_CONTENT_COLLECT == bean.getTaskType()){
					parseConfigXml(bean);
					CountWeibo countWeibo = new CountWeibo();
					countWeibo.setTaskId(bean.getTaskId());
					countWeibo.setUid(bean.getUid());
					List<CountWeibo> list = this.getCountWeiboService().select(countWeibo);
					countList.addAll(list);
				}
			}else if(Task.TASK_TYPE_MY_WEIBO_COMMENT_COLLECT == bean.getTaskType() || Task.TASK_TYPE_MY_FRIEND_WEIBO_COMMENT_COLLECT == bean.getTaskType() ||
					Task.TASK_TYPE_THIRD_WEIBO_COMMENT_COLLECT == bean.getTaskType()){
				dataNum = this.getCommentDao().getCount(bean.getTaskId());
			}else if(Task.TASK_TYPE_MY_WEIBO_REPOST_COLLECT == bean.getTaskType() || Task.TASK_TYPE_MY_FRIEND_WEIBO_REPOST_COLLECT == bean.getTaskType() ||
					Task.TASK_TYPE_THIRD_WEIBO_REPOST_COLLECT == bean.getTaskType()){
				dataNum = this.getRepostDao().getCount(bean.getTaskId());
			}else if(Task.TASK_TYPE_THIRD_WEIBO_SEARCH_COLLECT == bean.getTaskType()){
				parseConfigXml(bean);
				String q = bean.getKeyWord();
				String lanmu = bean.getProgram();
				dataNum = this.getSearchWeiboDao().getCount(bean.getTaskId(),q,lanmu,null,null);
				if(Task.Task_STATE_FINISH == bean.getTaskState() || Task.TASK_STATE_NORMAL == bean.getTaskState()){
					CountSearchWeibo countSearchWeibo = new CountSearchWeibo();
					countSearchWeibo.setTaskId(bean.getTaskId());
					countSearchWeibo.setQ(q);
					countSearchWeibo.setLanmu(lanmu);
					List<CountSearchWeibo> list = this.getCountSearchWeiboService().select(countSearchWeibo);
					countList.addAll(list);
				}
			}else{
				dataNum = this.getUserDao().getCount(bean.getTaskId());
				//全量粉丝
				if(Task.TASK_TYPE_THIRD_WEIBO_FOLLOWER_COLLECT == bean.getTaskType()){
					parseConfigXml(bean);
					CountFollowerSex countFollowerSex = new CountFollowerSex();
					countFollowerSex.setTaskId(bean.getTaskId());
					countFollowerSex.setUid(bean.getUid());
					sex = this.getCountFollowerSexService().select(countFollowerSex);
					
					CountFollowerDistrict countFollowerDistrict = new CountFollowerDistrict();
					countFollowerDistrict.setTaskId(bean.getTaskId());
					countFollowerDistrict.setUid(bean.getUid());
					List<CountFollowerDistrict> list = this.getCountFollowerDistrictService().select(countFollowerDistrict);
					countList.addAll(list);
				}
			}
			
			
			queryJson = new HashMap<String, Object>();
			queryJson.put("taskType", bean.getTaskType());
			queryJson.put("taskState", bean.getTaskState());
			queryJson.put("dataNum", dataNum);
			queryJson.put("countList", countList);
			queryJson.put("sex", sex);
		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
		return "look";
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
	
	/**
	 * 字符串是否为空
	 * @param equalsStr
	 * @return
	 */
	private boolean isNotNull(String str, String equalsStr) {
		return (str != null && !equalsStr.equals(str));
	}
	
	/**
	 * 解析QuartzTime
	 * @param detailTime
	 */
	private void parseQuartzTime(String detailTime) {
		if(null != detailTime && detailTime.length() > 0){
			String [] temp = detailTime.split(":");
			String equalsStr = "00";
			if(isNotNull(temp[0],equalsStr)){ this.tempMonth=temp[0]; }
			if(isNotNull(temp[1],equalsStr)){ this.tempDay=temp[1];   }
			if(isNotNull(temp[2],equalsStr)){ this.tempWeek=temp[2];  }
			if(isNotNull(temp[3],equalsStr)){ this.tempHour=temp[3];  }
			if(isNotNull(temp[4],equalsStr)){ this.tempMinute=temp[4];}
			if(isNotNull(temp[5],equalsStr)){ this.tempSecond=temp[5];}
		}
	}
	
	/**
	 * 解析config_xml字段
	 * @param weibo
	 * @throws Exception
	 */
	private void parseConfigXml(Task task) throws Exception {
		if(task != null && task.getTaskConfigXml() != null && !"".equals(task.getTaskConfigXml())) {
			try {
				String xml = task.getTaskConfigXml();
				Document doc = null;
				doc = DocumentHelper.parseText(xml); // 将字符串转为XML
				Element root = doc.getRootElement(); // 获取根节点
			    if(task.getTaskType() == 9) {
			    	String q = root.elementText("q");
				    task.setKeyWord(q);
				    String lanmu = root.elementText("lanmu");
				    task.setProgram(lanmu);
				    String startTime = root.elementText("startTime");
				    task.setStartDateStr(startTime);
				    String endTime = root.elementText("endTime");
				    task.setEndDateStr(endTime);
			    } else {
			    	String uid = root.elementText("uid");
				    task.setUid(uid);
			    }
			} catch (DocumentException e) {
			    e.printStackTrace();
			} catch (Exception e) {
			    e.printStackTrace();
			}
		}
	}
	
	/**
	 * 将task的config_xml字段解析 转化为指定字符串，前台展示任务信息用
	 * @param task
	 * @throws Exception
	 */
	private void convertXml(Task task) throws Exception {
		if(task != null) {
			String keyWord = task.getKeyWord();
			String startDateStr = task.getStartDateStr();
			String endDateStr = task.getEndDateStr();
			String program = task.getProgram();
			String uid = task.getUid();
			StringBuffer xmlStr = new StringBuffer();
			if(isNotNull(keyWord, "") ) {
				xmlStr.append("关键词:").append(keyWord).append("栏目:").append(program).append("</br>")
				.append("开始时间:").append(startDateStr).append("</br>").append("结束时间:").append(endDateStr);
			} else if(isNotNull(uid, "")) {
				xmlStr.append("微博uid:").append(uid);
			}
			task.setXmlStr(xmlStr.toString());
		}
	}
	
	/**
	 * 转化任务的时间
	 * @param bean
	 * @throws Exception
	 */
	private void convertTime(Task bean) throws Exception {
		if(StringUtils.isBlank(tempHour) && StringUtils.isBlank(tempMinute) && StringUtils.isBlank(tempSecond)) {
			return;
		}
		bean.setTaskFrequency(ruleUtil.makeQuartzRule(bean.getTaskFrequencySort(), bean.getTaskEverySort(), 
				tempMonth, tempDay, tempWeek, tempHour, tempMinute, tempSecond,
				bean.getTaskBeginTime(),bean.getTaskEndTime()));
		//存储临时字段
		String taskDetailTime = ruleUtil.toDBString(tempMonth, tempDay, tempWeek, tempHour, tempMinute, tempSecond);
		bean.setTaskDetailTime(taskDetailTime);
	}
	

	public Weibo getWeibo() {
		return weibo;
	}

	public void setWeibo(Weibo weibo) {
		this.weibo = weibo;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public Task getBean() {
		return bean;
	}

	public void setBean(Task bean) {
		this.bean = bean;
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public void setRuleUtil(QuartzRuleUtil ruleUtil) {
		this.ruleUtil = ruleUtil;
	}

	public void setTempHour(String tempHour) {
		this.tempHour = tempHour;
	}

	public void setTempMinute(String tempMinute) {
		this.tempMinute = tempMinute;
	}

	public void setTempSecond(String tempSecond) {
		this.tempSecond = tempSecond;
	}

	public void setTempWeek(String tempWeek) {
		this.tempWeek = tempWeek;
	}

	public void setTempDay(String tempDay) {
		this.tempDay = tempDay;
	}

	public void setTempMonth(String tempMonth) {
		this.tempMonth = tempMonth;
	}

	public String getTempHour() {
		return tempHour;
	}

	public String getTempMinute() {
		return tempMinute;
	}

	public String getTempSecond() {
		return tempSecond;
	}

	public String getTempWeek() {
		return tempWeek;
	}

	public String getTempDay() {
		return tempDay;
	}

	public String getTempMonth() {
		return tempMonth;
	}

	public Task getExample() {
		return example;
	}

	public void setExample(Task example) {
		this.example = example;
	}
	public WeiboDao getWeiboDao() {
		return weiboDao;
	}
	public void setWeiboDao(WeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}
	public SearchWeiboDao getSearchWeiboDao() {
		return searchWeiboDao;
	}
	public void setSearchWeiboDao(SearchWeiboDao searchWeiboDao) {
		this.searchWeiboDao = searchWeiboDao;
	}
	public RepostDao getRepostDao() {
		return repostDao;
	}
	public void setRepostDao(RepostDao repostDao) {
		this.repostDao = repostDao;
	}
	public CommentDao getCommentDao() {
		return commentDao;
	}
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	public CountSearchWeiboService getCountSearchWeiboService() {
		return countSearchWeiboService;
	}
	public void setCountSearchWeiboService(CountSearchWeiboService countSearchWeiboService) {
		this.countSearchWeiboService = countSearchWeiboService;
	}
	public CountFollowerSexService getCountFollowerSexService() {
		return countFollowerSexService;
	}
	public void setCountFollowerSexService(CountFollowerSexService countFollowerSexService) {
		this.countFollowerSexService = countFollowerSexService;
	}
	public CountFollowerDistrictService getCountFollowerDistrictService() {
		return countFollowerDistrictService;
	}
	public void setCountFollowerDistrictService(
			CountFollowerDistrictService countFollowerDistrictService) {
		this.countFollowerDistrictService = countFollowerDistrictService;
	}
	public CountWeiboService getCountWeiboService() {
		return countWeiboService;
	}
	public void setCountWeiboService(CountWeiboService countWeiboService) {
		this.countWeiboService = countWeiboService;
	}
	
}
