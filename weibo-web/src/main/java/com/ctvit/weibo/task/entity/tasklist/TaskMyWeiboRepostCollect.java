package com.ctvit.weibo.task.entity.tasklist;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.util.ConstantParam;

/**
 * 我的微博转发采集任务
 * @author tqc
 *
 */
public class TaskMyWeiboRepostCollect extends Task{
	
	/**
	 * 新浪接口默认返回最新200条数据
	 */
	private static int COUNT = 200;

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
		boolean flag = true;
		int wait = 0;
		
		flag = this.checkIfTaskCanRun(task, flag);
		
		if(flag){
			//从mongodb中查询出未处理的微博数据
			List<String> weiboDataIds = this.getWeiboDao().select(task.getWeiboId(), Task.TASK_TYPE_MY_WEIBO_CONTENT_COLLECT, null, ConstantParam.MONGODB_REPOST);
			for(String weiboDataId:weiboDataIds){
				int page = 1;
				flag = true;
				int errorNum = 0;
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
							StatusWapper statusWapper = this.getSinaWeiboUtil().getRepostTimeline(weiboDataId, accessToken, page, COUNT);
							List<Status> statusList = statusWapper.getStatuses();
							if(statusList.size()>0){
								//statusList入库mongodb,排重在mongodb中做
								for(Status status:statusList){
									this.getRepostDao().insert(status, task.getWeiboId(), taskId, task.getTaskType());
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
				this.getWeiboDao().update(task.getWeiboId(), weiboDataId, ConstantParam.MONGODB_REPOST);
			}
			this.endTask(task);
		}
	}
}
