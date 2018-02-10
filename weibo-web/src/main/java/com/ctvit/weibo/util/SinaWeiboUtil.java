package com.ctvit.weibo.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.protocol.Protocol;

import weibo4j.Account;
import weibo4j.Comments;
import weibo4j.Friendships;
import weibo4j.Oauth;
import weibo4j.Timeline;
import weibo4j.Users;
import weibo4j.http.AccessToken;
import weibo4j.http.ImageItem;
import weibo4j.model.CommentWapper;
import weibo4j.model.MySSLSocketFactory;
import weibo4j.model.Paging;
import weibo4j.model.RateLimitStatus;
import weibo4j.model.Status;
import weibo4j.model.StatusWapper;
import weibo4j.model.User;
import weibo4j.model.UserWapper;
import weibo4j.model.WeiboException;
import weibo4j.org.json.JSONObject;
import weibo4j.util.WeiboConfig;

public class SinaWeiboUtil{
	
	public static int base_app = 0;//是否只获取当前应用的数据。0为否（所有数据），1为是（仅当前应用），默认为0。
	public static int feature = 0;//过滤类型ID，0：全部、1：原创、2：图片、3：视频、4：音乐，默认为0。 
	public static int filter_by_author = 0;//作者筛选类型，0：全部、1：我关注的人、2：陌生人，默认为0。 
	
	
	/**
	 * 获取token
	 * @param clientId
	 * @param clientSecret
	 * @param redirectUri
	 * @param userName
	 * @param password
	 * @return
	 */
	public String getToken(String clientId,String clientSecret,String redirectUri,String userName, String password) {
        String url = WeiboConfig.getValue("authorizeURL");  
  
        Protocol.registerProtocol("https", new Protocol("https", new MySSLSocketFactory(), 443));
        PostMethod postMethod = new PostMethod(url);  
        //应用的App Key  
        postMethod.addParameter("client_id",clientId);  
        //应用的重定向页面  
        postMethod.addParameter("redirect_uri",redirectUri);  
        //模拟登录参数  
        //开发者或测试账号的用户名和密码  
        postMethod.addParameter("userId", userName);  
        postMethod.addParameter("passwd", password);
        postMethod.addParameter("isLoginSina", "0");  
        postMethod.addParameter("action", "submit");  
        postMethod.addParameter("response_type","code");  
        HttpMethodParams param = postMethod.getParams();  
        param.setContentCharset("UTF-8");  
        //添加头信息  
        List<Header> headers = new ArrayList<Header>();  
        headers.add(new Header("Referer", "https://api.weibo.com/oauth2/authorize?client_id="+clientId+"&redirect_uri="+redirectUri+"&from=sina&response_type=code"));  
        headers.add(new Header("Host", "api.weibo.com"));  
        headers.add(new Header("User-Agent","Mozilla/5.0 (Windows NT 6.1; rv:11.0) Gecko/20100101 Firefox/11.0"));  
        HttpClient client = new HttpClient();  
        client.getHostConfiguration().getParams().setParameter("http.default-headers", headers);  
        try {
			client.executeMethod(postMethod);
		} catch (HttpException e1) {
			return null;
		} catch (IOException e1) {
			return null;
		}  
        int status = postMethod.getStatusCode();  
        if (status != 302)  {  
            System.out.println("token刷新失败");  
            return null;  
        }  
        //解析Token  
        Header location = postMethod.getResponseHeader("Location");  
        if (location != null)  {  
            String retUrl = location.getValue();  
            int begin = retUrl.indexOf("code=");  
            if (begin != -1) {  
                int end = retUrl.indexOf("&", begin);  
                if (end == -1)  
                    end = retUrl.length();  
                String code = retUrl.substring(begin + 5, end);  
                if (code != null) {  
                    Oauth oauth = new Oauth();  
                    try{  
                        AccessToken token = oauth.getAccessTokenByCode(code, clientId, clientSecret, redirectUri);
                        return token.getAccessToken();  
                    }catch(Exception e){ 
                    	e.printStackTrace();
                    	return null;
                    }  
                }  
            }  
        }  
        return null;  
	}
	
	/**
	 * 获取次数调用剩余次数以及次数下次刷新时间
	 * @param accessToken
	 * @return
	 * @throws WeiboException
	 */
	public RateLimitStatus getAccountRateLimitStatus(String accessToken) throws WeiboException{
		Account account = new Account();
		account.setToken(accessToken);
		return account.getAccountRateLimitStatus();
	}
	
	
	/**
	 * 根据uid、sinceId获取用户的微博
	 * @param uid
	 * @param accessToken
	 * @param page
	 * @param count
	 * @param sinceId
	 * @return
	 * @throws WeiboException
	 */
	public StatusWapper getUserTimelineByUid(String uid,String accessToken,int page,int count,long sinceId) throws WeiboException {
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setCount(count);
        if(sinceId>0){
        	paging.setSinceId(sinceId);
        }
        return timeline.getUserTimelineByUid(uid, paging, base_app, feature);
	}
	
	/**
	 * 根据微博id获取评论
	 * @param weiboId
	 * @param accessToken
	 * @param page
	 * @param count
	 * @return
	 * @throws WeiboException
	 */
	public CommentWapper getCommentById(String weiboId,String accessToken,int page,int count) throws WeiboException{
		Comments comments = new Comments();
		comments.client.setToken(accessToken);
		Paging paging = new Paging();
        paging.setPage(page);
        paging.setCount(count);
        return comments.getCommentById(weiboId, paging, filter_by_author);
	}
	
	/**
	 * 根据微博id获取转发
	 * @param weiboId
	 * @param accessToken
	 * @param page
	 * @param count
	 * @return
	 * @throws WeiboException
	 */
	public StatusWapper getRepostTimeline(String weiboId,String accessToken,int page,int count) throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setCount(count);
        return timeline.getRepostTimeline(weiboId, paging);
	}
	
	/**
	 * 根据uid获取用户信息
	 * @param uid
	 * @param accessToken
	 * @return
	 * @throws WeiboException
	 */
	public User showUserById(String uid,String accessToken) throws WeiboException{
		Users users = new Users();
		users.client.setToken(accessToken);
		return users.showUserById(uid);
	}
	
	
	/**
	 * 根据uid和游标cursor获取用户关注信息
	 * @param uid
	 * @param accessToken
	 * @param cursor
	 * @param count
	 * @return
	 * @throws WeiboException
	 */
	public UserWapper getFriendsByID(String uid,String accessToken,int cursor,int count) throws WeiboException{
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken);
		return friendships.getFriendsByID(uid, count, cursor);
	}
	
	/**
	 * 根据uid和游标cursor获取用户粉丝信息
	 * @param uid
	 * @param accessToken
	 * @param cursor
	 * @param count
	 * @return
	 * @throws WeiboException
	 */
	public UserWapper getFollowersById(String uid,String accessToken,int cursor,int count) throws WeiboException{
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken);
		return friendships.getFollowersById(uid, count, cursor);
	}
	
	
	/**
	 * 获取我关注的微博的微博内容
	 * @param uid
	 * @param accessToken
	 * @param page
	 * @param count
	 * @return
	 * @throws WeiboException
	 */
	public StatusWapper getHomeTimeline(String accessToken,int page,int count)throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setCount(count);
        return timeline.getHomeTimeline(base_app, feature, paging);
	}
	
	/**
	 * 获取搜索内容
	 * @param accessToken
	 * @param page
	 * @param count
	 * @param q
	 * @param startTime
	 * @param endTime
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws WeiboException
	 */
	public StatusWapper getSearchLimited(String accessToken,int page,int count,String q,int startTime,int endTime) throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
        Paging paging = new Paging();
        paging.setPage(page);
        paging.setCount(count);
        return timeline.getSearchLimited(q, startTime, endTime, paging);
	}
	
	/**
	 * 获取全量粉丝id列表
	 * @param accessToken
	 * @param uid
	 * @param maxTime
	 * @param cursorUid
	 * @return
	 * @throws WeiboException
	 */
	public JSONObject getAllFollowersById(String accessToken,String uid,String maxTime,long cursorUid) throws WeiboException{
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken);
		return friendships.getAllFollowersById(uid, maxTime, cursorUid);
	}
	
	/**
	 * 添加一个关注
	 * @param accessToken
	 * @param uid
	 * @return
	 * @throws WeiboException
	 */
	public User createFriendshipsById(String accessToken,String uid) throws WeiboException{
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken);
		return friendships.createFriendshipsById(uid);
	}
	
	/**
	 * 取消一个关注
	 * @param accessToken
	 * @param uid
	 * @return
	 * @throws WeiboException
	 */
	public User destroyFriendshipsDestroyById(String accessToken,String uid) throws WeiboException{
		Friendships friendships = new Friendships();
		friendships.client.setToken(accessToken);
		return friendships.destroyFriendshipsDestroyById(uid);
	}
	
	
	/**
	 * 发送图片、以及文字到微博(文字小于140个字)
	 * @param accessToken
	 * @param imgUrl
	 * @param content
	 * @return
	 * @throws WeiboException
	 */
	public Status UploadStatus(String accessToken,String imgUrl,String content) throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
        if(imgUrl!=null&&!"".equals(imgUrl)){
        	byte[] data = null;
            try {
    			URL url = new URL(imgUrl);
    			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
    			conn.setRequestMethod("GET");  
    			conn.setConnectTimeout(5 * 1000);
    			InputStream inStream = conn.getInputStream();  
    			data = readInputStream(inStream);
    		} catch (Exception e) {
    			return null;
    		}
            ImageItem pic = new ImageItem("pic", data);
    		try {
    			content = URLEncoder.encode(content, "utf-8");
    		} catch (UnsupportedEncodingException e) {
    			return null;
    		}
            Status status = timeline.UploadStatus(content, pic);
            return status;
        }else{
        	Status status = timeline.UpdateStatus(content);
        	return status;
        }
	}
	
	
	/**
	 * 上传图片、高级权限（待验证）
	 * @param accessToken
	 * @param imgUrl
	 * @return
	 * @throws WeiboException
	 */
	public JSONObject uploadPic(String accessToken,String imgUrl) throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
      
    	byte[] data = null;
        try {
			URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			conn.setRequestMethod("GET");  
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();  
			data = readInputStream(inStream);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (ProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
        ImageItem pic = new ImageItem("pic", data);
        return timeline.uploadPic(pic);
	}

	/**
	 * 删除指定微博
	 * @param id
	 * @return
	 * @throws WeiboException
	 */
	public Status deleteById(String weiboId, String accessToken) throws WeiboException{
		Timeline timeline = new Timeline();  
        timeline.client.setToken(accessToken);
		return timeline.Destroy(weiboId);
	}
	
	
	/**
	 * 转换为byte
	 * @param inStream
	 * @return
	 * @throws Exception
	 */
	private static byte[] readInputStream(InputStream inStream) throws Exception{  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();  
        byte[] buffer = new byte[1024];  
        int len = 0;
        while( (len=inStream.read(buffer)) != -1 ){  
            outStream.write(buffer, 0, len);  
        }
        inStream.close();  
        return outStream.toByteArray();  
    }  
	
	public static void main(String[] args) throws WeiboException {
		SinaWeiboUtil s = new SinaWeiboUtil();
		List<Status> l = s.getUserTimelineByUid("2269483043", "2.00NNWaTC0oXPWt14211f8a0dkJm45E", 1, 10, 0).getStatuses();
		for(Status st : l){
			System.out.println(st.getPicUrls());
		}
	}
}
