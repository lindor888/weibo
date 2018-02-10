package com.ctvit.weibo.service;

import java.util.List;

import weibo4j.model.User;
import weibo4j.model.WeiboException;

import com.ctvit.weibo.dao.AppMapper;
import com.ctvit.weibo.dao.FriendWeiboMapper;
import com.ctvit.weibo.entity.App;
import com.ctvit.weibo.entity.FriendWeibo;
import com.ctvit.weibo.entity.FriendWeiboExample;
import com.ctvit.weibo.entity.FriendWeiboExample.Criteria;
import com.ctvit.weibo.util.KeyUtil;
import com.ctvit.weibo.util.SinaWeiboUtil;

public class FriendWeiboService {
	
	private FriendWeiboMapper dao;
	private AppMapper appMapper;

	
	/**
	 * 分页查询我的关注
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public List<FriendWeibo> getByPaging(FriendWeiboExample example) throws Exception {
		return dao.selectByExample(example);
	}
	
	/**
	 * 我的关注数量
	 * @param example
	 * @return
	 * @throws Exception
	 */
	public int getCount(FriendWeiboExample example) throws Exception {
		return dao.countByExample(example);
	}
	
	private void trans(FriendWeiboExample example) throws Exception {
		if(example != null) {
			Criteria c = example.createCriteria();
			String friendWeiboUid = example.getFriendWeiboUid();
			if(friendWeiboUid != null && !"".equals(friendWeiboUid)) {
				c.andFriendWeiboUidLike(friendWeiboUid + "%");
			}
			example.getOredCriteria().add(c);
 		}
	}
	
	/**
	 * 更具主键删除我的关注
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public int delete(FriendWeibo bean) throws Exception {
			boolean flag = true;
			while(flag) {
				try {
					SinaWeiboUtil s = new SinaWeiboUtil();
					s.destroyFriendshipsDestroyById(bean.getWeiboToken(),bean.getFriendWeiboUid());
					flag = false;
				} catch (WeiboException e) {
					int error = e.getErrorCode();
					//10006 token失效 重新获取token
					if(error==10006){
						App app = appMapper.searchById(bean.getAppId());
						SinaWeiboUtil s = new SinaWeiboUtil();
						String accessToken = s.getToken(app.getAppKey(), app.getAppSecret(), app.getAppRedirectUri(), 
								bean.getWeiboUserName(), bean.getWeiboPassword());
						if(accessToken != null) flag = true;
					//10023 超过次数调用限制
					}else if(error==10022||error==10023||error==10024){
						return 2;//该小时内调用新浪接口次数已超，请等待。
					}else{
						return 3;//其他错误
					}
				}
			}
		dao.delete(bean.getFriendWeiboId());
		return 1;
	}
	
	/**
	 * 添加微博
	 * @param friendWeibo
	 * @return
	 * @throws Exception
	 */
	public int add(FriendWeibo bean) throws Exception {
		boolean flag = true;
		while(flag) {
			try {
				SinaWeiboUtil s = new SinaWeiboUtil();
				User user = s.createFriendshipsById(bean.getWeiboToken(),bean.getFriendWeiboUid());
				String friendWeiboName = user.getName();
				bean.setFriendWeiboName(friendWeiboName);
				flag = false;
			} catch (WeiboException e) {
				int error = e.getErrorCode();
				//10006 token失效 重新获取token
				if(error==10006){
					App app = appMapper.searchById(bean.getAppId());
					SinaWeiboUtil s = new SinaWeiboUtil();
					String accessToken = s.getToken(app.getAppKey(), app.getAppSecret(), app.getAppRedirectUri(), 
							bean.getWeiboUserName(), bean.getWeiboPassword());
					if(accessToken != null) flag = true;
				//10023 超过次数调用限制
				}else if(error==10022||error==10023||error==10024){
					return 2;//该小时内调用新浪接口次数已超，请等待。
				}else{
					return 3;//其他错误
				}
			}
		}
		bean.setFriendWeiboId(KeyUtil.getKey(bean));
		return dao.insert(bean);
	}

	public FriendWeiboMapper getDao() {
		return dao;
	}

	public void setDao(FriendWeiboMapper dao) {
		this.dao = dao;
	}

	public AppMapper getAppMapper() {
		return appMapper;
	}

	public void setAppMapper(AppMapper appMapper) {
		this.appMapper = appMapper;
	}
	
}
