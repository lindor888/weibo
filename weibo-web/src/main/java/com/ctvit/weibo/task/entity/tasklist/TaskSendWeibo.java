package com.ctvit.weibo.task.entity.tasklist;

import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.entity.SentWeibo;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.util.ConstantParam;

public class TaskSendWeibo extends Task {

	
	public void execute(JobExecutionContext context) throws JobExecutionException {
		this.initTask(context);
		String id = (String) context.getJobDetail().getJobDataMap().get("id");
		SentWeibo sentWeibo = this.getSentDao().selectByPrimaryKey(id);
		if(sentWeibo != null) {
			if(StringUtils.isNotBlank(sentWeibo.getUserId())) {
				sentWeibo.setWeiboUid(sentWeibo.getUserId());
			}
		}
		Weibo weibo = this.getWeiboService().searchWeiboAppById(sentWeibo);
		String weiboToken = this.getSinaWeiboUtil().getToken(weibo.getAppKey(), weibo.getAppSecret(), 
				weibo.getAppRedirectUri(), weibo.getWeiboUserName(), weibo.getWeiboPassword());
		Status status = null;
		try {
			status = this.getSinaWeiboUtil().UploadStatus(weiboToken, sentWeibo.getImageUrl(), sentWeibo.getBrief());
			if(status != null) {//发送成功时，将本地已发微博表中的该条数据更新为已发状态
				sentWeibo.setStatus(ConstantParam.STATUS_SENT);
				sentWeibo.setWeiboContentId(status.getId());
				sentWeibo.setUserId(status.getUser().getId());
				this.getSentDao().updateByPrimaryKeySelective(sentWeibo);
			}
		} catch (WeiboException e) {
			e.printStackTrace();
		}
		
	}

}
