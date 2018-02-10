package com.ctvit.weibo.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import weibo4j.model.Status;

import com.ctvit.weibo.dao.SentWeiboMapper;
import com.ctvit.weibo.dao.WeiboMapper;
import com.ctvit.weibo.entity.SearchBean;
import com.ctvit.weibo.entity.SentWeibo;
import com.ctvit.weibo.entity.SentWeiboExample;
import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.entity.WeiboExample;
import com.ctvit.weibo.mongodb.MongodbUtil;
import com.ctvit.weibo.studio.entity.BtvComment;
import com.ctvit.weibo.studio.entity.BtvUser;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.task.entity.tasklist.TaskSendWeibo;
import com.ctvit.weibo.task.service.QuartzManager;
import com.ctvit.weibo.task.service.TaskService;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.KeyUtil;
import com.ctvit.weibo.util.SearchUtil;
import com.ctvit.weibo.util.SinaWeiboUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class WeiboService {

	private WeiboMapper weiboMapper;
	private TaskService taskService;// 任务服务类
	private SentWeiboMapper sentDao;
	private QuartzManager quartzManager;

	/**
	 * 更新微博的token
	 * 
	 * @param weibo
	 * @return
	 */
	public int updateTokenByWeiboId(Weibo weibo) {
		return weiboMapper.updateTokenByWeiboId(weibo);
	}

	/**
	 * 分页查询微博
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<Weibo> getByPaging(WeiboExample example) throws Exception {
		trans(example);
		return weiboMapper.selectByExample(example);
	}

	/**
	 * 微博数量
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public int getCount(WeiboExample example) throws Exception {
		trans(example);
		return weiboMapper.countByExample(example);
	}

	/**
	 * 查询用户下的所有微博信息
	 * 
	 * @param weibo
	 * @return
	 * @throws Exception
	 */
	public List<Weibo> selectByUserId(Weibo weibo) throws Exception {
		weibo.setDeleteFlag(ConstantParam.STATUS_VALID);
		return weiboMapper.selectByUserId(weibo);
	}

	/**
	 * 通过微博登录名查询是否存在
	 * 
	 * @param weiboUserName
	 * @return
	 * @throws Exception
	 */
	public Weibo searchByWeiboUserName(Weibo bean) throws Exception {
		bean.setDeleteFlag(ConstantParam.STATUS_VALID);
		return weiboMapper.searchByWeiboUserName(bean);
	}

	/**
	 * 添加微博
	 * 
	 * @param weibo
	 * @return
	 * @throws Exception
	 */
	public int add(Weibo weibo) throws Exception {
		try {
			weibo.setWeiboId(KeyUtil.getKey(weibo));
			weibo.setDeleteFlag(ConstantParam.STATUS_VALID);
			weiboMapper.insert(weibo);
			addDefaultTask(weibo);// 添加默认任务
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 添加微博时，默认添加该微博的15任务
	 * 
	 * @param weibo
	 * @return
	 * @throws Exception
	 */
	private int addDefaultTask(Weibo weibo) throws Exception {
		List<Task> tasks = null;
		if (weibo != null) {
			tasks = new ArrayList<Task>();
			Task task = null;
			for (int i = 0; i < Task.TASK_DEFAULT_COUNT; i++) {
				task = new Task();
				task.setTaskId(KeyUtil.getKey(task));
				task.setWeiboId(weibo.getWeiboId());
				task.setTaskType(i);
				task.setTaskState(Task.TASK_STATE_STOP);
				task.setTaskLevel(Task.TASK_LEVEL_BASIC);
				tasks.add(task);
			}
			return taskService.insertBatch(tasks);
		}
		return 0;
	}

	/**
	 * 通过weiboId查询微博信息
	 * 
	 * @param weiboId
	 * @return
	 * @throws Exception
	 */
	public Weibo searchById(String weiboId) throws Exception {
		return weiboMapper.searchById(weiboId);
	}

	/**
	 * 更新微博
	 * 
	 * @param weibo
	 * @return
	 * @throws Exception
	 */
	public int update(Weibo weibo) throws Exception {
		return weiboMapper.update(weibo);
	}

	/**
	 * 发送微博
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int send(Weibo bean) throws Exception {

		SinaWeiboUtil sinaWeiboUtil = new SinaWeiboUtil();
		Status status = null;
		String weiboId = bean.getWeiboId();
		try {
			SentWeibo sent = new SentWeibo();
			String key = KeyUtil.getKey(sent);
			sent.setId(key);
			sent.setContentId(bean.getContentId());
			sent.setTitle(bean.getTitle());
			sent.setUrl(bean.getUrl());
			sent.setPhotoUrl(bean.getImageUrl());
			sent.setWeiboId(weiboId);
			sent.setBrief(bean.getContent());
			sent.setUserId(bean.getWeiboUid());

			Integer weiboStatus = bean.getStatus();
			if (weiboStatus != null && ConstantParam.STATUS_TIME == bean.getStatus().intValue()) {// 定时发布
				sent.setStatus(ConstantParam.STATUS_TIME);
				sent.setTaskTime(bean.getBeginTime());
				sentDao.insertSelective(sent);
				// 添加定时任务
				quartzManager.addSendWeiboJob(key, new TaskSendWeibo(), bean.getBeginTime());
				return 1;
			} else {// 立即发布
				Weibo weibo = searchWeiboAppById(bean);
				String weiboToken = sinaWeiboUtil.getToken(weibo.getAppKey(), weibo.getAppSecret(), weibo.getAppRedirectUri(), weibo.getWeiboUserName(), weibo.getWeiboPassword());
				status = sinaWeiboUtil.UploadStatus(weiboToken, bean.getImageUrl(), bean.getContent());
				if (status != null) {// 发送成功时，将以发微博存入本地系统
					sent.setStatus(ConstantParam.STATUS_SENT);
					sent.setWeiboContentId(status.getId());
					sent.setUserId(status.getUser().getId());
					sentDao.insertSelective(sent);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		return 1;
	}

	/**
	 * 通过搜索查询数据
	 * 
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getFromSearch(SentWeibo bean) throws Exception {

		Map<String, Object> map = SearchUtil.search(bean);
		Integer total = (Integer) map.get("total");
		List<SearchBean> list = (List<SearchBean>) map.get("list");
		int count = 0;
		List<SentWeibo> sents = selectSentWeibo(bean);
		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				if (sents != null && sents.size() > 0) {
					for (SentWeibo sent : sents) {
						if (list.get(i).getId().equals(sent.getContentId())) {
							list.set(i, null);
							count++;
							break;
						}
					}
				} else {
					break;
				}
			}
		}
		map.put("total", total - count);
		map.put("list", list);
		return map;
	}

	/**
	 * 查询某个微博已发送的微博信息
	 * 
	 * @param weiboId
	 * @return
	 */
	private List<SentWeibo> selectSentWeibo(SentWeibo bean) {
		SentWeiboExample example = new SentWeiboExample();
		example.setWeiboId(bean.getWeiboId());
		example.setBeginTime(bean.getBeginTime());
		example.setEndTime(bean.getEndTime());
		return sentDao.selectSentWeibo(example);
	}

	/**
	 * 通过weiboid查询微博和应用信息
	 * 
	 * @param weiboId
	 * @return
	 */
	public Weibo searchWeiboAppById(Weibo bean) {
		bean.setDeleteFlag(ConstantParam.STATUS_VALID);
		return weiboMapper.searchWeiboAppById(bean);
	}

	/**
	 * 添加查询条件
	 * 
	 * @param example
	 * @throws Exception
	 */
	private void trans(WeiboExample example) throws Exception {
		if ("".equals(example.getAppId())) {
			example.setAppId(null);
		}
		example.setDeleteFlag(ConstantParam.STATUS_VALID);
	}

	/**
	 * 通过用户ID查询该微博已发布的微博列表
	 * 
	 * @param weiboId
	 * @return
	 */
	public List<Weibo> selectContentByWeiboId(WeiboExample example) {
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
			DBCollection commentDbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("task_weibo_id", example.getUserId());
			if (example.getBeginTimeStr() != null && !"".equals(example.getBeginTimeStr())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date beginTime = sdf.parse(example.getBeginTimeStr());
				Date endTime = new Date(sdf.parse(example.getEndTimeStr()).getTime() + 1000 * 60 * 60 * 24);
				queryCondition.put("weibo_created_at", new BasicDBObject("$gte", beginTime).append("$lte", endTime));
			}
			DBCursor dbCursor = dbc.find(queryCondition).skip(example.getBeginIndex()).limit(example.getRows()).sort(new BasicDBObject("weibo_created_at", -1));
			List<Weibo> weibos = new ArrayList<Weibo>();
			Weibo weibo = null;
			while (dbCursor.hasNext()) {
				DBObject tobj = dbCursor.next();
				weibo = new Weibo();
				String weiboId = (String) tobj.get("weibo_id");
				weibo.setWeiboId(weiboId);
				String weiboText = (String) tobj.get("weibo_text");
				weibo.setContent(weiboText);
				Date weiboCreateTime = (Date) tobj.get("weibo_created_at");
				weibo.setWeiboCreateTime(weiboCreateTime);
				String sourceName = (String) tobj.get("source_name");
				weibo.setSourceName(sourceName);
				String sourceUrl = (String) tobj.get("source_url");
				weibo.setSourceUrl(sourceUrl);
				weibo.setUserId(example.getUserId());

				Integer commentsCount = this.selectCommentCount(weiboId, commentDbc);
				weibo.setCommentsCount(commentsCount);

				String userCcreenName = (String) tobj.get("user_screen_name");
				weibo.setUserCcreenName(userCcreenName);
				String profileImageUrl = (String) tobj.get("profile_image_url");
				weibo.setProfileImageUrl(profileImageUrl);
				Integer followersCount = (Integer) tobj.get("followers_count");
				weibo.setFollowersCount(followersCount);
				Integer friendsCount = (Integer) tobj.get("friends_count");
				weibo.setFriendsCount(friendsCount);
				String description = (String) tobj.get("description");
				weibo.setDescription(description);
				Integer statusesCount = (Integer) tobj.get("statuses_count");
				weibo.setStatusesCount(statusesCount);
				Integer shenheFlag = (Integer) tobj.get("shenhe");
				weibo.setFlag(shenheFlag);
				weibos.add(weibo);
			}
			return weibos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 查询某个微博的评论数
	 * 
	 * @param weiboId
	 * @param dbc
	 * @return
	 * @throws Exception
	 */
	private int selectCommentCount(String weiboId, DBCollection dbc) throws Exception {
		try {
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("weibo_id", weiboId);
			DBCursor dbCursor = dbc.find(queryCondition);
			return dbCursor.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 查询微博用户发微博的总数目
	 * 
	 * @param example
	 * @return
	 */
	public int selectContentCount(WeiboExample example) {
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("task_weibo_id", example.getUserId());
			if (example.getBeginTimeStr() != null && !"".equals(example.getBeginTimeStr())) {
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Date beginTime = sdf.parse(example.getBeginTimeStr());
				Date endTime = new Date(sdf.parse(example.getEndTimeStr()).getTime() + 1000 * 60 * 60 * 24);
				queryCondition.put("weibo_created_at", new BasicDBObject("$gte", beginTime).append("$lte", endTime));
			}
			DBCursor dbCursor = dbc.find(queryCondition);
			return dbCursor.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 通过微博ID查询评论
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<Weibo> selectComment(WeiboExample example) throws Exception {
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("weibo_id", example.getWeiboId());
			DBCursor dbCursor = dbc.find(queryCondition).skip(example.getBeginIndex()).limit(example.getRows()).sort(new BasicDBObject("comment_created_at", -1));
			List<Weibo> weibos = new ArrayList<Weibo>();
			Weibo weibo = null;
			while (dbCursor.hasNext()) {
				DBObject tobj = dbCursor.next();
				weibo = new Weibo();
				String weiboText = (String) tobj.get("comment_text");
				weibo.setContent(weiboText);
				Date weiboCreateTime = (Date) tobj.get("comment_created_at");
				weibo.setWeiboCreateTime(weiboCreateTime);
				String commentId = (String) tobj.get("comment_id");
				weibo.setWeiboId(commentId);
				String userCcreenName = (String) tobj.get("comment_user_screen_name");
				weibo.setUserCcreenName(userCcreenName);
				String profileImageUrl = (String) tobj.get("comment_profile_image_url");
				weibo.setProfileImageUrl(profileImageUrl);
				Integer followersCount = (Integer) tobj.get("comment_followers_count");
				weibo.setFollowersCount(followersCount);
				Integer friendsCount = (Integer) tobj.get("comment_friends_count");
				weibo.setFriendsCount(friendsCount);
				String description = (String) tobj.get("comment_description");
				weibo.setDescription(description);
				Integer statusesCount = (Integer) tobj.get("comment_statuses_count");
				weibo.setStatusesCount(statusesCount);
				Integer shenheFlag = (Integer) tobj.get("shenhe");
				weibo.setFlag(shenheFlag);
				weibos.add(weibo);
			}
			return weibos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 通过微博ID查询微博的评论数目
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public int selectCommentCount(WeiboExample example) throws Exception {
		try {
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("weibo_id", example.getWeiboId());
			DBCursor dbCursor = dbc.find(queryCondition);
			return dbCursor.size();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	/**
	 * 更新评论状态
	 * 
	 * @param taskWeiboId
	 * @param commentId
	 * @param flag
	 */
	public void shenheComment(String taskWeiboId, String commentId, int flag) {
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		DBObject condition = new BasicDBObject("task_weibo_id", taskWeiboId).append("comment_id", commentId);
		DBObject updateDB = new BasicDBObject("$set", new BasicDBObject("shenhe", flag));
		dbc.update(condition, updateDB);
	}

	/**
	 * 更新微博状态
	 * 
	 * @param taskWeiboId
	 * @param weiboId
	 * @param flag
	 */
	public void shenheWeibo(String taskWeiboId, String weiboId, int flag) {
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
		DBObject condition = new BasicDBObject("task_weibo_id", taskWeiboId).append("weibo_id", weiboId);
		DBObject updateDB = new BasicDBObject("$set", new BasicDBObject("shenhe", flag).append("update_time", new Date()));
		dbc.update(condition, updateDB);
	}

	// 导播维护用户数据

	/**
	 * 添加评论
	 * 
	 * @param btvComment
	 */
	public void addOrUpdateBtvComment(BtvComment btvComment, String weiboId, String taskWeiboId) {
		// 判断是否存在userid,如果不存在则先插入user集合，如果存在则更新user集合
		if (!StringUtils.isNotBlank(btvComment.getUserid())) {
			BtvUser btvUser = new BtvUser();
			btvUser.setNickname(btvComment.getNickname());
			btvUser.setFace(btvComment.getFace());
			btvUser.setLocation(btvComment.getLocation());
			String userid = this.insertBtvUser(btvUser);
			btvComment.setUserid(userid);
		} else {
			BtvUser btvUser = new BtvUser();
			btvUser.setNickname(btvComment.getNickname());
			btvUser.setFace(btvComment.getFace());
			btvUser.setLocation(btvComment.getLocation());
			btvUser.setUserid(btvComment.getUserid());
			this.updateBtvUser(btvUser);
		}

		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		// 新增
		if (btvComment.getId() == null) {
			String commentId = KeyUtil.getKey(btvComment);
			DBObject dbo = new BasicDBObject("comment_text", btvComment.getContent()).append("comment_profile_image_url", btvComment.getFace()).append("comment_user_screen_name", btvComment.getNickname()).append("comment_user_id", btvComment.getUserid()).append("comment_created_at", new Date()).append("comment_location", btvComment.getLocation()).append("weibo_id", weiboId).append("task_weibo_id", taskWeiboId).append("shenhe", 0).append("comment_id", commentId);

			dbc.insert(dbo);
			// 更新
		} else {
			DBObject dbo = new BasicDBObject("$set", new BasicDBObject("comment_text", btvComment.getContent()).append("comment_profile_image_url", btvComment.getFace()).append("comment_user_screen_name", btvComment.getNickname()).append("comment_user_id", btvComment.getUserid()).append("comment_created_at", new Date()).append("comment_location", btvComment.getLocation()));
			DBObject condition = new BasicDBObject("comment_id", btvComment.getId());

			dbc.update(condition, dbo);
		}
	}

	/**
	 * 插入维护用户数据
	 * 
	 * @param nickname
	 * @param location
	 * @param face
	 * @return
	 */
	public String insertBtvUser(BtvUser btvUser) {
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
		String userid = KeyUtil.getKey(btvUser);
		DBObject dbo = new BasicDBObject("user_id", userid).append("location", btvUser.getLocation()).append("user_screen_name", btvUser.getNickname()).append("profile_image_url", btvUser.getFace()).append("btv", 0);
		dbc.insert(dbo);
		return userid;
	}

	/**
	 * 修改用户数据
	 * 
	 * @param nickname
	 * @param location
	 * @param face
	 * @return
	 */
	public String updateBtvUser(BtvUser btvUser) {
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
		DBObject dbo = new BasicDBObject("$set", new BasicDBObject("profile_image_url", btvUser.getFace()).append("location", btvUser.getLocation()).append("user_screen_name", btvUser.getNickname()).append("profile_image_url", btvUser.getFace()));
		DBObject condition = new BasicDBObject("user_id", btvUser.getUserid());
		dbc.update(condition, dbo);
		return btvUser.getUserid();
	}

	/**
	 * 获取维护用户数据
	 */
	public List<BtvUser> selectBtvUser() {
		List<BtvUser> list = new ArrayList<BtvUser>();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("btv", 0);
		DBCursor dbCursor = dbc.find(queryCondition);
		while (dbCursor.hasNext()) {
			try {
				DBObject o = dbCursor.next();
				BtvUser u = new BtvUser();
				u.setUserid(o.get("user_id").toString());
				u.setLocation(o.get("location").toString());
				u.setNickname(o.get("user_screen_name").toString());
				u.setFace(o.get("profile_image_url").toString());
				list.add(u);
			} catch (Exception e) {
				continue;
			}
		}
		return list;
	}

	/**
	 * 按主键查询
	 * 
	 * @param btvUser
	 * @return
	 */
	public BtvUser selectBtvUserByKey(BtvUser btvUser) {
		BtvUser u = new BtvUser();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_USER);
		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("btv", 0);
		queryCondition.put("user_id", btvUser.getUserid());
		DBCursor dbCursor = dbc.find(queryCondition);
		while (dbCursor.hasNext()) {
			DBObject o = dbCursor.next();
			u.setUserid(o.get("user_id").toString());
			u.setLocation(o.get("location").toString());
			u.setNickname(o.get("user_screen_name").toString());
			u.setFace(o.get("profile_image_url").toString());
			return u;
		}
		return u;
	}

	/**
	 * 按评论主键查询
	 * 
	 * @param commentId
	 * @return
	 */
	public BtvComment selectCommentByKey(String commentId) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		BtvComment b = new BtvComment();
		DB db = MongodbUtil.getInstance().getMasterConnection();
		DBCollection dbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
		DBObject queryCondition = new BasicDBObject();
		queryCondition.put("comment_id", commentId);
		DBCursor dbCursor = dbc.find(queryCondition);
		while (dbCursor.hasNext()) {
			DBObject o = dbCursor.next();
			b.setUserid(o.get("comment_user_id").toString());
			b.setContent(o.get("comment_text").toString());
			b.setFace(o.get("comment_profile_image_url").toString());
			b.setId(o.get("comment_id").toString());
			Date d = (Date) o.get("comment_created_at");
			String dStr = sdf.format(d);
			b.setInputtime(dStr);
			b.setLocation(o.get("comment_location").toString());
			b.setNickname(o.get("comment_user_screen_name").toString());
			b.setUpdatetime(dStr);
			return b;
		}
		return b;
	}

	public static void main(String[] args) {
		WeiboService service = new WeiboService();
		// List<Weibo> weibos = service.selectContentByWeiboId("1821042421");
		// System.out.println(weibos);
	}

	public boolean createDir(String destDirName) {
		File dir = new File(destDirName);
		if (dir.exists()) {
			return false;
		}
		if (!destDirName.endsWith(File.separator))
			destDirName = destDirName + File.separator;
		// 创建单个目录
		if (dir.mkdirs()) {

			return true;
		} else {

			return false;
		}
	}

	public WeiboMapper getWeiboMapper() {
		return weiboMapper;
	}

	public void setWeiboMapper(WeiboMapper weiboMapper) {
		this.weiboMapper = weiboMapper;
	}

	public TaskService getTaskService() {
		return taskService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public SentWeiboMapper getSentDao() {
		return sentDao;
	}

	public void setSentDao(SentWeiboMapper sentDao) {
		this.sentDao = sentDao;
	}

	public QuartzManager getQuartzManager() {
		return quartzManager;
	}

	public void setQuartzManager(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

	public List<Weibo> getRossList() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			DB db = MongodbUtil.getInstance().getMasterConnection();
			DBCollection dbc = db.getCollection(ConstantParam.MONGODB_WEIBO);
			DBCollection commentDbc = db.getCollection(ConstantParam.MONGODB_COMMENT);
			DBObject queryCondition = new BasicDBObject();
			queryCondition.put("task_weibo_id", "Weib1439195251774022");
			Date d = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String time = dateFormat.format(d);
			queryCondition.put("shenhe", 1);
			Date date = null;
			date = sdf.parse(time + " " + "00:00:00");
			// condition.put("comment_created_at", new BasicDBObject("$lt", date));
			queryCondition.put("task_weibo_id", "Weib1439195251774022");
			DBCursor dbCursor = dbc.find(queryCondition).sort(new BasicDBObject("weibo_created_at", -1));
			List<Weibo> weibos = new ArrayList<Weibo>();
			Weibo weibo = null;
			while (dbCursor.hasNext()) {
				DBObject tobj = dbCursor.next();
				weibo = new Weibo();
				String weiboId = (String) tobj.get("weibo_id");
				weibo.setWeiboId(weiboId);
				String weiboText = (String) tobj.get("weibo_text");
				weibo.setContent(weiboText);
				Date weiboCreateTime = (Date) tobj.get("weibo_created_at");
				weibo.setWeiboCreateTime(weiboCreateTime);
				String sourceName = (String) tobj.get("source_name");
				weibo.setSourceName(sourceName);
				String sourceUrl = (String) tobj.get("source_url");
				weibo.setSourceUrl(sourceUrl);
				// weibo.setUserId(example.getUserId());

				Integer commentsCount = this.selectCommentCount(weiboId, commentDbc);
				weibo.setCommentsCount(commentsCount);

				String userCcreenName = (String) tobj.get("user_screen_name");
				weibo.setUserCcreenName(userCcreenName);
				String profileImageUrl = (String) tobj.get("profile_image_url");
				weibo.setProfileImageUrl(profileImageUrl);
				Integer followersCount = (Integer) tobj.get("followers_count");
				weibo.setFollowersCount(followersCount);
				Integer friendsCount = (Integer) tobj.get("friends_count");
				weibo.setFriendsCount(friendsCount);
				String description = (String) tobj.get("description");
				weibo.setDescription(description);
				Integer statusesCount = (Integer) tobj.get("statuses_count");
				weibo.setStatusesCount(statusesCount);
				Integer shenheFlag = (Integer) tobj.get("shenhe");
				weibo.setFlag(shenheFlag);
				weibos.add(weibo);
			}
			return weibos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
