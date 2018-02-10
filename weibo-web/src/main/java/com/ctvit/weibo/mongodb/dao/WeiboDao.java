package com.ctvit.weibo.mongodb.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;



import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.studio.entity.BtvWeibo;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.DownloadImgUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import weibo4j.model.Status;

/**
 * 微博表
 * @author tqc
 * mongodb中task_id和weibo_id做唯一索引
 * db.weibo.ensureIndex({"task_id":1,"weibo_id":1},{"unique":true,"dropDups":true})
 */
public class WeiboDao {
	
	static{
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
			DBObject dbo = new BasicDBObject("task_id",1).append("weibo_id", 1);
			DBObject dboCondition = new BasicDBObject("unique",true).append("dropDups", true);
			dbc.ensureIndex(dbo, dboCondition);
			
			DBObject dboIndex = new BasicDBObject("task_weibo_id",1).append("task_type", 1).append("weibo_id", 1);
			dbc.createIndex(dboIndex);
			dboIndex = new BasicDBObject("task_id",1).append("user_id", 1).append("weibo_created_at", 1);
			dbc.createIndex(dboIndex);
			dboIndex = new BasicDBObject("task_id",1);
			dbc.createIndex(dboIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 插入微博表
	 * @param status
	 * @param taskWeiboId
	 * @param taskId
	 * @param taskType
	 * @param commentIfCount
	 * @param repostIfCount
	 * @return
	 */
	public boolean insert(Status status,String taskWeiboId,String taskId,int taskType,int commentIfCount, int repostIfCount){
		//头像和图片抓取
		String face = "";
		String image = "";
		if(StringUtils.isNotBlank(status.getUser().getProfileImageUrl())){
			face = DownloadImgUtil.downloadImageUrl(status.getUser().getProfileImageUrl());
		}
		if(StringUtils.isNotBlank(status.getPicUrls())){
			String[] imgArray = status.getPicUrls().split(",");
			for(int i=0;i<imgArray.length;i++){
				image += DownloadImgUtil.downloadImageUrl(imgArray[i]) + ",";
			}
		}
		if(image.length()>0){
			image = image.substring(0,image.length()-1);
		}
		
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
	    DBObject dbo = new BasicDBObject("task_weibo_id",taskWeiboId)//任务微博id本系统id
	    				.append("task_id", taskId)//任务id
	    				.append("task_type", taskType)//任务类型
	    				.append("task_time", new Date())//任务执行时间
	    				.append("weibo_created_at", status.getCreatedAt())//微博创建时间
	    				.append("weibo_id", status.getId())//微博id
	    				.append("weibo_text", status.getText())//微博信息内容 
	    				.append("pic_urls", image)//微博中的图片
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
	    				.append("profile_image_url", face)//用户头像地址（中图），50×50像素
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
	    				.append("verified", status.getUser().isVerified())
	    				.append("comment_if_count", commentIfCount)//评论是否统计
	    				.append("repost_if_count", repostIfCount)//转发是否统计
	    				.append("shenhe", 0)
	    				.append("update_time", new Date());
	    				
	    dbc.insert(dbo);
	    return true;
	}
	
	
	/**
	 * 查询出需要抓取评论和转发的微博，倒序（是否根据时间暂时未确定）
	 * @param taskWeiboId
	 * @param taskType
	 * @param weiboId
	 * @return
	 */
	public List<String> select(String taskWeiboId,int taskType,String weiboId,String commentOrRepost){
		List<String> weiboIds = new ArrayList<String>();
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_weibo_id", taskWeiboId);
		queryCondition.put("task_type", taskType);
		if(weiboId!=null&&!"".equals(weiboId)){
			queryCondition.put("user_id", weiboId);
		}
		DBObject sortConditon = new BasicDBObject();
		sortConditon.put("weibo_created_at", -1);
		//queryCondition.put(commentOrRepost+"_if_count", ConstantParam.NO_COUNT);
	    DBCursor dbCursor = dbc.find(queryCondition).sort(sortConditon);
	    while(dbCursor.hasNext()){
	    	DBObject tobj = dbCursor.next();
	    	String resultWeiboId =(String) tobj.get("weibo_id");
	    	weiboIds.add(resultWeiboId);
	    }
	    return weiboIds;
		
	}
	
	/**
	 * 更新微博集合中的统计字段
	 * @param taskId
	 * @param weiboId
	 * @param commentOrRepost
	 * @return
	 */
	public boolean update(String taskWeiboId,String weiboId,String commentOrRepost){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
	    DBObject condition = new BasicDBObject("task_weibo_id",taskWeiboId).append("weibo_id", weiboId);
	    DBObject updateDB = new BasicDBObject("$set",new BasicDBObject(commentOrRepost+"_if_count",ConstantParam.YES_COUNT));
	    dbc.update(condition, updateDB);
	    return true;
	}
	
	
	/**
	 * 获取任务抓取数据条数
	 * @param taskId
	 * @return
	 */
	public int getCount(String taskId){
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
	    int size = dbc.find(queryCondition).size();
	    return size;
	}
	
	/**
	 * 获取微博列表(微博数/评论数/转发数)
	 * @param taskId
	 * @param uid
	 * @param countBeginDate
	 * @param countEndDate
	 * @return
	 */
	public int[] getCountWeibo(String taskId,String uid,Date countBeginDate,Date countEndDate){
		int[] dataNum = {0,0,0};
		DB db = MongodbUtil.getInstance().getMasterConnection();
	    DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
	    DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
	    queryCondition.put("user_screen_name", uid);
	    queryCondition.put("weibo_created_at", new BasicDBObject("$gte",countBeginDate).append("$lt", countEndDate));
	    DBCursor dbCursor = dbc.find(queryCondition);
	    while(dbCursor.hasNext()){
	    	DBObject tobj = dbCursor.next();
	    	int commentNum = (tobj.get("comments_count")!=null&&!"".equals(tobj.get("reposts_count")))?Integer.parseInt((tobj.get("comments_count")+"")):0;
	    	int repostNum = (tobj.get("reposts_count")!=null&&!"".equals(tobj.get("reposts_count")))?Integer.parseInt((tobj.get("reposts_count")+"")):0;
	    	dataNum[0]++;
	    	dataNum[1] += commentNum;
	    	dataNum[2] += repostNum;
	    }
		return dataNum;
	}
	
	/**
	 * 获取微博列表
	 * @param startTime
	 * @param rows
	 * @param timesort
	 * @param taskWeiboId
	 * @return
	 */
	public List<BtvWeibo> getWeiboList(String startTime,Integer rows,String timesort,String taskWeiboId){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BtvWeibo> list = new ArrayList<BtvWeibo>();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
		DBObject condition = new BasicDBObject();
		condition.put("shenhe", 1);
		if(startTime!=null&&!"".equals(startTime)){
			Date date = null;
			try {
				date = sdf.parse(startTime);
			} catch (ParseException e) {
				date = null;
			}
			if(date!=null){
				if("true".equals(timesort)){
					condition.put("update_time", new BasicDBObject("$lt",date));
				}else{
					condition.put("update_time", new BasicDBObject("$gte",date));
				}
			}
		}
		if(StringUtils.isNotBlank(taskWeiboId)){
			condition.put("task_weibo_id", taskWeiboId);
		}
		DBObject orderby = new BasicDBObject("update_time",-1);
		DBCursor cur = null;
		if(rows!=null){
			cur = dbc.find(condition).sort(orderby).limit(rows);
		}else{
			cur = dbc.find(condition).sort(orderby);
		}
		
		while(cur.hasNext()){
			DBObject dbo = cur.next();
			String id = dbo.get("weibo_id").toString();
			String content = dbo.get("weibo_text").toString();
			String picUrls = dbo.get("pic_urls").toString();
			String face = dbo.get("profile_image_url").toString();
			String nickname = dbo.get("user_screen_name").toString();
			String userid = dbo.get("user_id").toString();
			
			
			String updatetime = dbo.get("update_time")==null?"":sdf.format(dbo.get("update_time"));
			String inputtime = sdf.format((Date) dbo.get("weibo_created_at"));
			String location = dbo.get("location").toString();
			
			BtvWeibo b = new BtvWeibo();
			b.setId(id);
			b.setContent(content);
			b.setPicUrls(picUrls);
			b.setFace(face);
			b.setUpdatetime(updatetime);
			b.setNickname(nickname);
			b.setUserid(userid);
			b.setInputtime(inputtime);
			b.setLocation(location);
			
			list.add(b);
		}
		return list;
		
	}
	
}
