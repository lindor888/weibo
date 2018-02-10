package com.ctvit.weibo.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.ctvit.weibo.entity.User;
import com.ctvit.weibo.entity.UserExample;
import com.ctvit.weibo.service.UserService;
import com.ctvit.weibo.util.KeyUtil;
import com.ctvit.weibo.util.LoginCode;
import com.ctvit.weibo.util.MD5Util;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 账户管理
 * 
 * @author Administrator
 * 
 */
public class UserAction extends ActionSupport {

	private static final long serialVersionUID = 3172461683182708318L;

	private User bean;

	private Map<String, Object> queryJson;

	private UserService userService;

	private String logincode; // 验证码

	private String newPassword;
	private String errMsg;//错误提示信息
	/**
	 * 日志对象
	 */
	private Logger log = Logger.getLogger(UserAction.class);

	public String indexPre() throws Exception {
		return "indexPre";
	}
	
	public String loginPre() {
		User user = (User) ActionContext.getContext().getSession().get(User.SESSION_USER);
		if(user != null) {
			return "login";
		}
		return "loginPre";
	}

	/**
	 * 用户登录
	 * 
	 * @return
	 */
	public String login() throws Exception {
		try {
			User user = (User) ActionContext.getContext().getSession().get(User.SESSION_USER);
			/*if (user != null && !user.getUserName().equals("")) {
				queryJson.put("msg", "success");
				return "dataList";
			} else {*/
				queryJson = new HashMap<String,Object>();
				UserExample example = new UserExample();
				// 密码加密与数据库加密后的密码去比较
				example.createCriteria().andUserNameEqualTo(bean.getUserName());
				List<User> userList = userService.selectByExample(example);
				if (userList == null || userList.size() < 1) {//验证用户名是否正确
					queryJson.put("msg", "用户不存在或该用户为CMS用户");
					return "dataList";
				}
				
				userList.clear();
				example = new UserExample();
				example.createCriteria().andUserNameEqualTo(bean.getUserName())
				.andPasswordEqualTo(MD5Util.md5(bean.getPassword()));
				userList = userService.selectByExample(example);
				if (userList == null || userList.size() < 1) {//验证密码是否正确
					queryJson.put("msg", "密码错误");
					return "dataList";
				} else {
					//验证码是否正确
					String certCodeInput = (String) ServletActionContext.getRequest().getSession().getAttribute(LoginCode.RANDOM_CODE_SESSION);
					if (certCodeInput != null && !"".equals(certCodeInput)
							&& certCodeInput.equals(logincode)) {
						ActionContext.getContext().getSession().put(User.SESSION_USER, (User) userList.get(0));
						queryJson.put("msg", "success");
						return "dataList";
					} else {
						queryJson.put("msg", "验证码输入错误");
						return "dataList";
					}
				}
			//}
		} catch (Exception e) {
			e.printStackTrace();
			queryJson.put("msg", "程序出错，请联系管理员");
			return "dataList";
		}
	}

	/**
	 * 修改密码,必须先登录
	 */
	public String modify() throws Exception {

		try {
			User user = (User) ActionContext.getContext().getSession().get(User.SESSION_USER);
			if (user != null) {
				// 验证旧密码与用户登录密码是否一致
				if (!MD5Util.md5(bean.getPassword()).equals(user.getPassword())) {
					this.queryJson = new HashMap<String, Object>();
					queryJson.put("msg", "3");
					return "modifyPass";
				}
				// user,session中保存的用户信息,bean是modifypass.jsp提交过来的user对象。
				bean = user;
				System.out.println(newPassword);
				// 新密码加密
				bean.setPassword(MD5Util.md5(newPassword));
				UserExample example = new UserExample();
				example.createCriteria().andUserNameEqualTo(bean.getUserName())
						.andUserIdEqualTo(bean.getUserId());
				int state = userService.updateByExample(bean, example);
				System.out.println(state);
				if (state != 0) {
					this.queryJson = new HashMap<String, Object>();
					queryJson.put("msg", "1");
					return "modifyPass";
				}
			}

		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 注册新用户
	 * 
	 * @return
	 */
	public String register() throws Exception {
		try {
			String key = KeyUtil.getKey(bean);
			System.out.println(key);
			if (key.length() > 0 && !key.equals("")) {
				bean.setUserId(key);
			}
			int state = 0;
			if (bean.getUserName() != null && !bean.getUserName().equals("")
					&& !bean.getPassword().equals("")
					&& bean.getPassword() != null) {
				// 密码加密
				bean.setPassword(MD5Util.md5(bean.getPassword()));
				state = userService.getUserMapper().insert(bean);
			}
			// 注册成功
			if (state != 0) {
				this.queryJson = new HashMap<String, Object>();
				queryJson.put("msg", "1");
				return "register";
			} else {
				this.queryJson = new HashMap<String, Object>();
				queryJson.put("msg", "2");
				return "register";
				// return "re-registration";
			}

		} catch (Exception e) {
			log.error("", e);
			throw e;
		}
	}

	/**
	 * 检查用户名是否存在
	 * 
	 * @return
	 */
	public String selectByName() throws Exception {
		try {
			UserExample userExample = new UserExample();
			userExample.createCriteria().andUserNameEqualTo(bean.getUserName());
			List<User> userList = userService.selectByExample(userExample);
			System.out.println(userList);
			if (userList.size() > 0) {
				this.queryJson = new HashMap<String, Object>();
				queryJson.put("msg", "1");
				return "getUser";
			}
		} catch (Exception e) {
			log.error("", e);
		}
		return null;
	}

	/**
	 * 退出登录
	 * 
	 * @return
	 */
	public String logoutLogin() throws Exception {
		// 如果存在user,清除
		if (ActionContext.getContext().getSession().containsKey(User.SESSION_USER)) {
			ActionContext.getContext().getSession().remove(User.SESSION_USER);
			return "re_login";
		}
		return "re_login";
	}

	public void setBean(User bean) {
		this.bean = bean;
	}

	public Map<String, Object> getQueryJson() {
		return queryJson;
	}

	public void setQueryJson(Map<String, Object> queryJson) {
		this.queryJson = queryJson;
	}

	public User getBean() {
		return bean;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public void setLogincode(String logincode) {
		this.logincode = logincode;
	}

}
