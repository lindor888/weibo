package com.ctvit.weibo.dao;

import com.ctvit.weibo.entity.Weibo;
import com.ctvit.weibo.entity.WeiboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface WeiboMapper {
    int countByExample(WeiboExample example);

    int deleteByExample(WeiboExample example);

    int insert(Weibo record);

    int insertSelective(Weibo record);

    List<Weibo> selectByExample(WeiboExample example);

    int updateByExampleSelective(@Param("record") Weibo record, @Param("example") WeiboExample example);

    int updateByExample(@Param("record") Weibo record, @Param("example") WeiboExample example);
    
    int updateTokenByWeiboId(Weibo weibo);
    
    
    Weibo searchByWeiboUserName(Weibo bean);
    Weibo searchById(String weiboId);
    Weibo searchWeiboAppById(Weibo bean);
    int update(Weibo weibo);
    
    //查询用户下的所有微博信息
    List<Weibo> selectByUserId(Weibo record);
}