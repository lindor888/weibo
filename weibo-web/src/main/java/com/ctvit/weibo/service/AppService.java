package com.ctvit.weibo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ctvit.weibo.dao.AppMapper;
import com.ctvit.weibo.entity.App;
import com.ctvit.weibo.entity.AppExample;

public class AppService {

	private AppMapper dao;

	/**
	 * 分页获取我的应用
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<App> getByPaging(AppExample example) throws Exception {
		return dao.selectByExample(example);
	}

	/**
	 * 根据条件查询应用列表
	 */
	public List<App> selectByExample(AppExample example) {
		return dao.selectByExample(example);
	}

	/**
	 * 更新应用信息
	 */
	public int updateByExample(@Param("record") App record,
			@Param("example") AppExample example) {
		return dao.updateByExample(record, example);
	}

	/**
	 * 添加应用
	 */
	public int insert(App record) {
		return dao.insert(record);
	}

	public int deleteByExample(AppExample example) {
		return dao.deleteByExample(example);
	}

	/**
	 * 根据appId查询App
	 */
	public App searchById(String appId) {
		return dao.searchById(appId);
	}

	/**
	 * 更新应用
	 */
	public int update(App app) {
		return dao.update(app);
	}

	/**
	 * 删除应用
	 */
	public int delete(App app) {
		return dao.delete(app);
	}

	/**
	 * 根据appName和userid查询APP
	 */
	public App searchByName(App app) {
		return dao.searchByName(app);
	}

	/**
	 * 获取我的应用数
	 * 
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public int getCount(AppExample example) throws Exception {
		return dao.countByExample(example);
	}

	public AppMapper getDao() {
		return dao;
	}

	public void setDao(AppMapper dao) {
		this.dao = dao;
	}

}
