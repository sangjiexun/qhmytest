
package com.fh.util;
import java.security.MessageDigest;

import com.fh.webservice.server.exception.Md5Exception;



public class Md5Util {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String password = "007";
		
		int size = 0;
		while(true){
			if(size>=20){
				break;
			}
			String miPassword = md5(password);
			System.out.println(miPassword);
			
			size++;
		}
//		String miPassword = md5(password);
//		System.out.println(miPassword);
	}
	public static String md5(String str) throws Md5Exception{
		String salt = "HBKEMAN2017";
		str = str+salt;
//		System.out.println("strstrstr"+str);
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Md5Exception("MD5加密出现异常");
		}
		return str;
	}
}
