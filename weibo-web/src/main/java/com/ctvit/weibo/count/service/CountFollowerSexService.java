package com.ctvit.weibo.count.service;

import com.ctvit.weibo.count.dao.CountFollowerSexMapper;
import com.ctvit.weibo.count.entity.CountFollowerSex;

public class CountFollowerSexService {
	private CountFollowerSexMapper countFollowerSexMapper;
	
	/**
	 * 插入
	 * @param countFollowerSex
	 * @return
	 */
	public int insert(CountFollowerSex countFollowerSex){
		return countFollowerSexMapper.insertSelective(countFollowerSex);
	}
	
	/**
	 * 更新
	 * @param countFollowerSex
	 * @return
	 */
	public int update(CountFollowerSex countFollowerSex){
		return countFollowerSexMapper.update(countFollowerSex);
	}
	
	/**
	 * 查询
	 * @param countFollowerSex
	 * @return
	 */
	public CountFollowerSex select(CountFollowerSex countFollowerSex){
		return countFollowerSexMapper.select(countFollowerSex);
	}

	public CountFollowerSexMapper getCountFollowerSexMapper() {
		return countFollowerSexMapper;
	}

	public void setCountFollowerSexMapper(CountFollowerSexMapper countFollowerSexMapper) {
		this.countFollowerSexMapper = countFollowerSexMapper;
	}

}
