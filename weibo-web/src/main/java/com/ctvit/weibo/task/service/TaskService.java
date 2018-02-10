package com.ctvit.weibo.task.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.quartz.SchedulerException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

import com.ctvit.weibo.task.dao.TaskMapper;
import com.ctvit.weibo.task.entity.Task;
import com.ctvit.weibo.task.entity.TaskFactroy;
import com.ctvit.weibo.util.KeyUtil;

public class TaskService {
	
	private TaskMapper taskMapper;
	
	private QuartzManager quartzManager;
	
	/**
	 * 任务启动时查询
	 * @param taskId
	 * @return
	 */
	public Task selectByTaskId(String taskId){
		return taskMapper.selectByTaskId(taskId);
	}
	
	/**
	 * 通过taskid更新任务信息
	 * @param task
	 * @return
	 */
	public int updateByTaskId(Task task){
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 启动任务
	 * @param task
	 * @return
	 * @throws SchedulerException 
	 * @throws ParseException 
	 */
	public int start(Task task) throws SchedulerException, ParseException{
		task.setTaskState(Task.TASK_STATE_NORMAL);
		if(Task.TASK_ONE_TIME == task.getTaskForever()){
			quartzManager.addOneTimeJob(task.getTaskId(), TaskFactroy.getTaskByType(task));
		}else{
			quartzManager.addJob(task.getTaskId(), TaskFactroy.getTaskByType(task), task.getTaskFrequency());
		}
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 暂停任务
	 * @param task
	 * @return
	 * @throws SchedulerException 
	 */
	public int pause(Task task) throws SchedulerException{
		task.setTaskState(Task.TASK_STATE_PAUSE);
		quartzManager.pauseJob(task.getTaskId());
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 恢复任务
	 * @param task
	 * @return
	 * @throws SchedulerException 
	 */
	public int regain(Task task) throws SchedulerException{
		task.setTaskState(Task.TASK_STATE_NORMAL);
		quartzManager.resumeJob(task.getTaskId());
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 还原任务
	 * @param task
	 * @return
	 */
	public int revert(Task task){
		task.setTaskState(Task.TASK_STATE_STOP);
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 删除任务
	 * @param task
	 * @return
	 * @throws SchedulerException 
	 */
	public int delete(Task task) throws SchedulerException{
		task.setTaskState(Task.TASK_STATE_DELETE);
		quartzManager.removeJob(task.getTaskId());
		return taskMapper.updateByTaskId(task);
	}
	
	
	/**
	 * 添加高级任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	public int add(Task task) throws Exception {
		task.setTaskId(KeyUtil.getKey(task));
		task.setTaskLevel(Task.TASK_LEVEL_SUPER);//高级任务
		task.setTaskState(Task.TASK_STATE_READY);//添加完之后 任务默认是就绪状态
		if(task != null && task.isForever()) {//0为永远执行/1为执行一次/2就看taskFrequency
			task.setTaskForever(Task.TASK_FOREVER);
		} else if("3".equals(task.getTaskFrequencySort())){
			task.setTaskForever(Task.TASK_ONE_TIME);
		} else {
			task.setTaskForever(Task.TASK_FREQUENCY);
		}
		
		String keyWord = task.getKeyWord();
		String keyWordArr[] = keyWord.split(",");
		List<Task> tasks = new ArrayList<Task>();
		tasks.add(task);
		//如果有关键词属性，task为,隔开的第一个关键词（taskAdd.jsp 460行）,所以这里从第二个开始取
		if(keyWordArr != null && keyWordArr.length > 1) {
			for(int i=1;i<keyWordArr.length;i++) {
				if(StringUtils.isNotBlank(keyWordArr[i])) {
					Task t = new Task();
					t.setTaskId(KeyUtil.getKey(task));//主键
					setTaskField(task, t);
					String xml = t.getTaskConfigXml();
					String newXml = xml.substring(0, xml.indexOf("<q>")+3) + keyWordArr[i] + xml.substring(xml.indexOf("</q>"),xml.length());
					t.setTaskConfigXml(newXml);
					tasks.add(t);
				}
			}
		}
		return insertBatch(tasks);
	}
	
	/**
	 * 修改时先查询出任务信息
	 * @param taskId
	 * @return
	 */
	public Task selectByPrimaryKey(String taskId){
		Task task = taskMapper.selectByPrimaryKey(taskId);
		if(task != null && task.getTaskForever() != null) {
			if(task.getTaskForever() == Task.TASK_FOREVER) {
				task.setForever(true);
			}
		}
		return task;
	}
	
	/**
	 * 查询基础任务
	 * @param weiboId
	 * @return
	 * @throws Exception
	 */
	public List<Task> selectBasic(Task task) throws Exception {
		return taskMapper.selectBasic(task);
	}
	
	/**
	 * 条件查询任务（可以带分页）
	 * @param weiboId
	 * @return
	 * @throws Exception
	 */
	public List<Task> selectByCondition(Task task) throws Exception {
		return taskMapper.selectByCondition(task);
	}
	
	/**
	 * 查询数量
	 * @param weiboId
	 * @return
	 * @throws Exception
	 */
	public int selectCount(Task task) throws Exception {
		return taskMapper.selectCount(task);
	}
	
	/**
	 * 启动任务(第一次启动任务，需填写任务信息)
	 * @param task
	 * @throws Exception
	 */
	public int startFirst(Task task) throws Exception {
		if(task != null && task.isForever()) {
			task.setTaskForever(Task.TASK_FOREVER);
		} else if("3".equals(task.getTaskFrequencySort())){
			task.setTaskForever(Task.TASK_ONE_TIME);
		} else {
			task.setTaskForever(Task.TASK_FREQUENCY);
		}
		task.setTaskState(Task.TASK_STATE_READY);
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 修改任务
	 * @param task
	 * @throws Exception
	 */
	public int update(Task task) throws Exception {
		if(task != null && task.isForever()) {
			task.setTaskForever(Task.TASK_FOREVER);
		} else if("3".equals(task.getTaskFrequencySort())){
			task.setTaskForever(Task.TASK_ONE_TIME);
		} else {
			task.setTaskForever(Task.TASK_FREQUENCY);
		}
		if(task.getTaskState() != Task.TASK_STATE_STOP && task.getTaskState() != Task.Task_STATE_FINISH && task.getTaskState() != Task.TASK_STATE_READY && task.getTaskState()!= Task.TASK_STATE_UNUSUAL){
			task.setTaskState(Task.TASK_STATE_PAUSE);
			quartzManager.pauseJob(task.getTaskId());
		}
		return taskMapper.updateByTaskId(task);
	}
	
	/**
	 * 批量添加任务
	 * @param tasks
	 * @return
	 * @throws Exception
	 */
	public int insertBatch(List<Task> tasks) throws Exception {
		ApplicationContext context = ContextLoaderListener.getCurrentWebApplicationContext();
		DefaultSqlSessionFactory sessionFactory = (DefaultSqlSessionFactory) context.getBean("sqlSessionFactory");
		SqlSession session = sessionFactory.openSession();
		session.getConnection().setAutoCommit(false);
		try {
			TaskMapper dao = session.getMapper(TaskMapper.class);
			if(tasks != null && tasks.size() > 0) {
				for(Task task : tasks) {
					dao.insert(task);
				}
			}
			session.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		} finally {
			if(session != null) {
				session.close();
			}
		}
		return 1;
	}
	
	private Task setTaskField(Task fromTask, Task toTask) {
		toTask.setWeiboId(fromTask.getWeiboId());
		toTask.setTaskType(fromTask.getTaskType());
		toTask.setTaskCreateTime(fromTask.getTaskCreateTime());
		toTask.setTaskBeginTime(fromTask.getTaskBeginTime());
		toTask.setTaskEndTime(fromTask.getTaskEndTime());
		toTask.setTaskForever(fromTask.getTaskForever());
		toTask.setTaskFrequency(fromTask.getTaskFrequency());
		toTask.setTaskState(fromTask.getTaskState());
		toTask.setTaskConfigXml(fromTask.getTaskConfigXml());
		toTask.setTaskLevel(fromTask.getTaskLevel());
		toTask.setTaskDetailTime(fromTask.getTaskDetailTime());
		toTask.setTaskEverySort(fromTask.getTaskEverySort());
		toTask.setTaskFrequencySort(fromTask.getTaskFrequencySort());
		return toTask;
	}
	
	public TaskMapper getTaskMapper() {
		return taskMapper;
	}

	public void setTaskMapper(TaskMapper taskMapper) {
		this.taskMapper = taskMapper;
	}

	public QuartzManager getQuartzManager() {
		return quartzManager;
	}

	public void setQuartzManager(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

}
