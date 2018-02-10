package com.ctvit.weibo.dao;

import com.ctvit.weibo.entity.FriendWeibo;
import com.ctvit.weibo.entity.FriendWeiboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FriendWeiboMapper {
    int countByExample(FriendWeiboExample example);

    int deleteByExample(FriendWeiboExample example);

    int deleteByPrimaryKey(String friendWeiboId);

    int insert(FriendWeibo record);

    int insertSelective(FriendWeibo record);

    List<FriendWeibo> selectByExample(FriendWeiboExample example);

    FriendWeibo selectByPrimaryKey(String friendWeiboId);

    int updateByExampleSelective(@Param("record") FriendWeibo record, @Param("example") FriendWeiboExample example);

    int updateByExample(@Param("record") FriendWeibo record, @Param("example") FriendWeiboExample example);

    int updateByPrimaryKeySelective(FriendWeibo record);

    int updateByPrimaryKey(FriendWeibo record);
   
    int delete(String id);
}