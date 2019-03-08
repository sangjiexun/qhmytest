package com.fh.webservice.server.returnvo;

import java.util.ArrayList;
import java.util.List;

import com.fh.webservice.server.RstEnum;
import com.keman.zhgd.common.util.PageData;

/**
 * 返回参数vo
 * @author Administrator
 *
 */
public class ReturnVo extends ReturnBaseVo {
	
	/**
	 * 工作站
	 */
	private List<PageData> GZZLIST;
	
	/**
	 * 录像机
	 */
	private List<PageData> LXJLIST;
	
	/**
	 * 控制器
	 */
	private List<PageData> KZQLIST;
	
	/**
	 * 门岗LIST
	 */
	private List<PageData> MGLIST;
	
	
	/**
	 * 人脸设备LIST
	 */
	private List<PageData> faceLIST;
	
	
	/**
	 * 组织和项目
	 */
	private PageData DP;
	
	/**
	 * 劳务人员LIST
	 */
	private List<PageData> LWRYLIST;
	
	/**
	 * 照片LIST
	 */
	private List<PageData> ZPLIST;
	
	/**
	 * 门区权限LIST
	 */
	private List<PageData> MQQXLIST;
	
	/**
	 * 卡信息LIST
	 */
	private List<PageData> CARDLIST;
	
	
	/**
	 * 新增 luzhen 20170627
	 * 队伍list  T_DUIWU表
	 */
	private List<PageData> DUIWULIST;
	
	/**
	 * 分包商 list
	 */
	private List<PageData> FENBAOSHANGLIST;
	
	/**
	 * 班组 list
	 */
	private List<PageData> BZLIST;
	
	/**
	 * 工种 list
	 */
	private List<PageData> GONGZHONGLIST;
	
	/**
	 * 黑名单list
	 */
	private List<PageData> HMDLIST;
	

	public ReturnVo(){
		init();
	}
	
	public void init(){
		this.RST = RstEnum.正常.getvalue();
		
		this.GZZLIST = new ArrayList<PageData>();
		this.LXJLIST = new ArrayList<PageData>();
		this.KZQLIST = new ArrayList<PageData>();
		this.DP = new PageData();
	}

	
	
	public List<PageData> getFaceLIST() {
		return faceLIST;
	}

	public void setFaceLIST(List<PageData> faceLIST) {
		this.faceLIST = faceLIST;
	}

	public List<PageData> getGZZLIST() {
		return GZZLIST;
	}

	public void setGZZLIST(List<PageData> gZZLIST) {
		GZZLIST = gZZLIST;
	}

	public List<PageData> getLXJLIST() {
		return LXJLIST;
	}

	public void setLXJLIST(List<PageData> lXJLIST) {
		LXJLIST = lXJLIST;
	}

	public List<PageData> getKZQLIST() {
		return KZQLIST;
	}

	public void setKZQLIST(List<PageData> kZQLIST) {
		KZQLIST = kZQLIST;
	}

	public PageData getDP() {
		return DP;
	}

	public void setDP(PageData dP) {
		DP = dP;
	}

	public List<PageData> getLWRYLIST() {
		return LWRYLIST;
	}

	public void setLWRYLIST(List<PageData> lWRYLIST) {
		LWRYLIST = lWRYLIST;
	}

	public List<PageData> getZPLIST() {
		return ZPLIST;
	}

	public void setZPLIST(List<PageData> zPLIST) {
		ZPLIST = zPLIST;
	}

	public List<PageData> getMQQXLIST() {
		return MQQXLIST;
	}

	public void setMQQXLIST(List<PageData> mQQXLIST) {
		MQQXLIST = mQQXLIST;
	}

	public List<PageData> getCARDLIST() {
		return CARDLIST;
	}

	public void setCARDLIST(List<PageData> cARDLIST) {
		CARDLIST = cARDLIST;
	}

	public List<PageData> getMGLIST() {
		return MGLIST;
	}

	public void setMGLIST(List<PageData> mGLIST) {
		MGLIST = mGLIST;
	}

	public List<PageData> getDUIWULIST() {
		return DUIWULIST;
	}

	public void setDUIWULIST(List<PageData> dUIWULIST) {
		DUIWULIST = dUIWULIST;
	}

	public List<PageData> getFENBAOSHANGLIST() {
		return FENBAOSHANGLIST;
	}

	public void setFENBAOSHANGLIST(List<PageData> fENBAOSHANGLIST) {
		FENBAOSHANGLIST = fENBAOSHANGLIST;
	}

	public List<PageData> getBZLIST() {
		return BZLIST;
	}

	public void setBZLIST(List<PageData> bZLIST) {
		BZLIST = bZLIST;
	}

	public List<PageData> getGONGZHONGLIST() {
		return GONGZHONGLIST;
	}

	public void setGONGZHONGLIST(List<PageData> gONGZHONGLIST) {
		GONGZHONGLIST = gONGZHONGLIST;
	}

	public List<PageData> getHMDLIST() {
		return HMDLIST;
	}

	public void setHMDLIST(List<PageData> hMDLIST) {
		HMDLIST = hMDLIST;
	}

}
