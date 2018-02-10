package com.ctvit.weibo.task.entity.tasklist;

import java.util.Date;
import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.count.entity.CountWeibo;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.TimeUtil;

/**
 * 第三方微博内容采集任务
 * @author tqc
 *
 */
public class TaskThirdWeiboContentCollect extends Task{
	
	/**
	 * 新浪接口默认返回最新100条数据
	 */
	private static int COUNT = 100;
	/**
	 * 记录下一次开始的sinceId
	 */
	private static String TAG_SINCEID = "sinceId";
	
	/**
	 * uid标签
	 */
	private static String TAG_UID = "uid";
	
	private long sinceId;
	
	private String uid;

	public long getSinceId() {
		return sinceId;
	}

	public void setSinceId(long sinceId) {
		this.sinceId = sinceId;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

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
		String sinceIdStr = this.getConfigValue(TAG_SINCEID,task.getTaskConfigXml());
		if(sinceIdStr!=null&&!"".equals(sinceIdStr)){
			sinceId = Long.parseLong(sinceIdStr);
		}
		String uidStr = this.getConfigValue(TAG_UID,task.getTaskConfigXml());
		if(uidStr!=null&&!"".equals(uidStr)){
			uid = uidStr;
		}
		boolean flag = true;
		int wait = 0;
		int page = 1;//数据从第一页开始取
		int errorNum = 0;
		
		flag = this.checkIfTaskCanRun(task, flag);
		
		Date countBeginDate = new Date();
		Date countEndDate = new Date();
		
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
					StatusWapper statusWapper = this.getSinaWeiboUtil().getUserTimelineByUid(uid, accessToken, page, COUNT, sinceId);
					List<Status> statusList = statusWapper.getStatuses();
					if(statusList.size()>0){
						if(page==1){
							//记录最大的微博id为sinceId，更新task
							sinceId = Long.parseLong(statusList.get(0).getId());
							countEndDate = statusList.get(0).getCreatedAt();
							String taskConfigXml = this.setConfigValue(TAG_SINCEID, task.getTaskConfigXml(), sinceId+"");
							task.setTaskConfigXml(taskConfigXml);
							this.getTaskService().updateByTaskId(task);
						}
						//statusList入库mongodb,排重在mongodb中做
						for(Status status:statusList){
							this.getWeiboDao().insert(status, task.getWeiboId(), taskId, task.getTaskType(), ConstantParam.NO_COUNT, ConstantParam.NO_COUNT);
							countBeginDate = status.getCreatedAt();
						}
						page++;
					}else{
						flag = false;
					}
					
				} catch (WeiboException e) {
					int error = e.getErrorCode();
					//10006 token失效
					if(error==10006){
						accessToken = this.catchWeiboExceptionForAccessToken(clientId, clientSecret, redirectUri, userName, password, task);
					//10023 超过次数调用限制
					}else if(error==10022||error==10023||error==10024){
						wait = this.catchWeiboExceptionForWait(accessToken, task);
					}else{
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
					task.setTaskState(Task.TASK_STATE_UNUSUAL);
					flag = catchWeiboUnusualException(e,task);
				}
			}
		}
		this.endTask(task);
		
		//根据countBeginDate和countEndDate统计
		countBeginDate = TimeUtil.tranDateToDate(countBeginDate);
		countEndDate = TimeUtil.tranDateToDate(countEndDate);
		
		while(countBeginDate.getTime()<=countEndDate.getTime()){
			try{
				Date oneCountEndDate = new Date(countBeginDate.getTime() + TimeUtil.DAY);
				int[] dataNum = this.getWeiboDao().getCountWeibo(taskId, uid, countBeginDate, oneCountEndDate);
				int countWeiboNum = dataNum[0];
				int countCommentNum = dataNum[1];
				int countRepostNum = dataNum[2];
				
				CountWeibo countWeibo = new CountWeibo();
				countWeibo.setTaskId(task.getTaskId());
				countWeibo.setUid(uid);
				countWeibo.setCountTime(countBeginDate);
				countWeibo.setCountWeiboNum(countWeiboNum);
				countWeibo.setCountCommentNum(countCommentNum);
				countWeibo.setCountRepostNum(countRepostNum);
				
				try{
					this.getCountWeiboService().insert(countWeibo);
				}catch(Exception e){
					this.getCountWeiboService().update(countWeibo);
				}
				countBeginDate = oneCountEndDate;
			}catch(Exception e){
				continue;
			}
		}
		
	}

	

}
