package com.fh.webservice.client;

import javax.jws.WebService;

@WebService(targetNamespace = "http://server.webservice.fh.com/")
public interface DemoWebservice {
	
	
	public String saveRequest(String requestData);
	
}
