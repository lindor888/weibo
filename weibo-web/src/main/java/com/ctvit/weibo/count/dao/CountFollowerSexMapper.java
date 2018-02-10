package com.ctvit.weibo.count.dao;

import com.ctvit.weibo.count.entity.CountFollowerSex;
import com.ctvit.weibo.count.entity.CountFollowerSexExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountFollowerSexMapper {
    int countByExample(CountFollowerSexExample example);

    int deleteByExample(CountFollowerSexExample example);

    int deleteByPrimaryKey(Integer countId);

    int insert(CountFollowerSex record);

    int insertSelective(CountFollowerSex record);

    List<CountFollowerSex> selectByExample(CountFollowerSexExample example);

    CountFollowerSex selectByPrimaryKey(Integer countId);

    int updateByExampleSelective(@Param("record") CountFollowerSex record, @Param("example") CountFollowerSexExample example);

    int updateByExample(@Param("record") CountFollowerSex record, @Param("example") CountFollowerSexExample example);

    int updateByPrimaryKeySelective(CountFollowerSex record);

    int updateByPrimaryKey(CountFollowerSex record);
    
    int update(CountFollowerSex countFollowerSex);
    
    CountFollowerSex select(CountFollowerSex countFollowerSex);
}