package com.ctvit.weibo.mongodb.dao;

import java.util.Date;

import weibo4j.model.Status;

import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.util.ConstantParam;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * 转发表
 * @author tqc
 * mongodb中task_id和repost_id做唯一索引
 * db.repost.ensureIndex({"task_id":1,"repost_id":1},{"unique":true,"dropDups":true})
 */
public class RepostDao {
	static{
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_REPOST);
			DBObject dbo = new BasicDBObject("task_id",1).append("repost_id", 1);
			DBObject dboCondition = new BasicDBObject("unique",true).append("dropDups", true);
			dbc.ensureIndex(dbo, dboCondition);
			
			DBObject dboIndex = new BasicDBObject("task_id",1);
			dbc.createIndex(dboIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入转发表
	 * @param status
	 * @param taskWeiboId
	 * @param taskId
	 * @param taskType
	 * @return
	 */
	public boolean insert(Status status,String taskWeiboId,String taskId,int taskType){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_REPOST);
	    DBObject dbo = new BasicDBObject("task_weibo_id",taskWeiboId)//任务微博id本系统id
	    				.append("task_id", taskId)//任务id
	    				.append("task_type", taskType)//任务类型
	    				.append("task_time", new Date())//任务执行时间
	    				.append("repost_created_at", status.getCreatedAt())//转发创建时间
	    				.append("repost_id", status.getId())//转发id
	    				.append("repost_text", status.getText())//转发内容 
	    				.append("repost_source_name", status.getSource().getName())//转发来源名称
	    				.append("repost_source_url", status.getSource().getUrl())//转发来源url
	    				.append("repost_repost_count", status.getRepostsCount())
	    				.append("repost_comments_count", status.getCommentsCount())
	    				.append("repost_user_id", status.getUser().getId())//转发用户id
	    				.append("repost_user_screen_name", status.getUser().getScreenName())//转发用户昵称 
	    				.append("repost_user_name", status.getUser().getName())//转发用户友好显示名称 
	    				.append("repost_province", status.getUser().getProvince())//转发用户所在省级ID
	    				.append("repost_city", status.getUser().getCity())//转发用户所在城市ID
	    				.append("repost_location", status.getUser().getLocation())//转发用户所在地
	    				.append("repost_description", status.getUser().getDescription())//转发用户个人描述
	    				.append("repost_url", status.getUser().getUrl())//转发用户博客地址
	    				.append("repost_profile_image_url", status.getUser().getProfileImageUrl())//转发用户头像地址（中图），50×50像素
	    				.append("repost_gender", status.getUser().getGender())//转发用户性别，m：男、f：女、n：未知
	    				.append("repost_followers_count", status.getUser().getFollowersCount())//转发用户粉丝数
	    				.append("repost_friends_count", status.getUser().getFriendsCount())//转发用户关注数 
	    				.append("repost_statuses_count", status.getUser().getStatusesCount())//转发用户微博数
	    				.append("repost_favourites_count", status.getUser().getFavouritesCount())//转发用户收藏数 
	    				.append("repost_user_created_at", status.getUser().getCreatedAt())//转发用户创建（注册）时间 
	    				.append("repost_remark", status.getUser().getRemark())//转发用户备注信息，只有在查询用户关系时才返回此字段
	    				.append("repost_avatar_large", status.getUser().getAvatarLarge())//转发用户头像地址（大图），180×180像素
	    				.append("repost_verified_reason", status.getUser().getVerifiedReason())//转发用户认证原因 
	    				.append("repost_bi_followers_count",  status.getUser().getBiFollowersCount())//转发用户的互粉数 
	    				.append("repost_verified_type", status.getUser().getverifiedType())
	    				.append("repost_user_domain", status.getUser().getUserDomain())
	    				.append("repost_verified", status.getUser().isVerified())
	    				.append("weibo_created_at", status.getRetweetedStatus().getCreatedAt())//原微博创建时间
	    				.append("weibo_id", status.getRetweetedStatus().getId())//原微博id
	    				.append("weibo_text", status.getRetweetedStatus().getText())//原微博信息内容 
	    				.append("source_name", status.getRetweetedStatus().getSource()==null?"":status.getRetweetedStatus().getSource().getName())//原微博来源名称
	    				.append("source_url", status.getRetweetedStatus().getSource()==null?"":status.getRetweetedStatus().getSource().getUrl())//原微博来源url
	    				.append("in_reply_to_status_id", status.getRetweetedStatus().getInReplyToStatusId())//原微博回复ID 
	    				.append("in_reply_to_user_id", status.getRetweetedStatus().getInReplyToUserId())//原微博回复人UID 
	    				.append("in_reply_to_screen_name", status.getRetweetedStatus().getInReplyToScreenName())//原微博回复人昵称
	    				.append("geo", status.getRetweetedStatus().getGeo())//原微博地理信息字段
	    				.append("mid", status.getRetweetedStatus().getMid())//原微博MID
	    				.append("reposts_count", status.getRetweetedStatus().getRepostsCount())//原微博转发数
	    				.append("comments_count", status.getRetweetedStatus().getCommentsCount())//原微博评论数 
	    				.append("annotations", status.getRetweetedStatus().getAnnotations())
	    				.append("user_id", status.getRetweetedStatus().getUser().getId())//原微博用户id
	    				.append("user_screen_name", status.getRetweetedStatus().getUser().getScreenName())//原微博用户昵称 
	    				.append("user_name", status.getRetweetedStatus().getUser().getName())//原微博用户友好显示名称 
	    				.append("province", status.getRetweetedStatus().getUser().getProvince())//原微博用户所在省级ID
	    				.append("city", status.getRetweetedStatus().getUser().getCity())//原微博用户所在城市ID
	    				.append("location", status.getRetweetedStatus().getUser().getLocation())//原微博用户所在地
	    				.append("description", status.getRetweetedStatus().getUser().getDescription())//原微博用户个人描述
	    				.append("url", status.getRetweetedStatus().getUser().getUrl())//原微博用户博客地址
	    				.append("profile_image_url", status.getRetweetedStatus().getUser().getProfileImageUrl())//原微博用户头像地址（中图），50×50像素
	    				.append("gender", status.getRetweetedStatus().getUser().getGender())//原微博用户性别，m：男、f：女、n：未知
	    				.append("followers_count", status.getRetweetedStatus().getUser().getFollowersCount())//原微博用户粉丝数
	    				.append("friends_count", status.getRetweetedStatus().getUser().getFriendsCount())//原微博用户关注数 
	    				.append("statuses_count", status.getRetweetedStatus().getUser().getStatusesCount())//原微博用户微博数
	    				.append("favourites_count", status.getRetweetedStatus().getUser().getFavouritesCount())//原微博用户收藏数 
	    				.append("user_created_at", status.getRetweetedStatus().getUser().getCreatedAt())//原微博用户创建（注册）时间 
	    				.append("remark", status.getRetweetedStatus().getUser().getRemark())//原微博用户备注信息，只有在查询用户关系时才返回此字段
	    				.append("avatar_large", status.getRetweetedStatus().getUser().getAvatarLarge())//原微博用户头像地址（大图），180×180像素
	    				.append("verified_reason", status.getRetweetedStatus().getUser().getVerifiedReason())//原微博用户认证原因 
	    				.append("bi_followers_count",  status.getRetweetedStatus().getUser().getBiFollowersCount())//原微博用户的互粉数 
	    				.append("verified_type", status.getRetweetedStatus().getUser().getverifiedType())
	    				.append("user_domain", status.getRetweetedStatus().getUser().getUserDomain())
	    				.append("verified", status.getRetweetedStatus().getUser().isVerified());
	    				
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
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_REPOST);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
}
