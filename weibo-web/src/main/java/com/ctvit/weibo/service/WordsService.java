package com.ctvit.weibo.service;

import java.util.List;


import com.ctvit.weibo.entity.Words;
import com.ctvit.weibo.entity.WordsExample;
import com.ctvit.weibo.entity.WordsExample.Criteria;
import com.ctvit.weibo.dao.WordsMapper;
import org.apache.commons.lang.StringUtils;

public class WordsService {
	
	private WordsMapper wordsMapper;
	
	/**
	 * 添加
	 * @param bean
	 * @return
	 */
	public int add(Words bean){
		return wordsMapper.insertSelective(bean);
	}
	
	/**
	 * 更新
	 * @param bean
	 * @return
	 */
	public int update(Words bean){
		return wordsMapper.updateByPrimaryKeySelective(bean);
	}
	
	/**
	 * 按主键查询
	 * @param bean
	 * @return
	 */
	public Words findByKey(Words bean){
		return wordsMapper.selectByPrimaryKey(bean.getId());
	}
	
	/**
	 * 查询总数
	 * @param example
	 * @return
	 */
	public int findCount(WordsExample example){
		setCondition(example);
		return wordsMapper.countByExample(example);
	}
	
	/**
	 * 分页查询
	 * @param example
	 * @return
	 */
	public List<Words> findListByPaging(WordsExample example){
		setCondition(example);
		return wordsMapper.selectByExample(example);
	}
	
	/**
	 * 设置查询条件
	 * @param example
	 */
	private void setCondition(WordsExample example){
		if(example!=null){
			Criteria cri = example.createCriteria();
			if(StringUtils.isNotBlank(example.getContent())){
				cri.andContentEqualTo(example.getContent());
			}
		}
	}
	
	/**
	 * 删除
	 * @param bean
	 * @return
	 */
	public int del(Words bean){
		return wordsMapper.deleteByPrimaryKey(bean.getId());
	}

	public WordsMapper getWordsMapper() {
		return wordsMapper;
	}

	public void setWordsMapper(WordsMapper wordsMapper) {
		this.wordsMapper = wordsMapper;
	}

}
