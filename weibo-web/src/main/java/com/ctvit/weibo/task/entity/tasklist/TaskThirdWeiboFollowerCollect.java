package com.ctvit.weibo.task.entity.tasklist;

import java.util.List;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.User;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONArray;
import weibo4j.org.json.JSONException;
import weibo4j.org.json.JSONObject;

import com.ctvit.weibo.count.entity.CountFollowerDistrict;
import com.ctvit.weibo.count.entity.CountFollowerSex;
import com.ctvit.weibo.task.entity.Task;

/**
 * 粉丝全量列表采集任务
 * @author tqc
 *
 */
public class TaskThirdWeiboFollowerCollect extends Task{
	
	/**
	 * 标签名
	 */
	private static String TAG_UID = "uid";
	
	private static String MAX_TIME = "maxTime";
	
	private static String CURSOR_UID = "cursorUid"; 
	
	private String uid;
	
	private String maxTime;
	
	private long cursorUid;
	
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
		
		uid = this.getConfigValue(TAG_UID, task.getTaskConfigXml());
		
		boolean flag = true;
		int wait = 0;
		
		flag = this.checkIfTaskCanRun(task, flag);
		
		/**
		 * 初始化
		 * max_time返回结果的时间戳游标，若指定此参数，则返回关注时间小于或等于max_time的粉丝，默认从当前时间开始算。返回结果中会得到next_cursor字段，表示下一页的max_time。next_cursor为0表示已经到记录末尾。
		 * cursor_uid排重ID，传上次获取时最后一个用户ID。
		 */
		maxTime = this.getConfigValue(MAX_TIME, task.getTaskConfigXml());
		if(maxTime==null||"".equals(maxTime)){
			maxTime = "0";
		}
		String cursorUidStr = this.getConfigValue(CURSOR_UID, task.getTaskConfigXml());
		if(cursorUidStr==null||"".equals(cursorUidStr)){
			cursorUid = 0;
		}
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
					JSONObject jsonObject = this.getSinaWeiboUtil().getAllFollowersById(accessToken, uid, maxTime, cursorUid);
					JSONArray ids = jsonObject.getJSONArray("ids");
					for(int i=0;i<ids.length();i++){
						String id = ids.getString(i);
						User user = null;
						try{
							user = this.getSinaWeiboUtil().showUserById(id, accessToken);
						}catch(WeiboException e){
							//用户不存在
							if(e.getErrorCode() == 20003){
								user = null;
							}
						}
						//将user入mongodb库
						if(user!=null){
							this.getUserDao().insert(user, task.getWeiboId(), taskId, task.getTaskType());
						}
						if(i==ids.length()-1){
							cursorUid = Long.parseLong(id);
						}
					}
					maxTime = jsonObject.getString("next_cursor");
					if("0".equals(maxTime)){
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
						String taskConfigXml = this.setConfigValue(MAX_TIME, task.getTaskConfigXml(), maxTime);
						taskConfigXml = this.setConfigValue(CURSOR_UID, taskConfigXml, cursorUid+"");
						task.setTaskConfigXml(taskConfigXml);
						task.setTaskState(Task.TASK_STATE_UNUSUAL);
						flag = catchWeiboUnusualException(e,task);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				errorNum = 0;
			}catch(Exception e){
				errorNum++;
				wait = 5000;
				flag = true;
				if(errorNum==Task.TASK_ERROR_NUM){
					String taskConfigXml = this.setConfigValue(MAX_TIME, task.getTaskConfigXml(), maxTime);
					taskConfigXml = this.setConfigValue(CURSOR_UID, taskConfigXml, cursorUid+"");
					task.setTaskConfigXml(taskConfigXml);
					task.setTaskState(Task.TASK_STATE_UNUSUAL);
					flag = catchWeiboUnusualException(e,task);
				}
			}
		}
		this.endTask(task);
		
		//对粉丝的统计 先统计性别 再统计地域
		int countMaleNum = this.getUserDao().getMaleCount(task.getTaskId());
		int countFemaleNum = this.getUserDao().getFemaleCount(task.getTaskId());
		//插入mysql
		CountFollowerSex countFollowerSex = new CountFollowerSex();
		countFollowerSex.setTaskId(task.getTaskId());
		countFollowerSex.setUid(uid);
		countFollowerSex.setCountMaleNum(countMaleNum);
		countFollowerSex.setCountFemaleNum(countFemaleNum);
		try{
			this.getCountFollowerSexService().insert(countFollowerSex);
		}catch(Exception e){
			this.getCountFollowerSexService().update(countFollowerSex);
		}
		
		//对地域的统计
		List<String> districtList = this.getUserDao().getDistrictList(task.getTaskId());
		for(String district:districtList){
			int countDistrictNum = this.getUserDao().getDistrictCount(task.getTaskId(), district);
			
			CountFollowerDistrict countFollowerDistrict = new CountFollowerDistrict();
			countFollowerDistrict.setTaskId(task.getTaskId());
			countFollowerDistrict.setUid(uid);
			countFollowerDistrict.setDistrict(district);
			countFollowerDistrict.setCountDistrictNum(countDistrictNum);
			try{
				this.getCountFollowerDistrictService().insert(countFollowerDistrict);
			}catch(Exception e){
				this.getCountFollowerDistrictService().update(countFollowerDistrict);
			}
		}
	}
	
	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getMaxTime() {
		return maxTime;
	}

	public void setMaxTime(String maxTime) {
		this.maxTime = maxTime;
	}

	public long getCursorUid() {
		return cursorUid;
	}

	public void setCursorUid(long cursorUid) {
		this.cursorUid = cursorUid;
	}

}
