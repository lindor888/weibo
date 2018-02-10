package com.ctvit.weibo.count.dao;

import com.ctvit.weibo.count.entity.CountFollowerDistrict;
import com.ctvit.weibo.count.entity.CountFollowerDistrictExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountFollowerDistrictMapper {
    int countByExample(CountFollowerDistrictExample example);

    int deleteByExample(CountFollowerDistrictExample example);

    int deleteByPrimaryKey(Integer countId);

    int insert(CountFollowerDistrict record);

    int insertSelective(CountFollowerDistrict record);

    List<CountFollowerDistrict> selectByExample(CountFollowerDistrictExample example);

    CountFollowerDistrict selectByPrimaryKey(Integer countId);

    int updateByExampleSelective(@Param("record") CountFollowerDistrict record, @Param("example") CountFollowerDistrictExample example);

    int updateByExample(@Param("record") CountFollowerDistrict record, @Param("example") CountFollowerDistrictExample example);

    int updateByPrimaryKeySelective(CountFollowerDistrict record);

    int updateByPrimaryKey(CountFollowerDistrict record);
    
    int update(CountFollowerDistrict countFollowerDistrict);
    
    List<CountFollowerDistrict> select(CountFollowerDistrict countFollowerDistrict);
}