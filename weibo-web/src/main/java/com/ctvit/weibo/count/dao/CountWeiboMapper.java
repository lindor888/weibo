package com.ctvit.weibo.count.dao;

import com.ctvit.weibo.count.entity.CountWeibo;
import com.ctvit.weibo.count.entity.CountWeiboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CountWeiboMapper {
    int countByExample(CountWeiboExample example);

    int deleteByExample(CountWeiboExample example);

    int deleteByPrimaryKey(Integer countId);

    int insert(CountWeibo record);

    int insertSelective(CountWeibo record);

    List<CountWeibo> selectByExample(CountWeiboExample example);

    CountWeibo selectByPrimaryKey(Integer countId);

    int updateByExampleSelective(@Param("record") CountWeibo record, @Param("example") CountWeiboExample example);

    int updateByExample(@Param("record") CountWeibo record, @Param("example") CountWeiboExample example);

    int updateByPrimaryKeySelective(CountWeibo record);

    int updateByPrimaryKey(CountWeibo record);
    
    int update(CountWeibo countWeibo);
    
    List<CountWeibo> select(CountWeibo countWeibo);
}