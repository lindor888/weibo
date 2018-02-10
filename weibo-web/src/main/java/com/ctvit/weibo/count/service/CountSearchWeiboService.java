package com.ctvit.weibo.count.service;

import java.util.List;

import com.ctvit.weibo.count.dao.CountSearchWeiboMapper;
import com.ctvit.weibo.count.entity.CountSearchWeibo;

public class CountSearchWeiboService {
	private CountSearchWeiboMapper countSearchWeiboMapper;
	
	/**
	 * 插入数据
	 * @param countSearchWeibo
	 * @return
	 */
	public int insert(CountSearchWeibo countSearchWeibo){
		return countSearchWeiboMapper.insertSelective(countSearchWeibo);
	}
	
	/**
	 * 更新数据
	 * @param countSearchWeibo
	 * @return
	 */
	public int update(CountSearchWeibo countSearchWeibo){
		return countSearchWeiboMapper.update(countSearchWeibo);
	}
	
	/**
	 * 查询数据
	 * @param countSearchWeibo
	 * @return
	 */
	public List<CountSearchWeibo> select(CountSearchWeibo countSearchWeibo){
		return countSearchWeiboMapper.select(countSearchWeibo);
	}
	
	

	public CountSearchWeiboMapper getCountSearchWeiboMapper() {
		return countSearchWeiboMapper;
	}

	public void setCountSearchWeiboMapper(CountSearchWeiboMapper countSearchWeiboMapper) {
		this.countSearchWeiboMapper = countSearchWeiboMapper;
	}

}
