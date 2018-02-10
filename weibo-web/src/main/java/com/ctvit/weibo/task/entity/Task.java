package com.ctvit.weibo.task.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;

import weibo4j.model.RateLimitStatus;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.count.service.CountFollowerDistrictService;
import com.ctvit.weibo.count.service.CountFollowerSexService;
import com.ctvit.weibo.count.service.CountSearchWeiboService;
import com.ctvit.weibo.count.service.CountWeiboService;
import com.ctvit.weibo.dao.SentWeiboMapper;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.mongodb.dao.CommentDao;
import com.ctvit.weibo.mongodb.dao.RepostDao;
import com.ctvit.weibo.mongodb.dao.SearchWeiboDao;
import com.ctvit.weibo.mongodb.dao.UserDao;
import com.ctvit.weibo.mongodb.dao.WeiboDao;
import com.ctvit.weibo.service.WeiboService;
import com.ctvit.weibo.task.service.QuartzManager;
import com.ctvit.weibo.task.service.TaskService;
import com.ctvit.weibo.util.Pagination;
import com.ctvit.weibo.util.SinaWeiboUtil;


public class Task extends Pagination implements Job{
	/**
	 * 任务出错次数
	 */
	public static int TASK_ERROR_NUM = 5;
	
	/**
	 *默认添加的任务数
	 */
	public static int TASK_DEFAULT_COUNT = 9;
	
	/**
	 * 我的微博内容采集
	 */
	public static int TASK_TYPE_MY_WEIBO_CONTENT_COLLECT = 0;
	
	/**
	 * 我的微博评论采集
	 */
	public static int TASK_TYPE_MY_WEIBO_COMMENT_COLLECT = 1;
	
	/**
	 * 我的微博转发采集
	 */
	public static int TASK_TYPE_MY_WEIBO_REPOST_COLLECT = 2;
	
	/**
	 * 我的微博账户信息采集
	 */
	public static int TASK_TYPE_MY_WEIBO_ACCOUNT_COLLECT = 3;
	
	/**
	 * 我的微博关注信息采集
	 */
	public static int TASK_TYPE_MY_WEIBO_FRIEND_COLLECT = 4;
	
	/**
	 * 我的微博粉丝信息采集
	 */
	public static int TASK_TYPE_MY_WEIBO_FOLLOWER_COLLECT = 5;
	
	/**
	 * 我的关注微博内容采集
	 */
	public static int TASK_TYPE_MY_FRIEND_WEIBO_CONTENT_COLLECT = 6;
	
	/**
	 * 我的关注微博评论采集
	 */
	public static int TASK_TYPE_MY_FRIEND_WEIBO_COMMENT_COLLECT = 7;
	
	/**
	 * 我的关注微博转发采集
	 */
	public static int TASK_TYPE_MY_FRIEND_WEIBO_REPOST_COLLECT = 8;
	
	/**
	 * 微博关键词搜索
	 */
	public static int TASK_TYPE_THIRD_WEIBO_SEARCH_COLLECT = 9;
	
	/**
	 * 第三方微博转发采集
	 */
	public static int TASK_TYPE_THIRD_WEIBO_REPOST_COLLECT = 10;
	
	/**
	 * 第三方微博内容采集
	 */
	public static int TASK_TYPE_THIRD_WEIBO_CONTENT_COLLECT = 11;
	
	/**
	 * 粉丝全量列表采集
	 */
	public static int TASK_TYPE_THIRD_WEIBO_FOLLOWER_COLLECT = 12;
	
	/**
	 * 第三方微博评论采集
	 */
	public static int TASK_TYPE_THIRD_WEIBO_COMMENT_COLLECT = 13;
	
	/**
	 * 第三方微博账户信息采集
	 */
	public static int TASK_TYPE_THIRD_WEIBO_ACCOUNT_COLLECT = 14;
	
	
	/**
	 * 任务停止状态
	 */
	public static int TASK_STATE_STOP = 0;
	
	/**
	 * 任务运行状态
	 */
	public static int TASK_STATE_NORMAL = 1;
	
	/**
	 * 任务暂停状态
	 */
	public static int TASK_STATE_PAUSE = 2;
	
	/**
	 * 任务等待状态
	 */
	public static int TASK_STATE_WAIT = 3;
	
	/**
	 * 任务删除状态
	 */
	public static int TASK_STATE_DELETE = 4;
	
	/**
	 * 任务就绪状态
	 */
	public static int TASK_STATE_READY = 7;
	
	/**
	 * 任务异常状态
	 */
	public static int TASK_STATE_UNUSUAL = 5;
	
	/**
	 * 任务完成状态
	 */
	public static int Task_STATE_FINISH = 6;
	
	/**
	 * 任务为永远执行
	 */
	public static int TASK_FOREVER = 0;
	
	/**
	 * 任务为执行一次
	 */
	public static int TASK_ONE_TIME = 1;
	
	/**
	 * 任务为频繁执行
	 */
	public static int TASK_FREQUENCY = 2;
	
	/**
	 * 任务基础权限
	 */
	public static int TASK_LEVEL_BASIC = 0;
	
	/**
	 * 任务高级权限
	 */
	public static int TASK_LEVEL_SUPER = 1;
	
	
	private String taskId;

    private String weiboId;

    private Integer taskType;

    private Date taskCreateTime;

    private Date taskBeginTime;
    private String taskBeginTimeStr;

    private Date taskEndTime;
    private String taskEndTimeStr;

    private Integer taskForever;

    private String taskFrequency;

    private Integer taskState;

    private String taskConfigXml;
    
    private Integer taskLevel;
	
	private SinaWeiboUtil sinaWeiboUtil;
	
	private TaskService taskService;
	
	private WeiboService weiboService;
	
	private QuartzManager quartzManager;
	
	private WeiboDao weiboDao;
	
	private CommentDao commentDao;
	
	private RepostDao repostDao;
	
	private UserDao userDao;
	
	private SearchWeiboDao searchWeiboDao;
	private SentWeiboMapper sentDao;
	
	private CountSearchWeiboService countSearchWeiboService;
	
	private CountFollowerSexService countFollowerSexService;
	
	private CountFollowerDistrictService countFollowerDistrictService;
	
	private CountWeiboService countWeiboService;
	
	private String weiboToken;
	
	private String weiboUserName;
	
	private String weiboPassword;
	
	private String weiboUid;
	
	private String appKey;
	
	private String appSecret;
	
	private String appRedirectUri;
	
	private String taskFrequencySort;//定时类型 ->定点/间隔/一次'1':'定点','2':'间隔','3':'一次'
	private String taskEverySort;//定时频率->每天/每周/每月'1':'每天','2':'每周','3':'每月'
	private boolean forever;//是否永远执行
	private String taskDetailTime;//详细时间
	private String keyWord;//关键词
	private Date startDate;//开始时间
	private String startDateStr;//开始时间字符串形式
	private Date endDate;//结束时间
	private String endDateStr;//结束时间字符串形式
	private String program;//栏目
	private String uid;//微博uid
	private String xmlStr;
	private String appLevel;//应用等级 0基础 1高级
	
	//定时类型 ->定点/间隔/一次'1':'定点','2':'间隔','3':'一次'
	public static final String TASK_FREQUENCY_SORT_POINT = "1";
	public static final String TASK_FREQUENCY_SORT_INTERVAL = "2";
	public static final String TASK_FREQUENCY_SORT_ONCE = "3";
	
	//定时频率->每天/每周/每月'1':'每天','2':'每周','3':'每月'
	public static final String TASK_EVERY_DAY = "1";
	public static final String TASK_EVERY_WEEK = "2";
	public static final String TASK_EVERY_MONTH = "3";
	
	
	
	private Logger logger = Logger.getLogger(Task.class);
	
	public void execute(JobExecutionContext context) throws JobExecutionException{
		
	}
	
	/**
	 * 获取配置文件中的某个标签的值
	 * <config><key></key></config>
	 * @param key
	 * @param taskConfigXml
	 * @return
	 */
	public String getConfigValue(String key,String taskConfigXml){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(taskConfigXml);
		} catch (Exception e) {
			doc = DocumentHelper.createDocument();
			Element config = doc.addElement("config");
			config.addElement(key);
			return doc.getRootElement().elementText(key);
		}
		return doc.getRootElement().elementText(key);
	}
	
	/**
	 * 更新配置文件中的某个标签的值
	 * <config><key></key></config>
	 * @param key
	 * @param taskConfigXml
	 * @param value
	 * @return
	 */
	public String setConfigValue(String key,String taskConfigXml,String value){
		Document doc = null;
		try {
			doc = DocumentHelper.parseText(taskConfigXml);
		} catch (Exception e) {
			doc = DocumentHelper.createDocument();
			Element config = doc.addElement("config");
			Element keyElement = config.addElement(key);
			keyElement.setText(value);
			return doc.asXML();
		}
		try{
			doc.getRootElement().element(key).setText(value);
		}catch(Exception e){
			Element keyElement = doc.getRootElement().addElement(key);
			keyElement.setText(value);
		}
		return doc.asXML();
	}
	
	
	/**
	 * 任务初始化加载服务类
	 * @param context
	 */
	public void initTask(JobExecutionContext context){
		this.setSinaWeiboUtil((SinaWeiboUtil)context.getJobDetail().getJobDataMap().get("sinaWeiboUtil"));
		this.setTaskService((TaskService)context.getJobDetail().getJobDataMap().get("taskService"));
		this.setWeiboService((WeiboService)context.getJobDetail().getJobDataMap().get("weiboService"));
		this.setQuartzManager((QuartzManager)context.getJobDetail().getJobDataMap().get("quartzManager"));
		this.setWeiboDao((WeiboDao)context.getJobDetail().getJobDataMap().get("weiboDao"));
		this.setCommentDao((CommentDao)context.getJobDetail().getJobDataMap().get("commentDao"));
		this.setRepostDao((RepostDao)context.getJobDetail().getJobDataMap().get("repostDao"));
		this.setUserDao((UserDao)context.getJobDetail().getJobDataMap().get("userDao"));
		this.setSearchWeiboDao((SearchWeiboDao)context.getJobDetail().getJobDataMap().get("searchWeiboDao"));
		this.setCountSearchWeiboService((CountSearchWeiboService)context.getJobDetail().getJobDataMap().get("countSearchWeiboService"));
		this.setCountFollowerSexService((CountFollowerSexService)context.getJobDetail().getJobDataMap().get("countFollowerSexService"));
		this.setCountFollowerDistrictService((CountFollowerDistrictService)context.getJobDetail().getJobDataMap().get("countFollowerDistrictService"));
		this.setCountWeiboService((CountWeiboService)context.getJobDetail().getJobDataMap().get("countWeiboService"));
		this.setSentDao((SentWeiboMapper)context.getJobDetail().getJobDataMap().get("sentDao"));
	}
	
	
	/**
	 * 任务由于token失效出错
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param userName
	 * @param password
	 * @param weiboId
	 * @return
	 */
	public String catchWeiboExceptionForAccessToken(String clientId,String clientSecret,String redirectUri,String userName, String password,Task task){
		String accessToken = this.getSinaWeiboUtil().getToken(clientId, clientSecret, redirectUri, userName, password);
		Weibo weibo = new Weibo();
		weibo.setWeiboId(task.getWeiboId());
		weibo.setWeiboToken(accessToken);
		this.getWeiboService().updateTokenByWeiboId(weibo);
		return accessToken;
	}
	
	/**
	 * 任务由于有效次数超出，需要等待
	 * @param accessToken
	 * @param task
	 * @return
	 */
	public int catchWeiboExceptionForWait(String accessToken,Task task){
		int wait = 1000 * 10;
		try {
			RateLimitStatus rateLimitStatus = this.getSinaWeiboUtil().getAccountRateLimitStatus(accessToken);
			wait = rateLimitStatus.getResetTimeInSeconds() * 1000;
			//更新任务的状态
			task.setTaskState(Task.TASK_STATE_WAIT);
			this.getTaskService().updateByTaskId(task);
		} catch (WeiboException e1) {
			wait = 1000 * 10;
		}
		logger.debug(task.getTaskId() + ":wait=" + wait);
		return wait;
	}
	
	/**
	 * 任务出现异常，将任务删除
	 * @param task
	 */
	public boolean catchWeiboUnusualException(Exception exception,Task task){
		exception.printStackTrace();
		logger.error("cause unusual exception");
		this.getTaskService().updateByTaskId(task);
		try {
			this.getQuartzManager().removeJob(task.getTaskId());
		} catch (SchedulerException e) {
			logger.error("cause unusual exception to removeJob " + task.getTaskId() + " error" );
		}
		return false;
	}
	
	/**
	 * 检查任务是否能够执行
	 * @return
	 */
	public boolean checkIfTaskCanRun(Task task,boolean flag){
		//首先看任务是否在允许执行的时间范围内
		Date taskBeginTime = task.getTaskBeginTime();
		Date taskEndTime = task.getTaskEndTime();
		if(taskBeginTime!=null&&taskEndTime!=null){
			Date nowTime = new Date();
			if(nowTime.getTime() < taskBeginTime.getTime()){
				flag = false;
			}
			if(nowTime.getTime() > taskEndTime.getTime()){
				flag = false;
				try {
					this.getQuartzManager().removeJob(task.getTaskId());
				} catch (SchedulerException e) {
					e.printStackTrace();
				}
				task.setTaskState(Task.TASK_STATE_STOP);
				this.getTaskService().updateByTaskId(task);
			}
		}
		
		//看任务的状态是否为等待
		if(task.getTaskState()!=Task.TASK_STATE_NORMAL){
			flag = false;
		}
		return flag;
	}
	
	
	/**
	 * 任务结束后 如果是一次性任务，则更改为任务完成状态
	 * @param task
	 */
	public void endTask(Task task){
		if(Task.TASK_ONE_TIME==task.getTaskForever()&&Task.TASK_STATE_NORMAL == task.getTaskState() ){
			task.setTaskState(Task.Task_STATE_FINISH);
			this.getTaskService().updateByTaskId(task);
		}
	}
	
	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getWeiboId() {
		return weiboId;
	}

	public void setWeiboId(String weiboId) {
		this.weiboId = weiboId;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Date getTaskCreateTime() {
		return taskCreateTime;
	}

	public void setTaskCreateTime(Date taskCreateTime) {
		this.taskCreateTime = taskCreateTime;
	}

	public Date getTaskBeginTime() {
		return taskBeginTime;
	}

	public void setTaskBeginTime(Date taskBeginTime) {
		this.taskBeginTime = taskBeginTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

	public Integer getTaskForever() {
		return taskForever;
	}

	public void setTaskForever(Integer taskForever) {
		this.taskForever = taskForever;
	}

	public String getTaskFrequency() {
		return taskFrequency;
	}

	public void setTaskFrequency(String taskFrequency) {
		this.taskFrequency = taskFrequency;
	}

	public Integer getTaskState() {
		return taskState;
	}

	public void setTaskState(Integer taskState) {
		this.taskState = taskState;
	}

	public String getTaskConfigXml() {
		return taskConfigXml;
	}

	public void setTaskConfigXml(String taskConfigXml) {
		this.taskConfigXml = taskConfigXml;
	}

	public SinaWeiboUtil getSinaWeiboUtil() {
		return sinaWeiboUtil;
	}

	public void setSinaWeiboUtil(SinaWeiboUtil sinaWeiboUtil) {
		this.sinaWeiboUtil = sinaWeiboUtil;
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

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public String getWeiboUid() {
		return weiboUid;
	}

	public void setWeiboUid(String weiboUid) {
		this.weiboUid = weiboUid;
	}

	public WeiboService getWeiboService() {
		return weiboService;
	}

	public void setWeiboService(WeiboService weiboService) {
		this.weiboService = weiboService;
	}

	public QuartzManager getQuartzManager() {
		return quartzManager;
	}

	public void setQuartzManager(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

	public Integer getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(Integer taskLevel) {
		this.taskLevel = taskLevel;
	}

	public boolean isForever() {
		return forever;
	}

	public void setForever(boolean forever) {
		this.forever = forever;
	}

	public String getTaskDetailTime() {
		return taskDetailTime;
	}

	public void setTaskDetailTime(String taskDetailTime) {
		this.taskDetailTime = taskDetailTime;
	}

	public String getTaskFrequencySort() {
		return taskFrequencySort;
	}

	public void setTaskFrequencySort(String taskFrequencySort) {
		this.taskFrequencySort = taskFrequencySort;
	}

	public String getTaskEverySort() {
		return taskEverySort;
	}

	public void setTaskEverySort(String taskEverySort) {
		this.taskEverySort = taskEverySort;
	}

	public String getTaskBeginTimeStr() {
		if(taskBeginTime != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(taskBeginTime);
		}
		return taskBeginTimeStr;
	}

	public void setTaskBeginTimeStr(String taskBeginTimeStr) {
		this.taskBeginTimeStr = taskBeginTimeStr;
	}

	public String getTaskEndTimeStr() {
		if(taskEndTime != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(taskEndTime);
		}
		return taskEndTimeStr;
	}

	public void setTaskEndTimeStr(String taskEndTimeStr) {
		this.taskEndTimeStr = taskEndTimeStr;
	}

	public String getKeyWord() {
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getStartDateStr() {
		if(startDate != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(startDate);
		}
		return startDateStr;
	}

	public void setStartDateStr(String startDateStr) {
		this.startDateStr = startDateStr;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getEndDateStr() {
		if(endDate != null) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(endDate);
		}
		return endDateStr;
	}

	public void setEndDateStr(String endDateStr) {
		this.endDateStr = endDateStr;
	}

	public String getProgram() {
		return program;
	}

	public void setProgram(String program) {
		this.program = program;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public String getXmlStr() {
		return xmlStr;
	}

	public void setXmlStr(String xmlStr) {
		this.xmlStr = xmlStr;
	}

	public String getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(String appLevel) {
		this.appLevel = appLevel;
	}

	public WeiboDao getWeiboDao() {
		return weiboDao;
	}

	public void setWeiboDao(WeiboDao weiboDao) {
		this.weiboDao = weiboDao;
	}

	public CommentDao getCommentDao() {
		return commentDao;
	}

	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}

	public RepostDao getRepostDao() {
		return repostDao;
	}

	public void setRepostDao(RepostDao repostDao) {
		this.repostDao = repostDao;
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

	public SentWeiboMapper getSentDao() {
		return sentDao;
	}

	public void setSentDao(SentWeiboMapper sentDao) {
		this.sentDao = sentDao;
	}

	
}
