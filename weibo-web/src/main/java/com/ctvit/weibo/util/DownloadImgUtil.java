package com.ctvit.weibo.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DownloadImgUtil {
	/**
	 * 下载图片
	 * @param imgUrl
	 * @return
	 */
	public static String downloadImageUrl(String imgUrl){
		String imgPath = "";
        try {
			URL url = new URL(imgUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();  
			conn.setRequestMethod("GET");  
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();  
			String fileName = "WEIBO" + KeyUtil.getKey(new Object())+".jpg";
			String prePath = getFilePath();
			String filePath = ResourceLoader.getInstance().getConfig().getProperty("imageFile") + prePath + fileName;
			imgPath = ResourceLoader.getInstance().getConfig().getProperty("imageUrl") + prePath + fileName;
			FileOutputStream fos = null;
			File file = new File(filePath);
			if(!file.getParentFile().exists())
			file.getParentFile().mkdirs();
			if(!file.exists()) 
			file.createNewFile();
			fos = new FileOutputStream(filePath); 
			int ch =0 ;
			try{
				while((ch=inStream.read())!=-1){
					fos.write(ch);
				}
			} catch(Exception e){
				e.printStackTrace();
			} finally{
				fos.close();
				inStream.close();
			}
		} catch (Exception e) {
			return "";	
		}
        return imgPath;
	}
	
	private static String getFilePath(){
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String now = sdf.format(new Date());
		String retPath = now.substring(0,4) + "/";
		retPath += now.substring(4, 6) + "/";
		retPath += now.substring(6, 8) + "/";
    	return retPath;
    }
}
