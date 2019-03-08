package com.fh.util;

import java.io.File;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Zip;
import org.apache.tools.ant.types.FileSet;

public class ZipCompressorByAnt {
    /** 
     * 执行压缩操作 
     * @param srcPathName 需要被压缩的文件/文件夹 
     */  
    public static boolean compressExe(String srcPathName,String finalFile) {    
        File srcdir = new File(srcPathName);    
        if (!srcdir.exists()){  
            throw new RuntimeException(srcPathName + "不存在！");
        }   
        Project prj = new Project();    
        Zip zip = new Zip();    
        zip.setProject(prj); 
        File zipFile = new File(finalFile);  
        zip.setDestFile(zipFile);    
        FileSet fileSet = new FileSet();    
        fileSet.setProject(prj);    
        fileSet.setDir(srcdir);    
        zip.addFileset(fileSet);    
        zip.execute();
        return true;
    } 
    public static void main(String[] args) {
    	System.out.println(compressExe("E:\\gr","E:\\高冲.zip"));
	}
   /* public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("一鸣惊人");
        list.add("人山人海");
        list.add("海阔天空");
        list.add("空前绝后");
        list.add("后来居上");
        Comparator<Object> cmp = Collator.getInstance(java.util.Locale.CHINA);
        Collections.sort(list, cmp);
        for (String str : list) {
            System.out.println(str);
        }
    }*/
}
