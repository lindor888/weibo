package com.ctvit.weibo.count.service;

import java.util.List;

import com.ctvit.weibo.count.dao.CountWeiboMapper;
import com.ctvit.weibo.count.entity.CountWeibo;

public class CountWeiboService {
	private CountWeiboMapper countWeiboMapper;
	
	/**
	 * 插入
	 * @param countWeibo
	 * @return
	 */
	public int insert(CountWeibo countWeibo){
		return countWeiboMapper.insertSelective(countWeibo);
	}
	
	/**
	 * 更新
	 * @param countWeibo
	 * @return
	 */
	public int update(CountWeibo countWeibo){
		return countWeiboMapper.update(countWeibo);
	}
	
	/**
	 * 查询
	 * @param countWeibo
	 * @return
	 */
	public List<CountWeibo> select(CountWeibo countWeibo){
		return countWeiboMapper.select(countWeibo);
	}

	public CountWeiboMapper getCountWeiboMapper() {
		return countWeiboMapper;
	}

	public void setCountWeiboMapper(CountWeiboMapper countWeiboMapper) {
		this.countWeiboMapper = countWeiboMapper;
	}
}
