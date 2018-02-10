package com.ctvit.weibo.exception;


/**
 * 解析xml exception类
 * @author tqc
 *
 */
public class ReadConfigXmlException extends RuntimeException{

	private static final long serialVersionUID = 275920064568767892L;

	public ReadConfigXmlException() {
		super();
	}

	public ReadConfigXmlException(String message, Throwable cause) {
		super(message, cause);
	}

	public ReadConfigXmlException(String message) {
		super(message);
	}

	public ReadConfigXmlException(Throwable cause) {
		super(cause);
	}

}
