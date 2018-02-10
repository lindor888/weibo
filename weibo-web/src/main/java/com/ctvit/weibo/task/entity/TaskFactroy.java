package com.ctvit.weibo.task.entity;

import com.ctvit.weibo.task.entity.tasklist.TaskMyFriendWeiboCommentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyFriendWeiboContentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyFriendWeiboRepostCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiBoFollowerCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiboAccountCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiboCommentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiboContentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiboFriendCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskMyWeiboRepostCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboAccountCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboCommentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboContentCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboFollowerCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboRepostCollect;
import com.ctvit.weibo.task.entity.tasklist.TaskThirdWeiboSearchCollect;

/**
 * 任务工厂
 * @author tqc
 *
 */
public class TaskFactroy {
	public static Task getTaskByType(Task task){
		int type = task.getTaskType();
		if(type == Task.TASK_TYPE_MY_WEIBO_CONTENT_COLLECT){
			return new TaskMyWeiboContentCollect();
		}else if(type == Task.TASK_TYPE_MY_WEIBO_COMMENT_COLLECT){
			return new TaskMyWeiboCommentCollect();
		}else if(type == Task.TASK_TYPE_MY_WEIBO_REPOST_COLLECT){
			return new TaskMyWeiboRepostCollect();
		}else if(type == Task.TASK_TYPE_MY_WEIBO_ACCOUNT_COLLECT){
			return new TaskMyWeiboAccountCollect();
		}else if(type == Task.TASK_TYPE_MY_WEIBO_FRIEND_COLLECT){
			return new TaskMyWeiboFriendCollect();
		}else if(type == Task.TASK_TYPE_MY_WEIBO_FOLLOWER_COLLECT){
			return new TaskMyWeiBoFollowerCollect();
		}else if(type == Task.TASK_TYPE_MY_FRIEND_WEIBO_CONTENT_COLLECT){
			return new TaskMyFriendWeiboContentCollect();
		}else if(type == Task.TASK_TYPE_MY_FRIEND_WEIBO_COMMENT_COLLECT){
			return new TaskMyFriendWeiboCommentCollect();
		}else if(type == Task.TASK_TYPE_MY_FRIEND_WEIBO_REPOST_COLLECT){
			return new TaskMyFriendWeiboRepostCollect();
		}else if(type == Task.TASK_TYPE_THIRD_WEIBO_SEARCH_COLLECT){
			return new TaskThirdWeiboSearchCollect();
		}else if(type == Task.TASK_TYPE_THIRD_WEIBO_REPOST_COLLECT){
			return new TaskThirdWeiboRepostCollect();
		}else if(type == Task.TASK_TYPE_THIRD_WEIBO_CONTENT_COLLECT){
			return new TaskThirdWeiboContentCollect();
		}else if(type == Task.TASK_TYPE_THIRD_WEIBO_FOLLOWER_COLLECT){
			return new TaskThirdWeiboFollowerCollect();
		}else if(type == Task.TASK_TYPE_THIRD_WEIBO_COMMENT_COLLECT){
			return new TaskThirdWeiboCommentCollect();
		}else{
			return new TaskThirdWeiboAccountCollect();
		}
		
		
	}
}
