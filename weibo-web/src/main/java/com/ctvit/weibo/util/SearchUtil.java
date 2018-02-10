package com.ctvit.weibo.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.lang3.StringUtils;

import com.ctvit.weibo.entity.SearchBean;
import com.ctvit.weibo.entity.SentWeibo;


public class SearchUtil {
	
	private static String searchUrl;
	
	
	static{
		searchUrl = ResourceLoader.getInstance().getConfig().getProperty("searchUrl");
	}
	
	/**
	 * 通过搜索提供的接口查询所需内容
	 * @return
	 * @throws Exception
	 */
	public static Map<String,Object> search(SentWeibo bean) throws Exception {
		
		StringBuffer url = new StringBuffer();
		url.append(searchUrl).append("/");
		if(StringUtils.isNotBlank(bean.getCoreName())) {
			url.append(bean.getCoreName());
		} else {
			url.append("article");//默认查询正文的
		}
		url.append("/").append("select");
		
		StringBuffer parm = new StringBuffer();//将q=后边的参数进行encode
		if(StringUtils.isNotBlank(bean.getQ())) {
			url.append("?q=");
			parm.append(bean.getQ());
		} else {
			url.append("?q=*");
		}
		parm.append(" AND currentdate:[").append(dateToStr(bean.getBeginTime()));
		
		Date endTime = bean.getEndTime();
		endTime = TimeUtil.getCountDay(endTime, 1);//搜索按时间查询时，不包括结束时间，所以要+1天
		parm.append(" TO ").append(dateToStr(endTime)).append("]");
		url.append(URLEncoder.encode(parm.toString(),"UTF-8"));
		
		if(StringUtils.isNotBlank(bean.getWt())) {
			url.append("&wt=").append(bean.getWt());
		}
		/*if(bean.getStart() != null && bean.getStart() != 0) {
			url.append("&start=").append(bean.getStart());
		}
		if(bean.getRows() != null && bean.getRows() != 0) {
			url.append("&rows=").append(bean.getRows());
		}*/
		if(StringUtils.isNotBlank(bean.getFq())) {
			url.append("&fq=").append(bean.getFq());
		}
		if(StringUtils.isNotBlank(bean.getSort())) {
			url.append("&sort=").append(bean.getSort());
		}
		if(StringUtils.isNotBlank(bean.getShards())) {
			url.append("&stards=").append(bean.getShards());
		}
		
		String str = readInterfaceData(url.toString());
		return ParseXmlUtil.getXmlContent(str);
	}
	
	private static String dateToStr(Date date) {
		if(date == null) return "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	
	/**
	 * 读取接口方法
	 * @param url
	 * @return
	 */
	private static String readInterfaceData(String url) {
		BufferedReader br = null;
		try {
			URL weburl = new URL(url);
			URLConnection connection = weburl.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(true);//表示向服务写数据
			connection.setUseCaches(false);
			InputStream in = connection.getInputStream();
			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			StringBuffer sb = new StringBuffer();
			for (String str = br.readLine(); str != null; str = br.readLine()) {
				sb.append(str);
			}
			if (sb.toString() != null && sb.toString().length() > 0) {
				return sb.toString();
			}
			return "error";
		} catch (Exception e) {
			return "error";
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public static void main(String[] args) {
		SearchUtil s = new SearchUtil();
		SearchBean bean = new SearchBean();
		bean.setCoreName("article");
		try {
			//s.search(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
