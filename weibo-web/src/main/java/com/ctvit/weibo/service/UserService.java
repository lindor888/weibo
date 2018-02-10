package com.ctvit.weibo.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.ctvit.weibo.dao.UserMapper;
import com.ctvit.weibo.entity.User;
import com.ctvit.weibo.entity.UserExample;

public class UserService
{
	private UserMapper userMapper;

	public UserMapper getUserMapper()
	{
		return userMapper;
	}

	public void setUserMapper(UserMapper userMapper)
	{
		this.userMapper = userMapper;
	}

	public List<User> selectByExample(UserExample example)
	{
		return userMapper.selectByExample(example);
	}

	public int insert(User record)
	{
		return userMapper.insert(record);
	}

	public int updateByExample(@Param("record") User record,
			@Param("example") UserExample example)
	{
		return userMapper.updateByExample(record, example);
		
	}
}
