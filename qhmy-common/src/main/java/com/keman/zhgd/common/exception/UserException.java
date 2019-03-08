package com.keman.zhgd.common.exception;

/**
 * 自定义异常
 * @author Administrator
 *
 */
public class UserException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public UserException(){
		super();
	}
	
	public UserException(String name){
		super(name);
	}
}
