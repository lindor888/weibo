package com.ctvit.weibo.exception;

import org.apache.log4j.Logger;


/**
 * 数据库exception类
 * @author tqc
 *
 */
public class AccessDBException extends RuntimeException {

	private static final long serialVersionUID = 6332933685409295592L;
	private static Logger log = Logger.getLogger(AccessDBException.class);
	public AccessDBException() {
		super();
	}

	public AccessDBException(String message, Throwable cause) {
		super(message, cause);
	}

	public AccessDBException(String message) {
		super(message);
	}

	public AccessDBException(Throwable cause) {
		log.error("mongodb error");
	}

}
