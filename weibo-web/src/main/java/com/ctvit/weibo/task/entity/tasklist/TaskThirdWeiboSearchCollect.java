package com.ctvit.weibo.task.entity.tasklist;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.count.entity.CountSearchWeibo;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.util.TimeUtil;

/**
 * 微博关键词搜索任务
 * @author tqc
 *
 */
public class TaskThirdWeiboSearchCollect extends Task{
	
	/**
	 * 新浪接口默认返回最新50条数据
	 */
	private static int COUNT = 50;
	
	/**
	 * 新浪接口默认一次最多只返回1000条
	 */
	private static int LIMIT_COUNT = 1000;
	
	/**
	 * 新浪接口按照每页50条查询最多只能到20页，超出则返回错误
	 */
	private static int LIMIT_PAGE = 20;
	
	/**
	 * 搜索词标签名
	 */
	private static String TAG_Q = "q";
	
	/**
	 * 搜索栏目标签名
	 */
	private static String TAG_LANMU = "lanmu";
	
	/**
	 * 搜索开始时间标签名
	 */
	private static String TAG_STARTTIME = "startTime";
	
	/**
	 * 搜索结束时间标签名
	 */
	private static String TAG_ENDTIME = "endTime";
	
	/**
	 * 实际开始时间，如果出现异常则会记录此时间
	 */
	private static String TAG_INFACTSTARTTIME = "infactStartTime";
	
	private String q;
	
	private String lanmu;
	
	private int startTime;
	
	private int endTime;
	
	/**
	 * 递归用
	 */
	 int page = 1;
	 
	 int infactStartTime;
	 
	int infactEndTime;
	 
	 int distance = 0;
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		this.initTask(context);
		String taskId = (String)context.getJobDetail().getJobDataMap().get("taskId");
		Task task = this.getTaskService().selectByTaskId(taskId);
		String clientId = task.getAppKey();
		String clientSecret = task.getAppSecret();
		String redirectUri = task.getAppRedirectUri();
		String userName = task.getWeiboUserName();
		String password = task.getWeiboPassword();
		
		String accessToken = task.getWeiboToken();
		
		q = this.getConfigValue(TAG_Q, task.getTaskConfigXml());
		lanmu = this.getConfigValue(TAG_LANMU, task.getTaskConfigXml());
		String finalStartTime = this.getConfigValue(TAG_STARTTIME, task.getTaskConfigXml());
		String finalEndTime = this.getConfigValue(TAG_ENDTIME, task.getTaskConfigXml());
		startTime = TimeUtil.tranTimeToSecond(finalStartTime);
		endTime = TimeUtil.tranTimeToSecond(finalEndTime);
		
		boolean flag = true;
		int wait = 0;
		
		flag = this.checkIfTaskCanRun(task, flag);
		String infactStartTimeStr = this.getConfigValue(TAG_INFACTSTARTTIME, task.getTaskConfigXml());
		if(infactStartTimeStr!=null&&!"".equals(infactStartTimeStr)){
			infactStartTime = Integer.parseInt(infactStartTimeStr);
		}else{
			infactStartTime = startTime;
		}
		infactEndTime = endTime;
		distance = 0;
		int errorNum = 0;
		
		this.getSearchWeiboDao().createIndex(task.getTaskId());
		while(flag){
			try{
				try {
					Thread.sleep(new Long(wait));
				} catch (InterruptedException e1) {
				}
				//如果任务为等待则先更改为正常
				if(task.getTaskState() == Task.TASK_STATE_WAIT){
					task.setTaskState(Task.TASK_STATE_NORMAL);
					this.getTaskService().updateByTaskId(task);
				}
				
				try {
					flag = getStepResult(accessToken,task,finalStartTime,finalEndTime);
				} catch (WeiboException e) {
					int error = e.getErrorCode();
					//10006 token失效
					if(error==10006){
						accessToken = this.catchWeiboExceptionForAccessToken(clientId, clientSecret, redirectUri, userName, password, task);
					//10023 超过次数调用限制
					}else if(error==10022||error==10023||error==10024){
						wait = this.catchWeiboExceptionForWait(accessToken, task);
					}else{
						String taskConfigXml = this.setConfigValue(TAG_INFACTSTARTTIME, task.getTaskConfigXml(), infactStartTime+"");
						task.setTaskConfigXml(taskConfigXml);
						task.setTaskState(Task.TASK_STATE_UNUSUAL);
						flag = catchWeiboUnusualException(e,task);
					}
				}
				errorNum = 0;
			}catch(Exception e){
				errorNum++;
				wait = 1000;
				flag = true;
				if(errorNum==Task.TASK_ERROR_NUM){
					String taskConfigXml = this.setConfigValue(TAG_INFACTSTARTTIME, task.getTaskConfigXml(), infactStartTime+"");
					task.setTaskConfigXml(taskConfigXml);
					task.setTaskState(Task.TASK_STATE_UNUSUAL);
					flag = catchWeiboUnusualException(e,task);
				}
			}
		}
		this.endTask(task);
		
		//将mongodb数据统计到mysql里去
		Date countBeginTime = TimeUtil.tranTimeToDate(finalStartTime);
		Date countEndTime = TimeUtil.tranTimeToDate(finalEndTime);
		
		while(countBeginTime.getTime() <= countEndTime.getTime()){
			try{
				Date oneCountEndTime = new Date(countBeginTime.getTime() + TimeUtil.DAY);
				int countWeiboNum = this.getSearchWeiboDao().getCount(taskId, q, lanmu, countBeginTime, oneCountEndTime);
				int countUserNum = this.getSearchWeiboDao().getUserCount(taskId, q, lanmu, countBeginTime, oneCountEndTime);
				
				CountSearchWeibo countSearchWeibo = new CountSearchWeibo();
				countSearchWeibo.setTaskId(taskId);
				countSearchWeibo.setQ(q);
				countSearchWeibo.setLanmu(lanmu);
				countSearchWeibo.setCountTime(countBeginTime);
				countSearchWeibo.setCountWeiboNum(countWeiboNum);
				countSearchWeibo.setCountUserNum(countUserNum);
				try{
					this.getCountSearchWeiboService().insert(countSearchWeibo);
				}catch(Exception e){
					this.getCountSearchWeiboService().update(countSearchWeibo);
				}
				countBeginTime = oneCountEndTime;
			}catch(Exception e){
				continue;
			}
		}
		
	}
	
	/**
	 * 递归获取搜索结果
	 * @param infactStartTime
	 * @param infactEndTime
	 * @param distance
	 * @param page
	 * @param accessToken
	 * @return
	 * @throws WeiboException
	 */
	public boolean getStepResult(String accessToken,Task task,String finalStartTime,String finalEndTime) throws WeiboException{
		if(page<=LIMIT_PAGE&&infactStartTime<endTime){
			StatusWapper statusWapper = null;
			long totalNum = LIMIT_COUNT;
			try{
				 statusWapper = this.getSinaWeiboUtil().getSearchLimited(accessToken, page, COUNT, q, infactStartTime, infactEndTime);
				 totalNum = statusWapper.getTotalNumber();
			}catch(Exception e){
				totalNum = LIMIT_COUNT + 1;
			}
			if(statusWapper==null){
				return true;
			}
			if(totalNum<LIMIT_COUNT&&distance==0){
				//数据小于1000的处理方法
				List<Status> statusList = statusWapper.getStatuses();
				if(statusList.size()>0){
					//statusList入库mongodb,排重在mongodb中做
					for(Status status:statusList){
						this.getSearchWeiboDao().insert(status, task.getWeiboId(), task.getTaskId(), task.getTaskType(), lanmu, q, finalStartTime, finalEndTime);
					}
					page++;
				}else{
					return false;
				}
			}else if(totalNum<LIMIT_COUNT){
				List<Status> statusList = statusWapper.getStatuses();
				if(statusList.size()>0){
					//statusList入库mongodb,排重在mongodb中做
					for(Status status:statusList){
						this.getSearchWeiboDao().insert(status, task.getWeiboId(), task.getTaskId(), task.getTaskType(), lanmu, q, finalStartTime, finalEndTime);
					}
					page++;
				}else{
					page = 1;
					infactStartTime = infactEndTime;
					distance = distance * 2;
					infactEndTime = (infactStartTime + distance)>endTime?endTime:(infactStartTime + distance); 
				}
			}else if(totalNum>=LIMIT_COUNT&&(infactEndTime-infactStartTime)==1){
				List<Status> statusList = statusWapper.getStatuses();
				if(statusList.size()>0){
					//statusList入库mongodb,排重在mongodb中做
					for(Status status:statusList){
						this.getSearchWeiboDao().insert(status, task.getWeiboId(), task.getTaskId(), task.getTaskType(), lanmu, q, finalStartTime, finalEndTime);
					}
					page++;
				}else{
					page = 1;
					infactStartTime = infactEndTime;
					distance = distance * 2;
					infactEndTime = (infactStartTime + distance)>endTime?endTime:(infactStartTime + distance); 
				}
			}else{
				//数据大于1000则需要把时间变短
				if(distance!=0){
					distance = distance/2;
				}else{
					int totalTimeDistance = infactEndTime - infactStartTime;
					distance = totalTimeDistance/2;
				}
				if(distance==0){
					distance = 1;
				}
				infactEndTime = (infactStartTime + distance)>endTime?endTime:(infactStartTime + distance);
				getStepResult(accessToken,task,finalStartTime,finalEndTime);
			}
			return true;
		}else if(infactStartTime>=endTime){
			return false;
		}else{
			page = 1;
			infactStartTime = infactEndTime;
			distance = distance * 2;
			infactEndTime = (infactStartTime + distance)>endTime?endTime:(infactStartTime + distance); 
			return true;
		}
	}
	
	public String getQ() {
		return q;
	}

	public void setQ(String q) {
		this.q = q;
	}

	public String getLanmu() {
		return lanmu;
	}

	public void setLanmu(String lanmu) {
		this.lanmu = lanmu;
	}
	
	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public int getEndTime() {
		return endTime;
	}

	public void setEndTime(int endTime) {
		this.endTime = endTime;
	}
	
	public int getInfactStartTime() {
		return infactStartTime;
	 }

	 public void setInfactStartTime(int infactStartTime) {
		this.infactStartTime = infactStartTime;
	 }
}
