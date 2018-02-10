package com.ctvit.weibo.mongodb.dao;

import java.util.Date;
import java.util.List;

import weibo4j.model.User;

import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.util.ConstantParam;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 用户信息表
 * @author tqc
 * mongodb中task_id和user_id做唯一索引
 * db.user.ensureIndex({"task_id":1,"user_id":1},{"unique":true,"dropDups":true})
 */
public class UserDao {
	
	/**
	 * 创建索引
	 */
	static{
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
			DBObject dbo = new BasicDBObject("task_id",1).append("user_id", 1);
			DBObject dboCondition = new BasicDBObject("unique",true).append("dropDups", true);
			dbc.ensureIndex(dbo, dboCondition);
			
			DBObject dboIndex = new BasicDBObject("task_id",1).append("location", 1);
			dbc.createIndex(dboIndex);
			dboIndex = new BasicDBObject("task_id",1).append("gender", 1);
			dbc.createIndex(dboIndex);
			dboIndex = new BasicDBObject("task_id",1);
			dbc.createIndex(dboIndex);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String MALE = "m";
	
	public static String FEMALE = "f";
	
	public boolean insert(User user,String taskWeiboId,String taskId,int taskType){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject dbo = null;
	    if(user.getStatus()!=null){
	    	dbo = new BasicDBObject("task_weibo_id",taskWeiboId)//任务微博id本系统id
			.append("task_id", taskId)//任务id
			.append("task_type", taskType)//任务类型
			.append("task_time", new Date())//任务执行时间
			.append("user_id", user.getId())//用户id
			.append("user_screen_name", user.getScreenName())//用户昵称 
			.append("user_name", user.getName())//友好显示名称 
			.append("province", user.getProvince())//用户所在省级ID
			.append("city", user.getCity())//用户所在城市ID
			.append("location", user.getLocation())//用户所在地
			.append("description", user.getDescription())//用户个人描述
			.append("url", user.getUrl())//用户博客地址
			.append("profile_image_url", user.getProfileImageUrl())//用户头像地址（中图），50×50像素
			.append("gender", user.getGender())//性别，m：男、f：女、n：未知
			.append("followers_count", user.getFollowersCount())//粉丝数
			.append("friends_count", user.getFriendsCount())//关注数 
			.append("statuses_count", user.getStatusesCount())//微博数
			.append("favourites_count", user.getFavouritesCount())//收藏数 
			.append("user_created_at", user.getCreatedAt())//用户创建（注册）时间 
			.append("remark", user.getRemark())//用户备注信息，只有在查询用户关系时才返回此字段
			.append("avatar_large", user.getAvatarLarge())//用户头像地址（大图），180×180像素
			.append("verified_reason", user.getVerifiedReason())//认证原因 
			.append("bi_followers_count",  user.getBiFollowersCount())//用户的互粉数
			.append("verified_type", user.getverifiedType())
			.append("user_domain", user.getUserDomain())
			.append("verified", user.isVerified())
			.append("weibo_created_at", user.getStatus().getCreatedAt())//最新一条微博创建时间
			.append("weibo_id", user.getStatus().getId())//最新一条微博id
			.append("weibo_text", user.getStatus().getText())//最新一条微博信息内容 
			.append("source_name", user.getStatus().getSource()==null?"":user.getStatus().getSource().getName())//最新一条微博来源名称
			.append("source_url", user.getStatus().getSource()==null?"":user.getStatus().getSource().getUrl())//最新一条微博来源url
			.append("in_reply_to_status_id", user.getStatus().getInReplyToStatusId())//最新一条微博回复ID 
			.append("in_reply_to_user_id", user.getStatus().getInReplyToUserId())//最新一条微博回复人UID 
			.append("in_reply_to_screen_name", user.getStatus().getInReplyToScreenName())//最新一条微博回复人昵称
			.append("geo", user.getStatus().getGeo())//最新一条微博地理信息字段
			.append("mid", user.getStatus().getMid())//最新一条微博MID
			.append("reposts_count", user.getStatus().getRepostsCount())//最新一条微博转发数
			.append("comments_count", user.getStatus().getCommentsCount())//最新一条微博评论数 
			.append("annotations", user.getStatus().getAnnotations());
	    }else{
	    	dbo = new BasicDBObject("task_weibo_id",taskWeiboId)//任务微博id本系统id
			.append("task_id", taskId)//任务id
			.append("task_type", taskType)//任务类型
			.append("task_time", new Date())//任务执行时间
			.append("user_id", user.getId())//用户id
			.append("user_screen_name", user.getScreenName())//用户昵称 
			.append("user_name", user.getName())//友好显示名称 
			.append("province", user.getProvince())//用户所在省级ID
			.append("city", user.getCity())//用户所在城市ID
			.append("location", user.getLocation())//用户所在地
			.append("description", user.getDescription())//用户个人描述
			.append("url", user.getUrl())//用户博客地址
			.append("profile_image_url", user.getProfileImageUrl())//用户头像地址（中图），50×50像素
			.append("gender", user.getGender())//性别，m：男、f：女、n：未知
			.append("followers_count", user.getFollowersCount())//粉丝数
			.append("friends_count", user.getFriendsCount())//关注数 
			.append("statuses_count", user.getStatusesCount())//微博数
			.append("favourites_count", user.getFavouritesCount())//收藏数 
			.append("user_created_at", user.getCreatedAt())//用户创建（注册）时间 
			.append("remark", user.getRemark())//用户备注信息，只有在查询用户关系时才返回此字段
			.append("avatar_large", user.getAvatarLarge())//用户头像地址（大图），180×180像素
			.append("verified_reason", user.getVerifiedReason())//认证原因 
			.append("bi_followers_count",  user.getBiFollowersCount())//用户的互粉数 
			.append("verified_type", user.getverifiedType())
			.append("user_domain", user.getUserDomain())
			.append("verified", user.isVerified());
	    }
	    
	    dbc.insert(dbo);
	    return true;
	}
	
	/**
	 * 获取任务抓取数据条数
	 * @param taskId
	 * @return
	 */
	public int getCount(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
	/**
	 * 获取任务抓取男性的总数
	 * @param taskId
	 * @return
	 */
	public int getMaleCount(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		queryCondition.put("gender", MALE);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
	/**
	 * 获取任务抓取女性的总数
	 * @param taskId
	 * @return
	 */
	public int getFemaleCount(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		queryCondition.put("gender", FEMALE);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
	
	/**
	 * 获取统计的地域列表
	 * @param taskId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getDistrictList(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		List<String> districtList = dbc.distinct("location", queryCondition);
		return districtList;
	}
	
	
	/**
	 * 获取任务抓取某个地域的总数
	 * @param taskId
	 * @param district
	 * @return
	 */
	public int getDistrictCount(String taskId,String district){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		queryCondition.put("location", district);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
}
