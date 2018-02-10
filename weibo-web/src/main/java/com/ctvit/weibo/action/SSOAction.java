package com.ctvit.weibo.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ctvit.util.SessionHandler;
import com.ctvit.weibo.entity.User;

public class SSOAction extends SessionHandler {
	

	@Override
	public boolean ifHaveSession(HttpServletRequest req) {
		HttpSession session = req.getSession();
		if(session != null) {
			return true;
		}
		return false;
	}

	@Override
	public void processSession(String userId, String userName,
			HttpServletRequest req, HttpServletResponse arg3) {
		
		User user = new User();
		user.setUserId(userId);
		user.setUserName(userName);
		req.getSession().setAttribute(User.SESSION_USER, user);
		
	}
	
}
