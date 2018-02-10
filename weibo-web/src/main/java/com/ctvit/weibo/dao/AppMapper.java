package com.ctvit.weibo.dao;

import com.ctvit.weibo.entity.App;
import com.ctvit.weibo.entity.AppExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AppMapper
{
	int countByExample(AppExample example);

	int deleteByExample(AppExample example);

	int insert(App record);

	int insertSelective(App record);

	List<App> selectByExample(AppExample example);

	int updateByExampleSelective(@Param("record") App record,
			@Param("example") AppExample example);

	int updateByExample(@Param("record") App record,
			@Param("example") AppExample example);

	App searchById(String appId);
	
	int update(App app);
	
	int delete(App app);
	
	App searchByName(App app);
}