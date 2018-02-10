package com.ctvit.weibo.service;

import java.util.List;
import javassist.bytecode.ConstantAttribute;

import org.apache.log4j.Logger;

import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.dao.SentWeiboMapper;
import com.ctvit.weibo.entity.SentWeibo;
import com.ctvit.weibo.entity.SentWeiboExample;
import com.ctvit.weibo.task.service.QuartzManager;
import com.ctvit.weibo.util.ConstantParam;
import com.ctvit.weibo.util.SinaWeiboUtil;

public class SentWeiboService {
	
	private Logger log = Logger.getLogger(SentWeiboService.class);
	private SentWeiboMapper dao;
	private QuartzManager quartzManager;

	/**
	 * 查询已发微博
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<SentWeibo> getAll(SentWeiboExample example) throws Exception {
		if(!"".equals(example.getUserId())) {
			return dao.selectByWeiboUid(example);
		}
		return dao.selectByExample(example);
	}
	/**
	 * 查询已发布微博的数量
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public int getCount(SentWeiboExample example) throws Exception {
		if(!"".equals(example.getUserId())) {
			return dao.countByWeiboUid(example);
		}
		return dao.countByExample(example);
	}
	 
	/**
	 * 添加微博信息
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int add(SentWeibo bean) throws Exception {
		bean.setStatus(ConstantParam.STATUS_SENT);//已发送状态
		return dao.insert(bean);
	}
	
	/**
	 * 删除已发送的微博
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int delete(SentWeibo bean) throws Exception {
		//根据发送ID和微博ID查询
		bean.setDeleteFlag(ConstantParam.STATUS_VALID);
		SentWeibo sent = dao.selectBySentId(bean);
		boolean flag = deleteWeibo(sent);
		if(flag) {
			bean.setStatus(ConstantParam.STATUS_DEL);//删除状态
			dao.updateByPrimaryKeySelective(bean);
			return 1;
		}
		return 0;
	}
	
	/**
	 * 删除定时发微博任务
	 * @param bean
	 * @return
	 * @throws Exception
	 */
	public int deleteTime(SentWeibo bean) throws Exception {
		try {
			quartzManager.removeJob(bean.getId());
			bean.setStatus(ConstantParam.STATUS_DEL);//删除状态
			dao.updateByPrimaryKeySelective(bean);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
	
	/**
	 * 调用新浪微博根据微博ID删除微博
	 * @param weibo
	 * @return
	 */
	private boolean deleteWeibo(SentWeibo weibo) {
		SinaWeiboUtil sinaWeiboUtil = new SinaWeiboUtil();
		String weiboToken = sinaWeiboUtil.getToken(weibo.getAppKey(), weibo.getAppSecret(), 
				weibo.getAppRedirectUri(), weibo.getWeiboUserName(), weibo.getWeiboPassword());
		try {
			Status status = sinaWeiboUtil.deleteById(weibo.getWeiboContentId(), weiboToken);
			if(status != null){
				return true;
			}
		} catch (WeiboException e) {
			log.error("",e);
		}
		return false;
	}
	
	
	public SentWeiboMapper getDao() {
		return dao;
	}

	public void setDao(SentWeiboMapper dao) {
		this.dao = dao;
	}
	public QuartzManager getQuartzManager() {
		return quartzManager;
	}
	public void setQuartzManager(QuartzManager quartzManager) {
		this.quartzManager = quartzManager;
	}

}
