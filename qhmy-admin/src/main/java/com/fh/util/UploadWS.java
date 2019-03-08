package com.fh.util;

import java.io.File;
import java.io.FileOutputStream;

import net.iharder.Base64;



public class UploadWS {
	 public  String  updateImage(String filename,String image){  
	        FileOutputStream fos = null;  
	        try{  
	        	
	            String strPath=this.getClass().getClassLoader().getResource("../../uploadFiles/tx/").toURI().getPath();
	        	//String toDir=new File("D:\\images").getPath();
	        	
	          // String x=strPath.substring(1);
	           // String y=x.replaceAll("/", "\\\\\\\\");
	            byte[] buffer = Base64.decode(image);
	            //String strPath = "D:\\scxjimages";
	            File destDir = new File(strPath);
	            //File fileParent = destDir.getParentFile();  
	            if(!destDir.exists()){  
	            	 destDir.mkdirs();  
	            }  
	           
	          // File destDir = new File(toDir);
	           //String dd= destDir.getPath();
	            //if(!destDir.exists())  
	            //destDir.mkdir();  
	              //String xxx="D:\\Program Files\\apache-tomcat-7.0.69\\webapps\\ws_server\\images\\1.jpg";
	            //System.out.println(buffer.length);  
	           File x= new File(destDir,filename);
	           if(!x.exists()){
	        	   x.createNewFile();
	           }
	            fos = new FileOutputStream(x);  
	            fos.write(buffer);  
	            fos.flush();  
	            fos.close();  
	            return destDir.getPath();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        return "false";  
	          
	}  
}
