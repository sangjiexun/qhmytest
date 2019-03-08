package com.fh.webservice.server.exception;

/**
 * 密文异常  密文不一致异常
 * @author Administrator
 *
 */
public class MiwenException extends RuntimeException {

	private static final long serialVersionUID = -6626537934240411673L;

	public MiwenException(){
		super();
	}
	
	public MiwenException(String name){
		super(name);
	}
	
}
