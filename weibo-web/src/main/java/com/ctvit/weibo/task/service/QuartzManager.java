package com.ctvit.weibo.task.service;

import java.text.ParseException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;

import com.ctvit.weibo.count.service.CountFollowerDistrictService;
import com.ctvit.weibo.count.service.CountFollowerSexService;
import com.ctvit.weibo.count.service.CountSearchWeiboService;
import com.ctvit.weibo.count.service.CountWeiboService;
import com.ctvit.weibo.dao.SentWeiboMapper;
import com.ctvit.weibo.mongodb.dao.CommentDao;
import com.ctvit.weibo.mongodb.dao.RepostDao;
import com.ctvit.weibo.mongodb.dao.SearchWeiboDao;
import com.ctvit.weibo.mongodb.dao.UserDao;
import com.ctvit.weibo.mongodb.dao.WeiboDao;
import com.ctvit.weibo.service.WeiboService;
import com.ctvit.weibo.util.SinaWeiboUtil;

public class QuartzManager {
	private static String JOB_GROUP_NAME_PRE = "group";
	private static String TRIGGER_GROUP_NAME_PRE = "trigger";
	private Scheduler scheduler = null;
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
	private Logger logger = Logger.getLogger(QuartzManager.class);
	
	public Scheduler getScheduler() {
        if(scheduler==null){
        	try {
        		SchedulerFactory sf = new StdSchedulerFactory();
				scheduler = sf.getScheduler();
			} catch (SchedulerException e) {
				e.printStackTrace();
			}
        }
        return scheduler;
    }
	
	/**
	 * 添加一个定时任务
	 * @param jobName
	 * @param job
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void addJob(String jobName,Job job,String time) throws SchedulerException, ParseException{  
		Scheduler scheduler = getScheduler();
		if(scheduler.getJobDetail(jobName, JOB_GROUP_NAME_PRE+jobName)==null){
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME_PRE+jobName, job.getClass());//任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("taskId", jobName);
			jobDetail.getJobDataMap().put("sinaWeiboUtil", sinaWeiboUtil);
			jobDetail.getJobDataMap().put("taskService", taskService);
			jobDetail.getJobDataMap().put("weiboService", weiboService);
			jobDetail.getJobDataMap().put("quartzManager", quartzManager);
			jobDetail.getJobDataMap().put("weiboDao", weiboDao);
			jobDetail.getJobDataMap().put("commentDao", commentDao);
			jobDetail.getJobDataMap().put("repostDao", repostDao);
			jobDetail.getJobDataMap().put("userDao", userDao);
			jobDetail.getJobDataMap().put("searchWeiboDao", searchWeiboDao);
			jobDetail.getJobDataMap().put("countSearchWeiboService", countSearchWeiboService);
			jobDetail.getJobDataMap().put("countFollowerSexService", countFollowerSexService);
			jobDetail.getJobDataMap().put("countFollowerDistrictService", countFollowerDistrictService);
			jobDetail.getJobDataMap().put("countWeiboService", countWeiboService);
			//触发器   
			CronTrigger trigger = new CronTrigger(jobName, TRIGGER_GROUP_NAME_PRE+jobName);//触发器名,触发器组   
			trigger.setCronExpression(time);//触发器时间设定   
			getScheduler().scheduleJob(jobDetail,trigger);  
			//启动   
			if(!getScheduler().isShutdown())  
				getScheduler().start();  
		}else{
			logger.debug(jobName + "***********任务已经在调度器里执行了*************");
		}
	}
	
	/**
	 * 添加一个一次性任务
	 * @param jobName
	 * @param job
	 * @throws SchedulerException
	 */
	public void addOneTimeJob(String jobName,Job job) throws SchedulerException{
		Scheduler scheduler = getScheduler();
		if(scheduler.getJobDetail(jobName, JOB_GROUP_NAME_PRE+jobName)==null){
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME_PRE+jobName, job.getClass());//任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("taskId", jobName);
			jobDetail.getJobDataMap().put("sinaWeiboUtil", sinaWeiboUtil);
			jobDetail.getJobDataMap().put("taskService", taskService);
			jobDetail.getJobDataMap().put("weiboService", weiboService);
			jobDetail.getJobDataMap().put("quartzManager", quartzManager);
			jobDetail.getJobDataMap().put("weiboDao", weiboDao);
			jobDetail.getJobDataMap().put("commentDao", commentDao);
			jobDetail.getJobDataMap().put("repostDao", repostDao);
			jobDetail.getJobDataMap().put("userDao", userDao);
			jobDetail.getJobDataMap().put("searchWeiboDao", searchWeiboDao);
			jobDetail.getJobDataMap().put("countSearchWeiboService", countSearchWeiboService);
			jobDetail.getJobDataMap().put("countFollowerSexService", countFollowerSexService);
			jobDetail.getJobDataMap().put("countFollowerDistrictService", countFollowerDistrictService);
			jobDetail.getJobDataMap().put("countWeiboService", countWeiboService);
			SimpleTrigger simpleTrigger = new SimpleTrigger(jobName, TRIGGER_GROUP_NAME_PRE+jobName, new Date(new Date().getTime() + 1000));
			getScheduler().scheduleJob(jobDetail,simpleTrigger);
			//启动   
			if(!getScheduler().isShutdown())  
				getScheduler().start();
		}else{
			logger.debug(jobName + "***********任务已经在调度器里执行了*************");
		}
	}
	
	/**
	 * 添加一个微博发送定时任务
	 * @param jobName
	 * @param job
	 * @param time
	 * @throws SchedulerException
	 * @throws ParseException
	 */
	public void addSendWeiboJob(String jobName,Job job,Date startTime) throws SchedulerException, ParseException{  
		Scheduler scheduler = getScheduler();
		if(scheduler.getJobDetail(jobName, JOB_GROUP_NAME_PRE+jobName)==null){
			JobDetail jobDetail = new JobDetail(jobName, JOB_GROUP_NAME_PRE+jobName, job.getClass());//任务名，任务组，任务执行类
			jobDetail.getJobDataMap().put("id", jobName);
			jobDetail.getJobDataMap().put("sentDao", sentDao);
			jobDetail.getJobDataMap().put("sinaWeiboUtil", sinaWeiboUtil);
			jobDetail.getJobDataMap().put("weiboService", weiboService);
			//触发器   
			SimpleTrigger simpleTrigger = new SimpleTrigger(jobName,TRIGGER_GROUP_NAME_PRE+jobName);
			simpleTrigger.setStartTime(startTime);
			simpleTrigger.setRepeatInterval(0);
			simpleTrigger.setRepeatCount(0);
     		getScheduler().scheduleJob(jobDetail,simpleTrigger);  
			//启动   
			if(!getScheduler().isShutdown())  
				getScheduler().start();  
		}else{
			logger.debug(jobName + "***********任务已经在调度器里执行了*************");
		}
	}
	
	     
   /**
    * 修改一个任务的触发时间
    * @param jobName 
    * @param time 
    * @throws SchedulerException 
    * @throws ParseException 
    */  
	public void modifyJobTime(String jobName,String time)   
                                  throws SchedulerException, ParseException{  
		Trigger trigger =  getScheduler().getTrigger(jobName,TRIGGER_GROUP_NAME_PRE+jobName);  
		if(trigger != null){  
			CronTrigger  ct = (CronTrigger)trigger;  
			ct.setCronExpression(time);  
			getScheduler().resumeTrigger(jobName,TRIGGER_GROUP_NAME_PRE+jobName);  
		}  
	}  
	     
	     
   /** 
    * 移除一个任务
    * @param jobName 
    * @throws SchedulerException 
    */  
	public void removeJob(String jobName)   
                               throws SchedulerException{  
	   	getScheduler().pauseTrigger(jobName,TRIGGER_GROUP_NAME_PRE+jobName);//停止触发器   
	   	getScheduler().unscheduleJob(jobName,TRIGGER_GROUP_NAME_PRE+jobName);//移除触发器   
	   	getScheduler().deleteJob(jobName,JOB_GROUP_NAME_PRE+jobName);//删除任务   
   	} 
	
	/**
	 * 暂停一个任务
	 * @param jobName
	 * @throws SchedulerException
	 */
	public void pauseJob(String jobName) throws SchedulerException{
		getScheduler().pauseJob(jobName, JOB_GROUP_NAME_PRE+jobName);
	}
	
	/**
	 * 恢复一个任务
	 * @param jobName
	 * @throws SchedulerException
	 */
	public void resumeJob(String jobName) throws SchedulerException{
		getScheduler().resumeJob(jobName, JOB_GROUP_NAME_PRE+jobName);
	}
	
	
	public SinaWeiboUtil getSinaWeiboUtil() {
		return sinaWeiboUtil;
	}

	public void setSinaWeiboUtil(SinaWeiboUtil sinaWeiboUtil) {
		this.sinaWeiboUtil = sinaWeiboUtil;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
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
