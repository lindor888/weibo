package com.ctvit.weibo.dao;

import com.ctvit.weibo.entity.SentWeibo;
import com.ctvit.weibo.entity.SentWeiboExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SentWeiboMapper {
    int countByExample(SentWeiboExample example);
    int countByWeiboId(SentWeiboExample example);//通过微博ID查询数量
    int countByWeiboUid(SentWeiboExample example);//通过微博UID查询数量

    int deleteByExample(SentWeiboExample example);

    int deleteByPrimaryKey(String id);

    int insert(SentWeibo record);

    int insertSelective(SentWeibo record);

    List<SentWeibo> selectByExample(SentWeiboExample example);
    List<SentWeibo> selectByWeiboId(SentWeiboExample example);//通过微博ID查询已发微博内容
    List<SentWeibo> selectSentWeibo(SentWeiboExample example);//查询已发布的微博(包括已发布和定时发布的)
    List<SentWeibo> selectByWeiboUid(SentWeiboExample example);//通过微博UID查询已发微博内容

    SentWeibo selectByPrimaryKey(String id);
    SentWeibo selectBySentId(SentWeibo bean);//根据已发微博ID查询内容

    int updateByExampleSelective(@Param("record") SentWeibo record, @Param("example") SentWeiboExample example);

    int updateByExample(@Param("record") SentWeibo record, @Param("example") SentWeiboExample example);

    int updateByPrimaryKeySelective(SentWeibo record);

    int updateByPrimaryKey(SentWeibo record);
}