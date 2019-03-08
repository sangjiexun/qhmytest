package com.fh.webservice.server.exception;

public class Md5Exception extends RuntimeException {
	
	private static final long serialVersionUID = 2696771959164356385L;

	public Md5Exception(){
		super();
	}
	
	public Md5Exception(String name){
		super(name);
	}
	
}
