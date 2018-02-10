package com.ctvit.weibo.mongodb;




import java.net.UnknownHostException;

import com.ctvit.weibo.exception.AccessDBException;
import com.ctvit.weibo.util.ResourceLoader;
import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * mongodb工具类
 * @author tqc
 *
 */
public class MongodbUtil {
	private static MongodbUtil mongodbUtil = null;
	
	private Mongo masterMongo = null;
	
	//private Mongo slaveMongo = null;
	
	private static String master_url;
	private static int master_port;
	/*private static String slave_url;
	private static int slave_port;*/
	private static String dbName;
	
	
	static{
		master_url = ResourceLoader.getInstance().getConfig().getProperty("master_url");
		master_port = Integer.parseInt(ResourceLoader.getInstance().getConfig().getProperty("master_port"));
		/*slave_url = ResourceLoader.getInstance().getConfig().getProperty("slave_url");
		slave_port = Integer.parseInt(ResourceLoader.getInstance().getConfig().getProperty("slave_port"));*/
		dbName = ResourceLoader.getInstance().getConfig().getProperty("dbName");
	}
	
	public static MongodbUtil getInstance(){
		if(mongodbUtil==null){
			mongodbUtil = new MongodbUtil();
		}
		return mongodbUtil;
	}
	
	private Mongo getMasterMongoInstance(){
		if(masterMongo==null){
			try {
				masterMongo = new Mongo(master_url,master_port);
			} catch (UnknownHostException e) {
				throw new AccessDBException(e);
			}
		}
		return masterMongo;
	}
	
	/*private Mongo getSlaveMongoInstance(){
		if(slaveMongo==null){
			try {
				slaveMongo = new Mongo(slave_url,slave_port);
			} catch (UnknownHostException e) {
				throw new AccessDBException(e);
			}
		}
		return slaveMongo;
	}*/
	
	
	public DB getMasterConnection(){
		DB db = this.getMasterMongoInstance().getDB(dbName);
		return db;
	}
	
	/*public DB getSlaveConnection(){
		DB db = this.getSlaveMongoInstance().getDB(dbName);
		return db;
	}*/
}
