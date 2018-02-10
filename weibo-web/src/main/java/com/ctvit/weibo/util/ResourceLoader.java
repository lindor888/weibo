package com.ctvit.weibo.util;

import java.io.IOException;
import java.util.Properties;


public class ResourceLoader {
	private static ResourceLoader instance=null;
	Properties config=new Properties();
	private ResourceLoader(){
        try {
        	config.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties"));
		} catch (IOException e) {
			 System.out.println("加载conf.properties失败.");
			e.printStackTrace();
		}
	}
	public static synchronized ResourceLoader getInstance() {
		 if(instance == null){
	            instance = new ResourceLoader();
	            System.out.println("===初始化conf.properties======");
	        }   
	     return instance;   
	}
	public Properties getConfig(){
		return config;
	}
	public static void main(String[] args) {
		System.out.println(ResourceLoader.getInstance().getConfig().getProperty("client_ID"));
	}
}
