package com.ctvit.weibo.util;

/**
 * 主键生成器
 * @author Administrator
 *
 */
public class KeyUtil {
	private static int randomNum = 1000;
	public static String getKey(Object pojo){
		StringBuffer sb = new StringBuffer();
		
		//取类名前4位作为前缀
		if(pojo != null) {
			String calssName = pojo.getClass().getSimpleName();
			int len = calssName.length();
			String preKey = len<5?calssName:calssName.substring(0, 4);
			sb.append(preKey);
			if(len<4){
				int n = 4 - len;
				for(int i=0;i<n;i++){
					sb.append("-");
				}
			}
		} else {
			String s = "keys";
			sb.append(s);
		}
		//取当前时间毫秒数+递增序列数作为值
		synchronized(KeyUtil.class){
			randomNum = randomNum==1999?1001:randomNum+1;
			sb.append(System.currentTimeMillis())
			  .append(Integer.toString(randomNum).substring(1));
		}
		
		return sb.toString();
	}
}
