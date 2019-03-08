package com.fh.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

public class ImgUtils {
    /** 
     * 截取图片 
     *  
     * @param srcImageFilePath 
     *            原图片地址 
     * @param x 
     *            截取时的x坐标 
     * @param y 
     *            截取时的y坐标 
     * @param desWidth 
     *            截取的宽度 
     * @param desHeight 
     *            截取的高度 
     */  
    public static void imgCrop(String srcImageFilePath, int x, int y,  
            int desWidth, int desHeight) {  
        try {  
            Image img;  
            ImageFilter cropFilter;  
            BufferedImage bi = ImageIO.read(new File(srcImageFilePath));  
            int srcWidth = bi.getWidth();  
            int srcHeight = bi.getHeight();  
            if (srcWidth >= desWidth && srcHeight >= desHeight) {  
                Image image = bi.getScaledInstance(srcWidth, srcHeight,  
                        Image.SCALE_DEFAULT);  
                cropFilter = new CropImageFilter(x, y, desWidth, desHeight);  
                img = Toolkit.getDefaultToolkit().createImage(  
                        new FilteredImageSource(image.getSource(), cropFilter));  
                BufferedImage tag = new BufferedImage(desWidth, desHeight,  
                        BufferedImage.TYPE_INT_RGB);  
                Graphics g = tag.getGraphics();  
                g.drawImage(img, 0, 0, null);  
                g.dispose();  
                // 输出文件  
                ImageIO.write(tag, "JPEG", new File(srcImageFilePath));  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
    /**  
     * 对图片裁剪，并把裁剪新图片保存  
     * @param srcPath 读取源图片路径 
     * @param toPath    写入图片路径 
     * @param x 剪切起始点x坐标 
     * @param y 剪切起始点y坐标 
     * @param width 剪切宽度 
     * @param height     剪切高度 
     * @param readImageFormat  读取图片格式 
     * @param writeImageFormat 写入图片格式 
     * @throws IOException 
     */  
    public static void cropImage(String srcPath,String toPath,  
            int x,int y,int width,int height,  
            String readImageFormat,String writeImageFormat) throws Exception{     
        FileInputStream fis = null ;  
        ImageInputStream iis =null ;  
        try{     
            //读取图片文件   
            fis = new FileInputStream(srcPath);   
            Iterator it = ImageIO.getImageReadersByFormatName(readImageFormat);   
            ImageReader reader = (ImageReader) it.next();   
            //获取图片流    
            iis = ImageIO.createImageInputStream(fis);    
            reader.setInput(iis,true) ;  
            ImageReadParam param = reader.getDefaultReadParam();   
            //定义一个矩形   
            Rectangle rect = new Rectangle(x, y, width, height);   
            //提供一个 BufferedImage，将其用作解码像素数据的目标。    
            param.setSourceRegion(rect);  
            BufferedImage bi = reader.read(0,param);                  
            //保存新图片    
            ImageIO.write(bi, writeImageFormat, new File(toPath));       
        }finally{  
            if(fis!=null)  
                fis.close();         
            if(iis!=null)  
               iis.close();   
        }   
    }   
    /** 
     * 分析图片的尺寸比例 
     * 2015年10月19日 下午2:22:34 
     *  
     * @param width 
     * @param height 
     * @param divWidth 
     * @param divHeight 
     * @return 
     */  
    public static String imgScale(int width, int height, int divWidth,  
            int divHeight) {  
        Double scale = 1.0;  
        if (divWidth >= width && divHeight >= height) {  
            scale = 1.0;  
        }  
      
        if (width >= height && width >= divWidth) {  
            scale = (double) width / divWidth;  
        }  
      
        if (height > width && height >= divHeight) {  
            scale = (double) height / divHeight;  
        }  
      
        return String.valueOf(scale);  
    } 
    /***
     * 删除图片根据路径
     * */
    public static void deleteFiles(String path){
        File file = new File(path);
        //1级文件刪除
        if(!file.isDirectory()){
            file.delete();
        }else if(file.isDirectory()){
            //2級文件列表
            String []filelist = file.list();
            //获取新的二級路径
            for(int j=0;j<filelist.length;j++){
                File filessFile= new File(path+"\\"+filelist[j]);
                if(!filessFile.isDirectory()){
                    filessFile.delete();
                }else if(filessFile.isDirectory()){
                    //递归调用
                    deleteFiles(path+"\\"+filelist[j]);
                }
            }
            file.delete();
        }
    }
}
