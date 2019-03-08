package com.fh.webservice.server.exception;

/**
 * 转换为json字符串异常
 * @author Administrator
 *
 */
public class TojsonstrException extends RuntimeException {
	
	private static final long serialVersionUID = 5181354614190695320L;

	public TojsonstrException(){
		super();
	}
	
	public TojsonstrException(String name){
		super(name);
	}
	
}
