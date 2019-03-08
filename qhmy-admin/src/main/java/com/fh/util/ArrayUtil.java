package com.fh.util;


/**
 * 
 * <p>标题:ArrayUtil</p>
 * <p>描述:</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年7月6日 上午9:52:00
 */
public class ArrayUtil {
	
	/**
	 * <p>描述:数组合并</p>
	 * @author Administrator 鲁震
	 * @date 2017年7月6日 上午9:52:06
	 * @param byte_1
	 * @param byte_2
	 * @return
	 */
	public static byte[] byteMerger(byte[] byte_1, byte[] byte_2){  
        byte[] byte_3 = new byte[byte_1.length+byte_2.length];  
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);//将第一个字节数组拷贝
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);//拷贝第二个字节数组
        return byte_3;  
    }
	
	
	/**
	 * 
	 * <p>描述:将临时byte数组拷贝到目标数组</p>
	 * @author Administrator 鲁震
	 * @date 2017年7月6日 上午9:57:41
	 * @param targetByte
	 * @param tempByte
	 * @return
	 */
	public static byte[] byteTargetByte(byte[] targetByte, byte[] tempByte){  
//		
//		01.public static void arraycopy(Object src,       
//				02.                             int srcPos,       
//				03.                             Object dest,       
//				04.                             int destPos,       
//				05.                             int length) 
        byte[] rst = new byte[targetByte.length+tempByte.length];
        System.arraycopy(targetByte, 0, rst, 0, targetByte.length);//将第一个字节数组拷贝
        System.arraycopy(tempByte, 0, rst, targetByte.length, tempByte.length);//拷贝第二个字节数组
        return rst;
    }
	
	
	/**
	 * <p>描述:将临时byte数组拷贝到目标数组</p>
	 * @author Administrator 鲁震
	 * @date 2017年7月6日 上午11:09:11
	 * @param targetByte
	 * @param tempByte
	 * @param tempByteSize tempByte虽然很大，但是有效数组长度是tempByTeSize
	 * @return
	 */
	public static byte[] byteTargetByte(byte[] targetByte, byte[] tempByte,int tempByteSize){  
//		
//		01.public static void arraycopy(Object src,       
//				02.                             int srcPos,       
//				03.                             Object dest,       
//				04.                             int destPos,       
//				05.                             int length) 
        byte[] rst = new byte[targetByte.length+tempByteSize];
        System.arraycopy(targetByte, 0, rst, 0, targetByte.length);//将第一个字节数组拷贝
        System.arraycopy(tempByte, 0, rst, targetByte.length, tempByteSize);//拷贝第二个字节数组
        return rst;
    }
	
	
	
	
	
	public static void main(String[] args) {
		byte [] a = new byte[0];
		byte [] b = {6,7,8};
		
		byte [] rst = byteTargetByte(a, b);
		
		for (int i = 0; i < rst.length; i++) {
			System.out.println(rst[i]);
		}
		
	}
	
	
}
