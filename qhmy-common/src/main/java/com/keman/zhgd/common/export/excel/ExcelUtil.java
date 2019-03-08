package com.keman.zhgd.common.export.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WriteException;

import org.springframework.util.CollectionUtils;

import com.keman.zhgd.common.DictKey;
import com.keman.zhgd.common.util.FileUtil;
import com.keman.zhgd.common.util.ZipUtil;

/**
 * 导出excel的通用工具
 * 支持生成复杂表头
 * @author Administrator
 *
 */
public class ExcelUtil {
	/**
	 * 导出excel文件下载
	 * @param request
	 * @param response
	 * @param fileName	文件名
	 * @param headerNames 表头
	 * @param bodyNames list每条记录的key值
	 * @param list 其中放map对象
	 * @param tableHeardSize 表头行数
	 * @return
	 * @throws Exception
	 */
	public static Boolean exportExcelToDownload(HttpServletRequest request,HttpServletResponse response,String fileName,List<Label> headerNamesList,String[][] bodyNames,List list,int tableHeardSize) {
		if (fileName == null) {
			fileName = "temp";
		}
		
		if(fileName==null || headerNamesList == null || headerNamesList.size()<1){
			return false;
		}
		if(!CollectionUtils.isEmpty(list)&&list.size()>DictKey.EXPORTZIPKEY){
			return exportExcelToDownloadByZip(request, response, fileName, headerNamesList, bodyNames,null, list, tableHeardSize);
		}else{
			fileName = fileName+".xls";	//2003-excel格式
			jxl.write.WritableWorkbook wwb = null;
			
			response.reset();// 清空输出流
			response.setCharacterEncoding("utf-8");  
	        response.setContentType("multipart/form-data");
	        try {
	        	//设定输出文件头
				response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));
				response.setContentType("application/x-msdownload;charset=utf-8");// 定义输出类型
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				return false;
			}
	        
	        try {
	        	ServletOutputStream out = response.getOutputStream();
	        	
				wwb = Workbook.createWorkbook(out);
				jxl.write.WritableSheet ws = wwb.createSheet(fileName, 0);
				
				//序号列
//				WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true); 
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
				jxl.write.Label xuhaoLabel = new jxl.write.Label(0, 0, "序号",wcf);
				wcf.setAlignment(Alignment.CENTRE);
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
				

				ws.mergeCells(0,0,0,tableHeardSize-1);//合并单元格，参数格式（开始列，开始行，结束列，结束行）
				ws.addCell(xuhaoLabel);
				

				/*
				 * 生成表头
				 */
				ExcelTablueHeardCount excelTablueHeardCount = new ExcelTablueHeardCount();//记录已使用列数
				excelTablueHeardCount.setLieshu(excelTablueHeardCount.getLieshu()+1);//序号已经使用了一列
				for (int i = 0; i < headerNamesList.size(); i++) {
					createHeader(headerNamesList,null,headerNamesList.get(i),ws,tableHeardSize,excelTablueHeardCount,wcf);
				}			
				
				//数据内容
				if(list!=null){
						for (int i = 0; i < list.size(); i++) {
							//序号
							jxl.write.Label label0 = new jxl.write.Label(0, i+tableHeardSize, i+1+"",wcf);//列号,行号
							ws.addCell(label0);
							Map map =  (Map)list.get(i);
							for (int j = 0,k=1; j < bodyNames.length; j++) {
							 	Object object = map.get(bodyNames[j][0]);
							 	if (bodyNames[j].length>1) {
							 		ws.setColumnView(j+1,Integer.parseInt(bodyNames[j][1]));//设置列宽
								}else {
									ws.setColumnView(j+1,30);//设置列宽
								}
							 	String val = "";
							 	if (object instanceof Timestamp) {
							 		Timestamp timestamp = (Timestamp)object;
							 		Date date = new Date();
							 		date.setTime(timestamp.getTime());
									java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
									val = simpleDateFormat.format(date);
								}else if (object instanceof Date) {
									java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
									val = simpleDateFormat.format(object);
								}else {
									val = String.valueOf(map.get(bodyNames[j][0]));
								}
							 	if (val == null || "null".equals(val)) {
									val = "";
								}
								jxl.write.Label label1 = new jxl.write.Label(k++, i+tableHeardSize, val,wcf);
								ws.addCell(label1);
							}
					}
				}
				wwb.write();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}finally{
				try {
					wwb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
	}
	

	
	
	
	/**
	 * 导出excel文件下载
	 * @param request
	 * @param response
	 * @param fileName	文件名
	 * @param headerNames 表头
	 * @param bodyNames list每条记录的key值
	 * @param list 其中放map对象
	 * @param tableHeardSize 表头行数
	 * @return
	 * @throws Exception
	 */
	public static Boolean exportExcelToDownload(HttpServletRequest request,HttpServletResponse response,String fileName,List<Label> headerNamesList,String[][] bodyNames,List<Map<String,Object>> ItemLx,List list,int tableHeardSize) {
		if (fileName == null) {
			fileName = "temp";
		}
		
		if(fileName==null || headerNamesList == null || headerNamesList.size()<1){
			return false;
		}
		/*if(CollectionUtils.isEmpty(list)){
			return false;
		}*/
		if(!CollectionUtils.isEmpty(list)&&list.size()>DictKey.EXPORTZIPKEY){
			return exportExcelToDownloadByZip(request, response, fileName, headerNamesList, bodyNames,ItemLx, list, tableHeardSize);
		}else{
			fileName = fileName+".xls";	//2003-excel格式
			jxl.write.WritableWorkbook wwb = null;
			
			response.reset();// 清空输出流
			response.setCharacterEncoding("utf-8");  
	        response.setContentType("multipart/form-data");
	        try {
	        	//设定输出文件头
				response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(fileName, "UTF-8"));
				response.setContentType("application/x-msdownload;charset=utf-8");// 定义输出类型
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
				return false;
			}
	        
	        try {
	        	ServletOutputStream out = response.getOutputStream();
				wwb = Workbook.createWorkbook(out);
				jxl.write.WritableSheet ws = wwb.createSheet(fileName, 0);
				
				//序号列
//				WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true); 
				WritableCellFormat wcf = new WritableCellFormat();
				wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
				jxl.write.Label xuhaoLabel = new jxl.write.Label(0, 0, "序号",wcf);
				wcf.setAlignment(Alignment.CENTRE);
				wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
				

				ws.mergeCells(0,0,0,tableHeardSize-1);//合并单元格，参数格式（开始列，开始行，结束列，结束行）
				ws.addCell(xuhaoLabel);
				

				/*
				 * 生成表头
				 */
				ExcelTablueHeardCount excelTablueHeardCount = new ExcelTablueHeardCount();//记录已使用列数
				excelTablueHeardCount.setLieshu(excelTablueHeardCount.getLieshu()+1);//序号已经使用了一列
				for (int i = 0; i < headerNamesList.size(); i++) {
					createHeader(headerNamesList,null,headerNamesList.get(i),ws,tableHeardSize,excelTablueHeardCount,wcf);
				}			
				
				//数据内容
				if(list!=null){
						for (int i = 0; i < list.size(); i++) {
							//序号
							jxl.write.Label label0 = new jxl.write.Label(0, i+tableHeardSize, i+1+"",wcf);//列号,行号
							ws.addCell(label0);
							Map map =  (Map)list.get(i);
							for (int j = 0,k=1; j < bodyNames.length; j++) {
							 	Object object = map.get(bodyNames[j][0]);
							 	if (bodyNames[j].length>1) {
							 		ws.setColumnView(j+1,Integer.parseInt(bodyNames[j][1]));//设置列宽
								}else {
									ws.setColumnView(j+1,30);//设置列宽
								}
							 	String val = "";
							 	if (object instanceof Timestamp) {
							 		Timestamp timestamp = (Timestamp)object;
							 		Date date = new Date();
							 		date.setTime(timestamp.getTime());
									java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
									val = simpleDateFormat.format(date);
								}else if (object instanceof Date) {
									java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
									val = simpleDateFormat.format(object);
								}else {
									val = String.valueOf(map.get(bodyNames[j][0]));
								}
							 	if (val == null || "null".equals(val)) {
									val = "";
								}
							 	if(ItemLx.get(j)!=null){
							 		val=DictKey.getDictKeyMC(val,ItemLx.get(j));
							 	}
								jxl.write.Label label1 = new jxl.write.Label(k++, i+tableHeardSize, val,wcf);
								ws.addCell(label1);
							}
					}
				}
				wwb.write();
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}finally{
				try {
					wwb.close();
				} catch (WriteException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return true;
		}
		
	}
	public static Boolean exportExcel(String fileName,List<Label> headerNamesList,String[][] bodyNames,List list,int tableHeardSize) {
		if (fileName == null) {
			fileName = "temp";
		}
		
		if(fileName==null || headerNamesList == null || headerNamesList.size()<1){
			return false;
		}
		
		
		fileName = fileName+".xls";	//2003-excel格式
		
		jxl.write.WritableWorkbook wwb = null;
		
		FileOutputStream fileOutputStream = null;
		
        try {
        	File file = new File("c:\\"+fileName);
        	if (!file.exists()) {
				file.createNewFile();
			}
        	fileOutputStream = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(fileOutputStream);
			jxl.write.WritableSheet ws = wwb.createSheet(fileName, 0);
			
			//序号列
//			WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true); 
			WritableCellFormat wcf = new WritableCellFormat();
			wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			jxl.write.Label xuhaoLabel = new jxl.write.Label(0, 0, "序号",wcf);
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			
			
			ws.mergeCells(0,0,0,tableHeardSize-1);//合并单元格，参数格式（开始列，开始行，结束列，结束行）
			ws.addCell(xuhaoLabel);
			

			/*
			 * 生成表头
			 */
			ExcelTablueHeardCount excelTablueHeardCount = new ExcelTablueHeardCount();//记录已使用列数
			excelTablueHeardCount.setLieshu(excelTablueHeardCount.getLieshu()+1);//序号已经使用了一列
			for (int i = 0; i < headerNamesList.size(); i++) {
				createHeader(headerNamesList,null,headerNamesList.get(i),ws,tableHeardSize,excelTablueHeardCount,wcf);
			}			
			
			
			//数据内容
			if(list!=null){
					for (int i = 0; i < list.size(); i++) {
						//序号
						jxl.write.Label label0 = new jxl.write.Label(0, i+1, i+1+"");
						ws.addCell(label0);
						Map map =  (Map)list.get(i);
						for (int j = 0,k=1; j < bodyNames.length; j++) {
						 	Object object = map.get(bodyNames[j][0]);
						 	if (bodyNames[j].length>1) {
						 		ws.setColumnView(i,Integer.parseInt(bodyNames[j][1]));//设置列宽
							}else {
								ws.setColumnView(i,30);//设置列宽
							}
						 	String val = "";
						 	if (object instanceof Timestamp) {
						 		Timestamp timestamp = (Timestamp)object;
						 		Date date = new Date();
						 		date.setTime(timestamp.getTime());
								java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								val = simpleDateFormat.format(date);
							}else if (object instanceof Date) {
								java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								val = simpleDateFormat.format(object);
							}else {
								val = String.valueOf(map.get(bodyNames[j][0]));
							}
						 	if (val == null || "null".equals(val)) {
								val = "";
							}
							jxl.write.Label label1 = new jxl.write.Label(k++, i+1, val);
							
							ws.addCell(label1);
						}
				}
			}
			wwb.write();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				wwb.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	@SuppressWarnings("rawtypes")
	public static Boolean exportExceltoPath(String path,String fileName,List<Label> headerNamesList,String[][] bodyNames,List<Map<String,Object>> ItemLx,List list,int tableHeardSize,int starxh) {
		if (fileName == null) {
			fileName = "temp";
		}
		
		if(fileName==null || headerNamesList == null || headerNamesList.size()<1){
			return false;
		}
		
		
		fileName = fileName+".xls";	//2003-excel格式
		
		jxl.write.WritableWorkbook wwb = null;
		FileOutputStream fileOutputStream = null;
		
        try {
        	File file = new File(path+fileName);
        	if (!file.exists()) {
				file.createNewFile();
			}
        	fileOutputStream = new FileOutputStream(file);
			wwb = Workbook.createWorkbook(fileOutputStream);
			jxl.write.WritableSheet ws = wwb.createSheet(fileName, 0);
			
			//序号列
//			WritableFont wf = new WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true); 
			WritableCellFormat wcf = new WritableCellFormat();
			wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);
			jxl.write.Label xuhaoLabel = new jxl.write.Label(0, 0, "序号",wcf);
			wcf.setAlignment(Alignment.CENTRE);
			wcf.setVerticalAlignment(VerticalAlignment.CENTRE);
			
			
			
			ws.mergeCells(0,0,0,tableHeardSize-1);//合并单元格，参数格式（开始列，开始行，结束列，结束行）
			ws.addCell(xuhaoLabel);
			/*
			 * 生成表头
			 */
			ExcelTablueHeardCount excelTablueHeardCount = new ExcelTablueHeardCount();//记录已使用列数
			excelTablueHeardCount.setLieshu(excelTablueHeardCount.getLieshu()+1);//序号已经使用了一列
			for (int i = 0; i < headerNamesList.size(); i++) {
				createHeader(headerNamesList,null,headerNamesList.get(i),ws,tableHeardSize,excelTablueHeardCount,wcf);
			}	
			
			boolean change=!CollectionUtils.isEmpty(ItemLx);
			//数据内容
			if(list!=null){
					for (int i = 0; i < list.size(); i++) {
						//序号
						jxl.write.Label label0 = new jxl.write.Label(0, i+tableHeardSize, starxh+"",wcf);
						ws.addCell(label0);
						ws.setColumnView(0,10);//设置列宽
						Map map =  (Map)list.get(i);
						for (int j = 0,k=1; j < bodyNames.length; j++) {
						 	Object object = map.get(bodyNames[j][0]);
						 	if (bodyNames[j].length>1) {
						 		ws.setColumnView(i,Integer.parseInt(bodyNames[j][1]));//设置列宽
							}else {
								ws.setColumnView(i,30);//设置列宽
							}
						 	String val = "";
						 	if (object instanceof Timestamp) {
						 		Timestamp timestamp = (Timestamp)object;
						 		Date date = new Date();
						 		date.setTime(timestamp.getTime());
								java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								val = simpleDateFormat.format(date);
							}else if (object instanceof Date) {
								java.text.SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
								val = simpleDateFormat.format(object);
							}else {
								val = String.valueOf(map.get(bodyNames[j][0]));
							}
						 	if (val == null || "null".equals(val)) {
								val = "";
							}
						 	if(change&&ItemLx.get(j)!=null){
						 		val=DictKey.getDictKeyMC(val,ItemLx.get(j));
						 	}
							jxl.write.Label label1 = new jxl.write.Label(k++, i+tableHeardSize, val,wcf);
							
							ws.addCell(label1);
						}
						starxh++;
				}
			}
			wwb.write();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				wwb.close();
				fileOutputStream.close();
			} catch (WriteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 生成头部
	 * @param headerNamesList
	 * @param ws
	 */
	private static void createHeader(List<Label> headerList, Label fLabel,Label sLabel,
			WritableSheet ws,int tableHeaderXSize,ExcelTablueHeardCount excelTablueHeardCount,WritableCellFormat wcf) throws Exception{
		List<Label> sonLabelList = sLabel.getSonLabelList();
		ExcelTableHeard excelTableHeard = new ExcelTableHeard();//主要记录列值
		excelTableHeard.setStartL(excelTablueHeardCount.getLieshu());//列数
		//System.out.println(excelTablueHeardCount.getLieshu());
		//System.out.println(sLabel.getLabelName());
		//excelTableHeard.setEndL(sonLabelList.size()+excelTablueHeardCount.getLieshu()-1);//结束列就是自己子label的大小数,3
		
		if (fLabel == null) {
			excelTableHeard.setStartH(0);//开始行,就是第一行
		}else {
			excelTableHeard.setStartH(fLabel.getTableHeardInfo().getStartH()+1);//上一级父对象的行号+1
		}
		
		sLabel.setTableHeardInfo(excelTableHeard);//设置表头信息
		
		if (sonLabelList.size()==0) {
			//没有子对象
			/*
			 * 更新上级的结束列号
			 */
			if (fLabel!=null) {
				fLabel.getTableHeardInfo().setEndL(sLabel.getTableHeardInfo().getStartL());//更新上级的结束列号
			}
			/*
			 * 更新计数器
			 */
			excelTablueHeardCount.setLieshu(excelTablueHeardCount.getLieshu()+1);//每次加1
			
			/*
			 * 添加
			 */
			jxl.write.Label tempLabel = new jxl.write.Label(sLabel.getTableHeardInfo().getStartL(), sLabel.getTableHeardInfo().getStartH(), sLabel.getLabelName(),wcf);//列号,行号
			ws.mergeCells(sLabel.getTableHeardInfo().getStartL(),sLabel.getTableHeardInfo().getStartH(),sLabel.getTableHeardInfo().getStartL(),tableHeaderXSize-1);//合并单元格，参数格式（开始列，开始行，结束列，结束行）
			ws.addCell(tempLabel);
			
		}else {
			/*
			 * 先生成子节点
			 */
			for (int i = 0; i < sonLabelList.size(); i++) {
				createHeader(sonLabelList, sLabel, sonLabelList.get(i), ws, tableHeaderXSize, excelTablueHeardCount,wcf);
			}
			/*
			 * 跨列 new cell即可,不需要跨行。
			 */
			jxl.write.Label tempLabel = new jxl.write.Label(sLabel.getTableHeardInfo().getStartL(), sLabel.getTableHeardInfo().getStartH(), sLabel.getLabelName(),wcf);//列号,行号
			ws.mergeCells(sLabel.getTableHeardInfo().getStartL(),sLabel.getTableHeardInfo().getStartH(),sLabel.getTableHeardInfo().getEndL(),sLabel.getTableHeardInfo().getStartH());//合并单元格，参数格式（开始列，开始行，结束列，结束行）
			ws.addCell(tempLabel);
			
			/*
			 * 更新上级的结束列号
			 */
			if (fLabel!=null) {
				fLabel.getTableHeardInfo().setEndL(sLabel.getTableHeardInfo().getEndL());//更新上级的结束列号
			}
			
		}
	}


	public static void main(String[] args) throws Exception{
		List<Label> headerLabelList = new ArrayList<Label>();
		Label label1 = new Label("税务机关");//第一大列
		Label label2 = new Label("总户数");
		Label label3 = new Label("汇缴情况(户数)").addLabel(new Label("总户数")).addLabel(new Label("未申报")).addLabel(new Label("已申报"));
		Label label4 = new Label("申报情况(户数)").addLabel(new Label("总户数")).addLabel(new Label("未汇缴")).addLabel(new Label("已汇缴"));
		Label label5 = new Label("汇缴申报比对(户数)").addLabel(new Label("总户数")).addLabel(new Label("一致")).addLabel(new Label("不一致"));
		headerLabelList.add(label1);
		headerLabelList.add(label2);
		headerLabelList.add(label3);
		headerLabelList.add(label4);
		headerLabelList.add(label5);
		String[][] bodyNames = new String[][]{{"bmjc"},{"zhs"},{"hjzhs"},{"hjwsbzhs"},{"hjsbzhs"},{"sbzhs"},{"sbwhjzhs"},{"sbhjzhs"},{"bdhs"},{"bdyzhs"},{"byzhs"}};
		
		
		
		/*
		 * 往年结转
		 */
//		Label label1 = new Label("税务机关");//第一大列
//		Label label3 = new Label("往年结账").addLabel(new Label("广告费").
//				addLabel(new Label("户数")).addLabel(new Label("金额"))).
//				addLabel(new Label("尚未弥补亏损").addLabel(new Label("户数")).
//						addLabel(new Label("金额")));
//		headerLabelList.add(label1);
//		headerLabelList.add(label3);
		String path="D:\\dwzmvc";
		ExcelUtil.exportExceltoPath(path, "aa", headerLabelList, bodyNames, null, null, 3, 3);
	}
	
	public static String createTempFile(String path,List list,List<Label> headerLabelList,String[][] bodyNames,List<Map<String,Object>> ItemLx, String name,int tableHeardSize){
		path=path+"downloadtemp\\"+UUID.randomUUID()+"\\";
		File temp = new File(path); //为了保证目录存在,如果没有则新建该目录 
		if (!temp.exists()) {
			temp.mkdirs(); 
		}
		int size=list.size();
		List Templist=null;
		int j=0;
		int page=1;
		for(int i=0;i<size;){
			if(i+DictKey.EXPORTPAGESIZEKEY>size){
				j=size;
			}else{
				j=i+DictKey.EXPORTPAGESIZEKEY;
			}
			Templist=list.subList(i,j);
			ExcelUtil.exportExceltoPath(path,name+"第"+page+"页",headerLabelList,bodyNames,ItemLx, Templist,tableHeardSize,i+1);
			page++;
			i+=DictKey.EXPORTPAGESIZEKEY;
		}
		return path;
	}
	
	public static File getZipFile(String path){
		try {
			return new File(ZipUtil.zip(path,false));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 导出excel文件下载
	 * @param request
	 * @param response
	 * @param fileName	文件名
	 * @param headerNames 表头
	 * @param bodyNames list每条记录的key值
	 * @param list 其中放map对象
	 * @param tableHeardSize 表头行数
	 * @return
	 * @throws Exception
	 */
	public static Boolean exportExcelToDownloadByZip(HttpServletRequest request,HttpServletResponse response,String fileName,List<Label> headerNamesList,String[][] bodyNames,List<Map<String,Object>> ItemLx,List list,int tableHeardSize) {
		response.reset();// 清空输出流
		response.setCharacterEncoding("utf-8");  
        //response.setContentType("multipart/form-data");
        try {
        	//设定输出文件头
			response.setHeader("Content-Disposition", "attachment;fileName="+URLEncoder.encode(fileName+".zip", "UTF-8"));
			response.setContentType("application/zip;charset=utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return false;
		}
        ServletOutputStream out =null;
        try {
        	out = response.getOutputStream();
			String basePath =request.getSession().getServletContext().getRealPath("/");
			String path=createTempFile(basePath, list, headerNamesList, bodyNames,ItemLx, fileName,tableHeardSize);
			File zipFile =getZipFile(path);
			
			//数据内容
			/*
			 * 将Zip文件输出给调用者
			 */
			FileInputStream fileInputStream = new FileInputStream(zipFile);
			byte [] outByte = new byte[1024];
			int len;
			while((len = fileInputStream.read(outByte))!=-1){
				out.write(outByte,0,len);
			}
			out.flush();
			fileInputStream.close();
			FileUtil.deleteFiles(new File(path)); 
			FileUtil.deleteFiles(zipFile); 
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}finally{
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
}
