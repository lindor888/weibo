package com.ctvit.weibo.count.service;

import java.util.List;

import com.ctvit.weibo.count.dao.CountFollowerDistrictMapper;
import com.ctvit.weibo.count.entity.CountFollowerDistrict;

public class CountFollowerDistrictService {
	private CountFollowerDistrictMapper countFollowerDistrictMapper;
	
	/**
	 * 插入
	 * @param countFollowerDistrict
	 * @return
	 */
	public int insert(CountFollowerDistrict countFollowerDistrict){
		return countFollowerDistrictMapper.insertSelective(countFollowerDistrict);
	}
	
	/**
	 * 更新
	 * @param countFollowerDistrict
	 * @return
	 */
	public int update(CountFollowerDistrict countFollowerDistrict){
		return countFollowerDistrictMapper.update(countFollowerDistrict);
	}
	
	/**
	 * 查询
	 * @param countFollowerDistrict
	 * @return
	 */
	public List<CountFollowerDistrict> select(CountFollowerDistrict countFollowerDistrict){
		return countFollowerDistrictMapper.select(countFollowerDistrict);
	}
	

	public CountFollowerDistrictMapper getCountFollowerDistrictMapper() {
		return countFollowerDistrictMapper;
	}

	public void setCountFollowerDistrictMapper(
			CountFollowerDistrictMapper countFollowerDistrictMapper) {
		this.countFollowerDistrictMapper = countFollowerDistrictMapper;
	}

}
