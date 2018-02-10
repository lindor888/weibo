package com.ctvit.weibo.mongodb.dao;

import java.util.Date;
import java.util.List;

import weibo4j.model.Status;

import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.util.ConstantParam;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 微博搜索表
 * @author tqc
 * mongodb中task_id和weibo_id和q做唯一索引
 * db.searchweibo.ensureIndex({"task_id":1,"weibo_id":1,"q":1,"lanmu":1},{"unique":true,"dropDups":true})
 */
public class SearchWeiboDao {
	/**
	 * 创建唯一索引以及查询索引
	 * @param taskId
	 */
	public void createIndex(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_SEARCH_WEIBO+taskId);
		DBObject dbo = new BasicDBObject("task_id",1).append("weibo_id", 1).append("q", 1).append("lanmu", 1);
		DBObject dboCondition = new BasicDBObject("unique",true).append("dropDups", true);
		dbc.ensureIndex(dbo, dboCondition);
		
		DBObject dboIndex = new BasicDBObject("task_id",1).append("q", 1).append("lanmu", 1);
		dbc.createIndex(dboIndex);
		dboIndex = new BasicDBObject("task_id",1).append("q", 1).append("lanmu", 1).append("weibo_created_at", 1);
		dbc.createIndex(dboIndex);
	}
	
	/**
	 * 插入微博搜索表(主服务器)
	 * @param status
	 * @param taskWeiboId
	 * @param taskId
	 * @param taskType
	 * @param lanmu
	 * @param q
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public boolean insert(Status status,String taskWeiboId,String taskId,int taskType,String lanmu,String q,String startTime,String endTime){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_SEARCH_WEIBO+taskId);
	    DBObject dbo = new BasicDBObject("task_weibo_id",taskWeiboId)//任务微博id本系统id
	    				.append("task_id", taskId)//任务id
	    				.append("task_type", taskType)//任务类型
	    				.append("task_time", new Date())//任务执行时间
	    				.append("lanmu", lanmu)//栏目名称
	    				.append("q", q)//搜索关键词
	    				.append("starttime", startTime)//搜索范围开始时间
	    				.append("endtime", endTime)//搜索范围结束时间
	    				.append("weibo_created_at", status.getCreatedAt())//微博创建时间
	    				.append("weibo_id", status.getId())//微博id
	    				.append("weibo_text", status.getText())//微博信息内容 
	    				.append("source_name", status.getSource()==null?"":status.getSource().getName())//微博来源名称
	    				.append("source_url", status.getSource()==null?"":status.getSource().getUrl())//微博来源url
	    				.append("in_reply_to_status_id", status.getInReplyToStatusId())//回复ID 
	    				.append("in_reply_to_user_id", status.getInReplyToUserId())//回复人UID 
	    				.append("in_reply_to_screen_name", status.getInReplyToScreenName())//回复人昵称
	    				.append("geo", status.getGeo())//地理信息字段
	    				.append("mid", status.getMid())//微博MID
	    				.append("reposts_count", status.getRepostsCount())//转发数
	    				.append("comments_count", status.getCommentsCount())//评论数 
	    				.append("annotations", status.getAnnotations())
	    				.append("user_id", status.getUser().getId())//用户id
	    				.append("user_screen_name", status.getUser().getScreenName())//用户昵称 
	    				.append("user_name", status.getUser().getName())//友好显示名称 
	    				.append("province", status.getUser().getProvince())//用户所在省级ID
	    				.append("city", status.getUser().getCity())//用户所在城市ID
	    				.append("location", status.getUser().getLocation())//用户所在地
	    				.append("description", status.getUser().getDescription())//用户个人描述
	    				.append("url", status.getUser().getUrl())//用户博客地址
	    				.append("profile_image_url", status.getUser().getProfileImageUrl())//用户头像地址（中图），50×50像素
	    				.append("gender", status.getUser().getGender())//性别，m：男、f：女、n：未知
	    				.append("followers_count", status.getUser().getFollowersCount())//粉丝数
	    				.append("friends_count", status.getUser().getFriendsCount())//关注数 
	    				.append("statuses_count", status.getUser().getStatusesCount())//微博数
	    				.append("favourites_count", status.getUser().getFavouritesCount())//收藏数 
	    				.append("user_created_at", status.getUser().getCreatedAt())//用户创建（注册）时间 
	    				.append("remark", status.getUser().getRemark())//用户备注信息，只有在查询用户关系时才返回此字段
	    				.append("avatar_large", status.getUser().getAvatarLarge())//用户头像地址（大图），180×180像素
	    				.append("verified_reason", status.getUser().getVerifiedReason())//认证原因 
	    				.append("bi_followers_count",  status.getUser().getBiFollowersCount())//用户的互粉数 
	    				.append("verified_type", status.getUser().getverifiedType())
	    				.append("user_domain", status.getUser().getUserDomain())
	    				.append("verified", status.getUser().isVerified());
	    				
	    dbc.insert(dbo);
	    return true;
	}
	
	/**
	 * 获取提及关键字微博数/任务所抓取微博数(从服务器)
	 * @param taskId
	 * @return
	 */
	public int getCount(String taskId,String q,String lanmu,Date countBeginTime,Date countEndTime){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_SEARCH_WEIBO+taskId);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		queryCondition.put("q", q);
		queryCondition.put("lanmu", lanmu);
		if(countBeginTime!=null&&countEndTime!=null){
			queryCondition.put("weibo_created_at", new BasicDBObject("$gte",countBeginTime).append("$lt", countEndTime));
		}
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
	
	/**
	 * 获取提及关键字用户数(从服务器)
	 * @param taskId
	 * @param q
	 * @param lanmu
	 * @param countBeginTime
	 * @param countEndTime
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public int getUserCount(String taskId,String q,String lanmu,Date countBeginTime,Date countEndTime){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_SEARCH_WEIBO+taskId);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		queryCondition.put("q", q);
		queryCondition.put("lanmu", lanmu);
		if(countBeginTime!=null&&countEndTime!=null){
			queryCondition.put("weibo_created_at", new BasicDBObject("$gte",countBeginTime).append("$lt", countEndTime));
		}
	    List<String> userIdList = dbc.distinct("user_id", queryCondition);
	    return userIdList.size();
	}

}
