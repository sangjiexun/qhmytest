package com.fh.webservice.server;

import javax.jws.WebService;

@WebService
public interface DemoWebservice {
	
	
	public String saveRequest(String requestData);
	
}
