package com.keman.zhgd.common.maputil;

/**
 * 应用异常:基本异常
 * @author Administrator
 *
 */
public class BaseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	public String exceptionCode;

	public BaseException(){
		super();
	}
	
	public BaseException(String message){
		super(message);
	}
	
}
