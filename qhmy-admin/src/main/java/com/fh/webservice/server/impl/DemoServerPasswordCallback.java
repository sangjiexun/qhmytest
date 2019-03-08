package com.fh.webservice.server.impl;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.wss4j.common.ext.WSPasswordCallback;
public class DemoServerPasswordCallback implements CallbackHandler {

	@Override
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pc = (WSPasswordCallback) callbacks[i];
			String identifier = pc.getIdentifier();
			System.out.println("身份是："+identifier);
			int usage = pc.getUsage();
			if (usage == WSPasswordCallback.USERNAME_TOKEN) {// 密钥方式USERNAME_TOKEN
				pc.setPassword("testPassword");// ▲【这里非常重要】▲
			} else if (usage == WSPasswordCallback.SIGNATURE) {// 密钥方式SIGNATURE
				pc.setPassword("testPassword");// //▲【这里非常重要】▲
			}
		}
	}

}
