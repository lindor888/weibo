package com.ctvit.weibo.task.dao;

import java.util.List;

import com.ctvit.weibo.task.entity.Task;

public interface TaskMapper {
	Task selectByTaskId(String taskId);
	
	int updateByTaskId(Task task);
	
	int insert(Task task);
	List<Task> selectByCondition(Task task);
	List<Task> selectBasic(Task task);
	int selectCount(Task task);
	Task selectByPrimaryKey(String taskId);
}
