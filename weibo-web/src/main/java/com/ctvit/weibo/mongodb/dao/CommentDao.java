package com.ctvit.weibo.mongodb.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import weibo4j.model.Comment;

import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.studio.entity.BtvComment;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.DownloadImgUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

/**
 * 评论表
 * 
 * @author tqc mongodb中task_id和comment_id做唯一索引 db.comment.ensureIndex({"task_id":1,"comment_id":1},{"unique":true,"dropDups":true})
 */
public class CommentDao {
	static {
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
			DBObject dbo = new BasicDBObject("task_id", 1).append("comment_id", 1);
			DBObject dboCondition = new BasicDBObject("unique", true).append("dropDups", true);
			dbc.ensureIndex(dbo, dboCondition);

			DBObject dboIndex = new BasicDBObject("task_id", 1);
			dbc.createIndex(dboIndex);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 插入(主服务器)
	 * 
	 * @param comment
	 * @param taskWeiboId
	 * @param taskId
	 * @param taskType
	 * @return
	 */
	public boolean insert(Comment comment, String taskWeiboId, String taskId, int taskType) {
		// 头像抓取
		String face = "";
		if (StringUtils.isNotBlank(comment.getUser().getProfileImageUrl())) {
			face = DownloadImgUtil.downloadImageUrl(comment.getUser().getProfileImageUrl());
		}
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		DBObject dbo = new BasicDBObject("task_weibo_id", taskWeiboId)// 任务微博id本系统id
				.append("task_id", taskId)// 任务id
				.append("task_type", taskType)// 任务类型
				.append("task_time", new Date())// 任务执行时间
				.append("comment_created_at", comment.getCreatedAt())// 评论创建时间
				.append("comment_id", comment.getId() + "")// 评论id
				.append("comment_text", comment.getText())// 评论内容
				.append("comment_source", comment.getSource())// 评论来源
				.append("comment_user_id", comment.getUser().getId())// 评论用户id
				.append("comment_user_screen_name", comment.getUser().getScreenName())// 评论用户昵称
				.append("comment_user_name", comment.getUser().getName())// 评论用户友好显示名称
				.append("comment_province", comment.getUser().getProvince())// 评论用户所在省级ID
				.append("comment_city", comment.getUser().getCity())// 评论用户所在城市ID
				.append("comment_location", comment.getUser().getLocation())// 评论用户所在地
				.append("comment_description", comment.getUser().getDescription())// 评论用户个人描述
				.append("comment_url", comment.getUser().getUrl())// 评论用户博客地址
				.append("comment_profile_image_url", face)// 评论用户头像地址（中图），50×50像素
				.append("comment_gender", comment.getUser().getGender())// 评论用户性别，m：男、f：女、n：未知
				.append("comment_followers_count", comment.getUser().getFollowersCount())// 评论用户粉丝数
				.append("comment_friends_count", comment.getUser().getFriendsCount())// 评论用户关注数
				.append("comment_statuses_count", comment.getUser().getStatusesCount())// 评论用户微博数
				.append("comment_favourites_count", comment.getUser().getFavouritesCount())// 评论用户收藏数
				.append("comment_user_created_at", comment.getUser().getCreatedAt())// 评论用户创建（注册）时间
				.append("comment_remark", comment.getUser().getRemark())// 评论用户备注信息，只有在查询用户关系时才返回此字段
				.append("comment_avatar_large", comment.getUser().getAvatarLarge())// 评论用户头像地址（大图），180×180像素
				.append("comment_verified_reason", comment.getUser().getVerifiedReason())// 评论用户认证原因
				.append("comment_bi_followers_count", comment.getUser().getBiFollowersCount())// 评论用户的互粉数
				.append("comment_verified_type", comment.getUser().getverifiedType()).append("comment_user_domain", comment.getUser().getUserDomain()).append("comment_verified", comment.getUser().isVerified()).append("weibo_created_at", comment.getStatus().getCreatedAt())// 原微博创建时间
				.append("weibo_id", comment.getStatus().getId())// 原微博id
				.append("weibo_text", comment.getStatus().getText())// 原微博信息内容
				.append("source_name", comment.getStatus().getSource() == null ? "" : comment.getStatus().getSource().getName())// 原微博来源名称
				.append("source_url", comment.getStatus().getSource() == null ? "" : comment.getStatus().getSource().getUrl())// 原微博来源url
				.append("in_reply_to_status_id", comment.getStatus().getInReplyToStatusId())// 原微博回复ID
				.append("in_reply_to_user_id", comment.getStatus().getInReplyToUserId())// 原微博回复人UID
				.append("in_reply_to_screen_name", comment.getStatus().getInReplyToScreenName())// 原微博回复人昵称
				.append("geo", comment.getStatus().getGeo())// 原微博地理信息字段
				.append("mid", comment.getStatus().getMid())// 原微博MID
				.append("reposts_count", comment.getStatus().getRepostsCount())// 原微博转发数
				.append("comments_count", comment.getStatus().getCommentsCount())// 原微博评论数
				.append("annotations", comment.getStatus().getAnnotations()).append("user_id", comment.getStatus().getUser().getId())// 原微博用户id
				.append("user_screen_name", comment.getStatus().getUser().getScreenName())// 原微博用户昵称
				.append("user_name", comment.getStatus().getUser().getName())// 原微博用户友好显示名称
				.append("province", comment.getStatus().getUser().getProvince())// 原微博用户所在省级ID
				.append("city", comment.getStatus().getUser().getCity())// 原微博用户所在城市ID
				.append("location", comment.getStatus().getUser().getLocation())// 原微博用户所在地
				.append("description", comment.getStatus().getUser().getDescription())// 原微博用户个人描述
				.append("url", comment.getStatus().getUser().getUrl())// 原微博用户博客地址
				.append("profile_image_url", comment.getStatus().getUser().getProfileImageUrl())// 原微博用户头像地址（中图），50×50像素
				.append("gender", comment.getStatus().getUser().getGender())// 原微博用户性别，m：男、f：女、n：未知
				.append("followers_count", comment.getStatus().getUser().getFollowersCount())// 原微博用户粉丝数
				.append("friends_count", comment.getStatus().getUser().getFriendsCount())// 原微博用户关注数
				.append("statuses_count", comment.getStatus().getUser().getStatusesCount())// 原微博用户微博数
				.append("favourites_count", comment.getStatus().getUser().getFavouritesCount())// 原微博用户收藏数
				.append("user_created_at", comment.getStatus().getUser().getCreatedAt())// 原微博用户创建（注册）时间
				.append("remark", comment.getStatus().getUser().getRemark())// 原微博用户备注信息，只有在查询用户关系时才返回此字段
				.append("avatar_large", comment.getStatus().getUser().getAvatarLarge())// 原微博用户头像地址（大图），180×180像素
				.append("verified_reason", comment.getStatus().getUser().getVerifiedReason())// 原微博用户认证原因
				.append("bi_followers_count", comment.getStatus().getUser().getBiFollowersCount())// 原微博用户的互粉数
				.append("virified_type", comment.getStatus().getUser().getverifiedType()).append("user_domain", comment.getStatus().getUser().getUserDomain()).append("verified", comment.getStatus().getUser().isVerified()).append("shenhe", 0);
		dbc.insert(dbo);
		return true;
	}

	/**
	 * 获取任务抓取数据条数(从服务器)
	 * 
	 * @param taskId
	 * @return
	 */
	public int getCount(String taskId) {
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("task_id", taskId);
		int size = dbc.find(queryCondition).size();
		return size;
	}

	/**
	 * 获取评论列表
	 * 
	 * @param startTime
	 * @param rows
	 * @param timesort
	 * @param taskWeiboId
	 * @return
	 */
	public List<BtvComment> getCommentList(String startTime, Integer rows, String timesort, String taskWeiboId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BtvComment> list = new ArrayList<BtvComment>();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		DBObject condition = new BasicDBObject();
		condition.put("shenhe", 1);
		if (startTime != null && !"".equals(startTime)) {
			Date date = null;
			try {
				date = sdf.parse(startTime);
			} catch (ParseException e) {
				date = null;
			}
			if (date != null) {
				if ("true".equals(timesort)) {
					condition.put("comment_created_at", new BasicDBObject("$lt", date));
				} else {
					condition.put("comment_created_at", new BasicDBObject("$gte", date));
				}
			}
		}
		if (StringUtils.isNotBlank(taskWeiboId)) {
			condition.put("task_weibo_id", taskWeiboId);
		}
		DBObject orderby = new BasicDBObject("comment_created_at", -1);
		DBCursor cur = null;
		if (rows != null) {
			cur = dbc.find(condition).sort(orderby).limit(rows);
		} else {
			cur = dbc.find(condition).sort(orderby);
		}

		while (cur.hasNext()) {
			DBObject dbo = cur.next();
			String id = dbo.get("comment_id").toString();
			String content = dbo.get("comment_text").toString();
			String face = dbo.get("comment_profile_image_url").toString();
			String nickname = dbo.get("comment_user_screen_name").toString();
			String userid = dbo.get("comment_user_id").toString();
			String updatetime = sdf.format((Date) dbo.get("comment_created_at"));
			String inputtime = sdf.format((Date) dbo.get("comment_created_at"));
			String location = dbo.get("comment_location").toString();

			BtvComment b = new BtvComment();
			b.setId(id);
			b.setContent(content);
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

	public List<BtvComment> getRossList() {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BtvComment> list = new ArrayList<BtvComment>();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
		DBObject condition = new BasicDBObject();
		try {
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String time = dateFormat.format(d);
			condition.put("shenhe", 1);
			Date date = null;
			date = sdf.parse(time + " " + "00:00:00");
			// condition.put("comment_created_at", new BasicDBObject("$lt", date));
			condition.put("task_weibo_id", "Weib1439195251774022");

			DBObject orderby = new BasicDBObject("comment_created_at", -1);
			DBCursor cur = null;

			cur = dbc.find(condition).sort(orderby);

			while (cur.hasNext()) {
				DBObject dbo = cur.next();
				String id = dbo.get("comment_id").toString();
				String content = dbo.get("comment_text").toString();
				String face = dbo.get("comment_profile_image_url").toString();
				String nickname = dbo.get("comment_user_screen_name").toString();
				String userid = dbo.get("comment_user_id").toString();
				String updatetime = sdf.format((Date) dbo.get("comment_created_at"));
				String inputtime = sdf.format((Date) dbo.get("comment_created_at"));
				String location = dbo.get("comment_location").toString();

				BtvComment b = new BtvComment();
				b.setId(id);
				b.setContent(content);
				b.setFace(face);
				b.setUpdatetime(updatetime);
				b.setNickname(nickname);
				b.setUserid(userid);
				b.setInputtime(inputtime);
				b.setLocation(location);

				list.add(b);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;

	}

}
