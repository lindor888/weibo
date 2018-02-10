package com.ctvit.weibo.task.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.quartz.SchedulerException;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.ctvit.weibo.task.entity.Task;

public class TaskStartServlet extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = -315628451397255125L;
	
	
	/**
	 * 将等待和运行的任务加入队列，将暂停的队伍加入队列后马上暂停
	 */
	@Override
	public void init() throws ServletException {
		WebApplicationContext wac = ContextLoaderListener.getCurrentWebApplicationContext();
		TaskService taskService =(TaskService)wac.getBean("taskService");
		List<Task> taskList = new ArrayList<Task>();
		Task selectTask = new Task();
		selectTask.setTaskState(Task.TASK_STATE_NORMAL);
		List<Task> normalTaskList = new ArrayList<Task>();
		try {
			normalTaskList = taskService.selectByCondition(selectTask);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		selectTask.setTaskState(Task.TASK_STATE_WAIT);
		List<Task> waitTaskList = new ArrayList<Task>();
		try {
			waitTaskList = taskService.selectByCondition(selectTask);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		taskList.addAll(normalTaskList);
		taskList.addAll(waitTaskList);
		for(Task task:taskList){
			try {
				taskService.start(task);
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		selectTask.setTaskState(Task.TASK_STATE_PAUSE);
		List<Task> pauseTaskList = new ArrayList<Task>();
		try {
			pauseTaskList = taskService.selectByCondition(selectTask);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for(Task task:pauseTaskList){
			try {
				taskService.start(task);
				taskService.pause(task);
			} catch (SchedulerException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
}
