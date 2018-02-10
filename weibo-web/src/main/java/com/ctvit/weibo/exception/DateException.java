package com.ctvit.weibo.exception;

import org.apache.log4j.Logger;


/**
 * 日期转化exception类
 * @author tqc
 *
 */
public class DateException extends RuntimeException {

	
	private static final long serialVersionUID = -4812550624421627955L;
	private static Logger log = Logger.getLogger(DateException.class);
	public DateException() {
		super();
	}

	public DateException(String message, Throwable cause) {
		super(message, cause);
	}

	public DateException(String message) {
		super(message);
	}

	public DateException(Throwable cause) {
		log.error("date type transformer error");
	}

}
