package com.fh.webservice.server.impl;

import javax.annotation.Resource;
import javax.jws.WebService;

import com.fh.dao.DaoSupport;
import com.fh.util.PageData;
import com.fh.util.UploadWS;
import com.fh.webservice.server.DemoWebservice;
import com.keman.zhgd.common.util.PathUtil;

//{http://server.webservice.data.zghd.keman.com/}queryUser

//Creating Service {http://client.webservice.data.zghd.keman.com/}DemoWebserviceService from class com.keman.zghd.data.webservice.client.DemoWebservice
	
//Creating Service {http://impl.server.webservice.data.zghd.keman.com/}DemoWebserviceImpleService from class com.keman.zghd.data.webservice.server.DemoWebservice
@WebService(endpointInterface = "com.fh.webservice.server.DemoWebservice")
public class DemoWebserviceImple implements DemoWebservice {
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	
	/**
	 * webservice 接收手机上传头像并保存到本地和数据库
	 */
	@Override
	public String saveRequest(String requestData) {
		PageData pd = new PageData();
		String SHENFENZHENGHAO = "";//学生身份证号
		String base64Img = "";//BASE64学生图片
		String rsg = "";//存储照片返回结果
		String[] data = null;
		if(!"".equals(requestData)&& requestData!=null){
			data = requestData.split(":");
		}else{
			return "error";
		}
		if(data.length>1){
			SHENFENZHENGHAO = data[0];
			base64Img = data[1];
			UploadWS us = new UploadWS();
			String imgName = SHENFENZHENGHAO+".jpeg";
			rsg = us.updateImage(imgName, base64Img);
			if("false".equals(rsg)){
				return "error";
			}
			pd.put("SHENFENZHENGHAO", SHENFENZHENGHAO);
			pd.put("TOUXIANG", "uploadFiles/tx/"+imgName);
			try {
				dao.update("UserMapper.updateTXImg", pd);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			return "error";
		}
		return "success";
	}
}
