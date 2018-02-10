package com.ctvit.weibo.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类/一期用户加密
 * @author tqc
 *
 */
public class MD5Util {
	/**
	 * md5加密
	 * @param plainText
	 * @return
	 */
	public static String md5(String plainText) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
					buf.append(Integer.toHexString(i));
			}
			String str = buf.toString();
			return str;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();  
		}
		return null;
	}
	
	/**
	 * 一期MD5加密
	 * @param content
	 * @param randomCode
	 * @return
	 */
	public static String encode(String content,String randomCode){
		String result = md5(md5(content)+randomCode);
		return result;
	}
	


	 public static void main(String agrs[]) {
		 String password = "aaaaaaaa";
		 System.out.println(md5("123456"));
		/* for(int i=0;i<21;i++){
			 String num = "000000"+ (int) Math.floor(100*Math.random()) + 1;
			 num = num.substring(num.length()-6);
			 System.out.println(num);
			 
			 System.out.println(encode(password+i,num));
		 }*/
	 }
	

}
