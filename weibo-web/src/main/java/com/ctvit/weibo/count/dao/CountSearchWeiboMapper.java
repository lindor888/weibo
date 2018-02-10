package com.ctvit.weibo.count.dao;

import com.ctvit.weibo.count.entity.CountSearchWeibo;
import com.ctvit.weibo.count.entity.CountSearchWeiboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountSearchWeiboMapper {
    int countByExample(CountSearchWeiboExample example);

    int deleteByExample(CountSearchWeiboExample example);

    int deleteByPrimaryKey(Integer countId);

    int insert(CountSearchWeibo record);

    int insertSelective(CountSearchWeibo record);

    List<CountSearchWeibo> selectByExample(CountSearchWeiboExample example);

    CountSearchWeibo selectByPrimaryKey(Integer countId);

    int updateByExampleSelective(@Param("record") CountSearchWeibo record, @Param("example") CountSearchWeiboExample example);

    int updateByExample(@Param("record") CountSearchWeibo record, @Param("example") CountSearchWeiboExample example);

    int updateByPrimaryKeySelective(CountSearchWeibo record);

    int updateByPrimaryKey(CountSearchWeibo record);
    
    int update(CountSearchWeibo countSearchWeibo);
    
    List<CountSearchWeibo> select(CountSearchWeibo countSearchWeibo);
}