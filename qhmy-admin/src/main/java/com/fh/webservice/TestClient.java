package com.fh.webservice;

import java.util.HashMap;
import java.util.Map;

import javax.xml.namespace.QName;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.WSConstants;
import org.apache.wss4j.dom.handler.WSHandlerConstants;

import com.fh.webservice.client.impl.DemoClientPasswordCallback;

public class TestClient {
	public static void main(String[] args) throws Exception {  
       /* // 以下和服务端配置类似，不对，应该说服务端和这里的安全验证配置一致  
        Map<String, Object> outProps = new HashMap<String, Object>();  
        outProps.put(WSHandlerConstants.ACTION,  
                WSHandlerConstants.USERNAME_TOKEN);  
        outProps.put(WSHandlerConstants.USER, "admin");  
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);  
        // 指定在调用远程ws之前触发的回调函数WsClinetAuthHandler，其实类似于一个拦截器  
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,  
        		DemoClientPasswordCallback.class.getName());  
        ArrayList list = new ArrayList();  
        // 添加cxf安全验证拦截器，必须  
        list.add(new SAAJOutInterceptor());  
        list.add(new WSS4JOutInterceptor(outProps));  
  
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();  
        // WebServiceSample服务端接口实现类，这里并不是直接把服务端的类copy过来，具体请参考http://learning.iteye.com/blog/1333223  
        factory.setServiceClass(DemoWebserviceImple.class);  
        // 设置ws访问地址  
        factory.setAddress("http://192.168.1.12:8080/colleges-admin/services/demo?wsdl");  
        //注入拦截器，用于加密安全验证信息  
        factory.getOutInterceptors().addAll(list);  
        DemoWebserviceImple service = (DemoWebserviceImple) factory.create();  
        String response = service.saveRequest("2012123234343");  
        System.out.println(response);*/ 
        
        JaxWsDynamicClientFactory clientFactory = JaxWsDynamicClientFactory.newInstance();
		Client client = clientFactory.createClient("http://192.168.1.12:8080/colleges-admin/services/demo?wsdl");
		//Client client = clientFactory.createClient("http://192.168.1.133/eCardWebService/Newcapec.eCard.PlatFormWS.asmx?wsdl");
		Map<String, Object> outProps = new HashMap<String, Object>();  
        outProps.put(WSHandlerConstants.ACTION,WSHandlerConstants.USERNAME_TOKEN);  
        outProps.put(WSHandlerConstants.USER, "admin");  
        outProps.put(WSHandlerConstants.PASSWORD_TYPE, WSConstants.PW_TEXT);  
        // 指定在调用远程ws之前触发的回调函数WsClinetAuthHandler，其实类似于一个拦截器  
        outProps.put(WSHandlerConstants.PW_CALLBACK_CLASS,DemoClientPasswordCallback.class.getName()); 
		client.getOutInterceptors().add(new WSS4JOutInterceptor(outProps));
		QName opName = new QName("http://server.webservice.fh.com/", "saveRequest");  // 指定到接口类所在包
		Object[] result = client.invoke(opName, "2012123234343");
		System.out.println(result[0]);
//		https://www.oschina.net/question/54100_26065
        
    } 
}
