package com.ctvit.weibo.task.entity.tasklist;


import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.task.entity.Task;

/**
 * 我的微博账户信息采集
 * @author tqc
 *
 */
public class TaskMyWeiboAccountCollect extends Task{
	
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
		
		String uid = task.getWeiboUid();
		String accessToken = task.getWeiboToken();
		boolean flag = true;
		int wait = 0;
		int errorNum = 0;
		
		flag = this.checkIfTaskCanRun(task, flag);
		
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
					User user = this.getSinaWeiboUtil().showUserById(uid, accessToken);
					//user入mongodb库
					this.getUserDao().insert(user, task.getWeiboId(), taskId, task.getTaskType());
					flag = false;
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
	}

}
