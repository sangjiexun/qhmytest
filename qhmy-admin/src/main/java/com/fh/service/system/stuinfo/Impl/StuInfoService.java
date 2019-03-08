package com.fh.service.system.stuinfo.Impl;


import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.newsky.epay.sdk.HttpClient;
import com.fh.controller.system.login.LoginController;
import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.service.system.stuinfo.StuSignSupManager;
import com.fh.util.PageData;
import com.fh.util.Tools;

@Service("stuInfoService")
public class StuInfoService implements StuInfoManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Autowired
	private StuSignSupManager StuSignUpService;
	
	@Override
	public List<PageData> stuinfo_list(Page page) throws Exception {
		return (List<PageData>) dao.findForList("StuInfoMapper.stulistPage", page);
	}

	@Override
	public PageData getCurrentUserTableShowCollums(PageData pd)
			throws Exception {
		return (PageData) dao.findForObject("TableColumnMapper.getCurrentUserTableShowCollums", pd);
	}

	@Override
	public void updateShowCols(PageData pd) throws Exception {
		dao.update("TableColumnMapper.updateShowCols", pd);
		
	}

	@Override
	public void saveShowCols(PageData pd) throws Exception {
		dao.save("TableColumnMapper.saveShowCols", pd);
		
	}

	@Override
	public List<PageData> getCurrentUserTableShowCollumsList(PageData pd)
			throws Exception {
		return (List<PageData>)  dao.findForList("TableColumnMapper.getCurrentUserTableShowCollumsList", pd);
	}

	@Override
	public List<PageData> getPartSchoolList(PageData pd) throws Exception {
		return (List<PageData>)  dao.findForList("StuInfoMapper.getPartSchoolList", pd);
	}

	@Override
	public List<PageData> getCengCi(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StuInfoMapper.getCengCi", pd);
	}

	@Override
	public List<PageData> getMeiYuan(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StuInfoMapper.getMeiYuan", pd);
	}
	
	@Override
	public List<PageData> getBanJi(PageData pd) throws Exception {
		pd.put("banxingArray", pd.getString("BANXING_PKID").split(","));
		pd.put("gradeArray", pd.getString("SYS_DICTIONARIES_PKID").split(","));
		return (List<PageData>)  dao.findForList("StuInfoMapper.getBanJi", pd);
	}

	@Override
	public List<PageData> exportStuInfoToExcel(PageData pd) throws Exception {
		return (List<PageData>)dao.findForList("StuInfoMapper.exportStuInfoToExcel", pd);
	}

	@Override
	public PageData getStuInfoByPkid(PageData pd) throws Exception {
		return (PageData) dao.findForObject("StuInfoMapper.getStuInfoByPkid", pd);
	}
	
	@Override
	public List<PageData> getGrades(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("StuInfoMapper.getGrades", pd);
	}
	
	@Override
	public List<PageData> getBan(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("StuInfoMapper.getBan", pd);
	}
	
	@Override
	public List<PageData> gethzxx(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("StuInfoMapper.gethzxx", pd);
	}
	
	@Override
	public List<PageData> getbanji(PageData pd) throws Exception {
		
		return (List<PageData>) dao.findForList("StuInfoMapper.getbanji", pd);
	}
	
	@Override
	public void sendMessage(String STUDENT_PKID, String DORM_PKID)
			throws Exception {
		/**
		 * 发送模板消息
		 */
		Map map = new HashMap<>();
		map.put("STUDENT_PKID", STUDENT_PKID);
		map.put("DORM_PKID", DORM_PKID);
		map.put("template_title", "预约成功提醒");
		Properties prop = new Properties();
		InputStream in = null;
		in=LoginController.class.getClassLoader().getResourceAsStream("conf.properties");
		prop.load(in);
		String methodurl="";
		if(prop!=null){
			methodurl=prop.getProperty("neiUrl");
		}
		HttpClient httpClient = new HttpClient(methodurl+"/colleges-pay/dingDanPayController/sendMubanMessage", 30000, 30000);
		int resultCode = httpClient.send(map, "UTF-8");
		
	}
	@Override
	public List<PageData> getZuzhis(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StuInfoMapper.getZuzhis", pd);
	}
	
	@Override
	public List<PageData> getFromSYS_DICT(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StuInfoMapper.getFromSYS_DICT", pd);
	}
	@Override
	public PageData getDictionariesById(PageData pageData) throws Exception {
		return (PageData)dao.findForObject("StuInfoMapper.getDictionariesById", pageData);
	}
	
	@Override
	public void updateDorm(String STUDENT_PKID,String opt_dorm,String DORM_PKID,String opt_des,String T_STUDENT_DORM_TYPE_PKID) throws Exception {
		/*
		 * STUDENT_PKID：学生PKID  新增床位时可为空
		 * 、opt_dorm：要进行的操作，1表示绑定床位，0表示解绑操作 不涉及绑定关系时可为空
		 * 、DORM_PKID：宿舍PKID 有预定记录但没有绑定关系时可为空
		 */
		if(Tools.isEmpty(STUDENT_PKID) && !Tools.isEmpty(DORM_PKID)){
			//表示不涉及学生操作，此时只更新宿舍资源表
			PageData pd_drom_info = new PageData();//宿舍信息
			pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
			//该宿舍类型有数量限制
			if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
				PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSource", pd_drom_info);
				//如果没有数据则插入默认总床位数为1，有则更新总床位数加一
				if(pd_drom_source==null){
					dao.save("StuInfoMapper.insertSource", pd_drom_info);
				}else{
					if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//表示预留床
						pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() + 1);
					}else{
						pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
					}
					dao.update("StuInfoMapper.updateSource", pd_drom_info);
				}
			}
		}else if(Tools.isEmpty(DORM_PKID) && !Tools.isEmpty(STUDENT_PKID)){
			//表示学生不为空，宿舍pkid为空，此时只涉及预定记录和已预订床位数维护
			
			//根据学生PKID和宿舍类型 获取是否有未作废的预定记录
			PageData pd_log = new PageData();
			pd_log.put("STUDENT_PKID", STUDENT_PKID);
			pd_log.put("T_STUDENT_DORM_TYPE_PKID", T_STUDENT_DORM_TYPE_PKID);
			PageData pd_resource_log = (PageData)dao.findForObject("StuInfoMapper.getResLog", pd_log);
			
			//有无未作废的记录，如果有，更新为作废，并将宿舍资源已预订数减一,如果没有，则不做任何操作
			if(pd_resource_log!=null){
				dao.update("StuInfoMapper.updatelogcancel", pd_resource_log);
				PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSource", pd_resource_log);
				if(pd_drom_source!=null){
					dao.update("StuInfoMapper.updateSourceOrder", pd_resource_log);
				}
			}
		}else if(!Tools.isEmpty(DORM_PKID) && !Tools.isEmpty(STUDENT_PKID) && !Tools.isEmpty(opt_dorm)){
			//表示有绑定关系，涉及资源表
			
			//判断操作是绑定操作，还是解绑操作
			if("1".equals(opt_dorm)){
				//如果是绑定操作，维护宿舍资源表已占用+1和已预订床位数-1，作废该学生的预订记录，如果没有预订记录，只将已占用数加一
				
				//更新学生宿舍绑定关系
				PageData pd_stu_dorm = new PageData();
				pd_stu_dorm.put("STUDENT_PKID", STUDENT_PKID);
				pd_stu_dorm.put("DORM_PKID", DORM_PKID);
				dao.update("StuInfoMapper.updateStuDormY", pd_stu_dorm);
				
				PageData pd_log = new PageData();
				pd_log.put("STUDENT_PKID", STUDENT_PKID);
				pd_log.put("T_STUDENT_DORM_TYPE_PKID", T_STUDENT_DORM_TYPE_PKID);
				PageData pd_resource_log = (PageData)dao.findForObject("StuInfoMapper.getResLog", pd_log);
				//有无未作废的记录，如果有，更新为作废
				if(pd_resource_log!=null){
					dao.update("StuInfoMapper.updatelogcancel", pd_resource_log);
					PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSource", pd_resource_log);
					if(pd_drom_source!=null){
						PageData pd_drom_info = new PageData();//宿舍信息
						pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
						if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//如果被分配到了预留的床位上时
							pd_resource_log.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() - 1);
						}else{
							pd_resource_log.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
						}
						dao.update("StuInfoMapper.updateSourceOrderUse", pd_resource_log);
					}
				}else{
					PageData pd_drom_info = new PageData();//宿舍信息
					pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
					if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
						PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSource", pd_drom_info);
						if(pd_drom_source!=null){
							if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//如果被分配到了预留的床位上时
								pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() - 1);
							}else{
								pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
							}
							//更新已占用数+1
							dao.update("StuInfoMapper.updateSourceUseAdd", pd_drom_info);
						}
					}
				}
				
			}else if("0".equals(opt_dorm)){
				
				//更新学生宿舍绑定关系
				PageData pd_stu_dorm = new PageData();
				pd_stu_dorm.put("STUDENT_PKID", STUDENT_PKID);
				pd_stu_dorm.put("DORM_PKID", DORM_PKID);
				dao.update("StuInfoMapper.updateStuDormNo", pd_stu_dorm);
				
				//如果是解绑操作，维护宿舍资源表已占用-1
				PageData pd_drom_info = new PageData();//宿舍信息
				pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
				if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
					PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSource", pd_drom_info);
					if(pd_drom_source!=null){
						if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//如果被分配到了预留的床位上时
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() + 1);
						}else{
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
						}
						//更新已占用数-1
						dao.update("StuInfoMapper.updateSourceUseMin", pd_drom_info);
					}
				}

			}
			//插入操作日志
			PageData pd_dormUseLog = new PageData();
			pd_dormUseLog.put("DORM_PKID", DORM_PKID);
			pd_dormUseLog.put("STUDENT_PKID", STUDENT_PKID);
			pd_dormUseLog.put("opt_dorm", opt_dorm);
			pd_dormUseLog.put("opt_des", opt_des);
			dao.save("StuInfoMapper.saveStuDormUseLog", pd_dormUseLog);
		}
		
	}
	@Override
	public void updateDorm(String STUDENT_PKID,String opt_dorm,String DORM_PKID,String opt_des,
			String T_STUDENT_DORM_TYPE_PKID,String OLD_DEPARTMENT_PKID, String NEW_DEPARTMENT_PKID) throws Exception {
		/*
		 * STUDENT_PKID：学生PKID  新增床位时可为空
		 * 、opt_dorm：要进行的操作，1表示绑定床位，0表示解绑操作不涉及绑定关系时可为空
		 * 、DORM_PKID：宿舍PKID 有预定记录但没有绑定关系时可为空
		 * opt_des:操作描述
		 * T_STUDENT_DORM_TYPE_PKID：宿舍类型PKID
		 * OLD_DEPARTMENT_PKID：旧所属院校专业PKID,当没有旧所属院校专业时，该值可为空，该值也可以用作记录分配时该床所属的专业
		 * NEW_DEPARTMENT_PKID: 新所属院校专业PKID，该值也可以用作记录学生的院校专业pkid
		 */
		if(NEW_DEPARTMENT_PKID.equals(OLD_DEPARTMENT_PKID)){
			OLD_DEPARTMENT_PKID = null;
		}
		if(Tools.isEmpty(STUDENT_PKID) && !Tools.isEmpty(DORM_PKID)){
			//表示不涉及学生操作，此时只更新宿舍资源表（新增/分配或者删除/回收时）
			PageData pd_drom_info = new PageData();//宿舍信息
			pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
			//该宿舍类型有数量限制
			if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
					pd_drom_info.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
					PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
					//如果没有数据则插入默认总床位数为1，有则更新总床位数加一
					if(pd_drom_source==null){
						dao.save("StuInfoMapper.insertSourceD", pd_drom_info);
					}else{
						if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//表示预留床
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() + 1);
						}else{
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
						}
						pd_drom_info.put("ROOM_SUM_NUM", Double.parseDouble(pd_drom_source.getString("ROOM_SUM_NUM"))+1);
						dao.update("StuInfoMapper.updateSourceD", pd_drom_info);
					}
					if(!Tools.isEmpty(OLD_DEPARTMENT_PKID)){//需要将旧专业的宿舍资源减一
						pd_drom_info.put("DEPARTMENT_PKID", OLD_DEPARTMENT_PKID);
						pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
						if(pd_drom_source!=null){
							if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//表示预留床
								pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() - 1);
							}else{
								pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
							}
							pd_drom_info.put("ROOM_SUM_NUM", Double.parseDouble(pd_drom_source.getString("ROOM_SUM_NUM"))-1);
							dao.update("StuInfoMapper.updateSourceD", pd_drom_info);
						}
					}
			}
		}else if(Tools.isEmpty(DORM_PKID) && !Tools.isEmpty(STUDENT_PKID)){
			//表示学生不为空，宿舍pkid为空，此时只涉及预定记录和已预订床位数维护（学生毕业，但是一直没有分配床位）
			
			//根据学生PKID和宿舍类型获取是否有未作废的预定记录
			PageData pd_log = new PageData();
			pd_log.put("STUDENT_PKID", STUDENT_PKID);
			pd_log.put("T_STUDENT_DORM_TYPE_PKID", T_STUDENT_DORM_TYPE_PKID);
			PageData pd_resource_log = (PageData)dao.findForObject("StuInfoMapper.getResLog", pd_log);
			
			//有无未作废的记录，如果有，更新为作废，并将宿舍资源已预订数减一,如果没有，则不做任何操作
			if(pd_resource_log!=null){
				dao.update("StuInfoMapper.updatelogcancel", pd_resource_log);
				pd_resource_log.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
				PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_resource_log);
				if(pd_drom_source!=null){
					pd_resource_log.put("ROOM_ALREADY_ORDER", Double.parseDouble(pd_drom_source.getString("ROOM_ALREADY_ORDER"))-1);
					dao.update("StuInfoMapper.updateSourceOrderD", pd_resource_log);
				}
			}
		}else if(!Tools.isEmpty(DORM_PKID) && !Tools.isEmpty(STUDENT_PKID) && !Tools.isEmpty(opt_dorm)){
			//表示有绑定关系，涉及资源表
			
			//判断操作是绑定操作，还是解绑操作
			if("1".equals(opt_dorm)){
				//如果是绑定操作，维护宿舍资源表已占用+1和已预订床位数-1，作废该学生的预订记录，如果没有预订记录，只将已占用数加一
				
				//更新学生宿舍绑定关系
				PageData pd_stu_dorm = new PageData();
				pd_stu_dorm.put("STUDENT_PKID", STUDENT_PKID);
				pd_stu_dorm.put("DORM_PKID", DORM_PKID);
				dao.update("StuInfoMapper.updateStuDormY", pd_stu_dorm);
				
				PageData pd_log = new PageData();
				pd_log.put("STUDENT_PKID", STUDENT_PKID);
				pd_log.put("T_STUDENT_DORM_TYPE_PKID", T_STUDENT_DORM_TYPE_PKID);
				PageData pd_resource_log = (PageData)dao.findForObject("StuInfoMapper.getResLog", pd_log);
				if(!"调宿".equals(opt_des)){
					//有无未作废的记录，如果有，更新为作废
					if(pd_resource_log!=null){
						dao.update("StuInfoMapper.updatelogcancel", pd_resource_log);
						pd_resource_log.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
						PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_resource_log);
						if(pd_drom_source!=null){
							pd_resource_log.put("ROOM_ALREADY_ORDER", Double.parseDouble(pd_drom_source.getString("ROOM_ALREADY_ORDER"))-1);
							pd_resource_log.put("ROOM_ALREADY_USED", Double.parseDouble(pd_drom_source.getString("ROOM_ALREADY_USED"))+1);
							dao.update("StuInfoMapper.updateSourceOrderUseD", pd_resource_log);
						}
					}else{
						PageData pd_drom_info = new PageData();//宿舍信息
						pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
						if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
							pd_drom_info.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
							PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
							if(pd_drom_source!=null){
								//更新已占用数+1
								dao.update("StuInfoMapper.updateSourceUseAddD", pd_drom_info);
							}
						}
					}
				}

				/*
				 * 2018-07-09新增逻辑：
				 * 要以学生所在专业为准，绑定操作时，判断这两个专业是否相同（即old是否为空），如果不同（不为空），需要将宿舍所属专业总床位数减一，将学生所属专业总床位数加一
				 * 如果相同（为空），总床位数不做处理 
				 */
				if(!Tools.isEmpty(OLD_DEPARTMENT_PKID)){//需要将旧专业的宿舍资源减一
					PageData pd_drom_info = new PageData();//宿舍信息
					pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
					pd_drom_info.put("DEPARTMENT_PKID", OLD_DEPARTMENT_PKID);
					PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
					if(pd_drom_source!=null){
						pd_drom_info.put("ROOM_SUM_NUM", Double.parseDouble(pd_drom_source.getString("ROOM_SUM_NUM"))-1);
						if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//表示预留床
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() - 1);
						}
						dao.update("StuInfoMapper.updateSourceD", pd_drom_info);
					}
					pd_drom_info.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
					pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
					//如果没有数据则插入默认总床位数为1，有则更新总床位数加一
					if(pd_drom_source==null){
						dao.save("StuInfoMapper.insertSourceD", pd_drom_info);
					}else{
						pd_drom_info.put("ROOM_SUM_NUM", Double.parseDouble(pd_drom_source.getString("ROOM_SUM_NUM"))+1);
						if(Tools.notEmpty(pd_drom_info.getString("IS_KEEP")) && "1".equals(pd_drom_info.getString("IS_KEEP"))){//表示预留床
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue() + 1);
						}else{
							pd_drom_info.put("ROOM_ALREADY_KEEP", Integer.valueOf(pd_drom_source.getString("ROOM_ALREADY_KEEP")).intValue());
						}
						dao.update("StuInfoMapper.updateSourceD", pd_drom_info);
					}
				}
				
			}else if("0".equals(opt_dorm)){
				
				//更新学生宿舍绑定关系
				PageData pd_stu_dorm = new PageData();
				pd_stu_dorm.put("STUDENT_PKID", STUDENT_PKID);
				pd_stu_dorm.put("DORM_PKID", DORM_PKID);
				dao.update("StuInfoMapper.updateStuDormNo", pd_stu_dorm);
				
				//如果是解绑操作，维护宿舍资源表已占用-1
				PageData pd_drom_info = new PageData();//宿舍信息
				pd_drom_info = (PageData)dao.findForObject("StuInfoMapper.getDromInfo", DORM_PKID);
				if("Y".equals(pd_drom_info.getString("IS_NUM_LIMIT"))){
					pd_drom_info.put("DEPARTMENT_PKID", NEW_DEPARTMENT_PKID);
					PageData pd_drom_source = (PageData)dao.findForObject("StuInfoMapper.getDormSourceD", pd_drom_info);
					if(pd_drom_source!=null){
						//更新已占用数-1
						dao.update("StuInfoMapper.updateSourceUseMinD", pd_drom_info);
					}
				}
			}
			//插入操作日志
			PageData pd_dormUseLog = new PageData();
			pd_dormUseLog.put("DORM_PKID", DORM_PKID);
			pd_dormUseLog.put("STUDENT_PKID", STUDENT_PKID);
			pd_dormUseLog.put("opt_dorm", opt_dorm);
			pd_dormUseLog.put("opt_des", opt_des);
			dao.save("StuInfoMapper.saveStuDormUseLog", pd_dormUseLog);
		}
		
	}
	
	public static boolean compareDate(String date1,String date2) throws Exception{
		DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
			Date d1 = dateFormat.parse(date1);
			Date d2 = dateFormat.parse(date2);
			boolean boo=false;
			if(d1.equals(d2)){
				boo=true;
			}else if(d1.before(d2)){
				System.out.println(date1+"在"+date2+"之前");
			}else if(d1.after(d2)){
				boo=true;
			}
			return boo;
	}
	public static String getIntersection(List<String> list1) throws Exception {
		 List dateList = new ArrayList();
	       DateFormat dateFormat=new SimpleDateFormat("yyyy-MM");
	       String zuida="";
	       Date date = null; 
	       for (int i=0;i<list1.size();i++) {  
	    	   date = dateFormat.parse(list1.get(i)); 
	    	   dateList.add(date);
	   		
	        }  
	       ComparatorDate c = new ComparatorDate();  
	       Collections.sort(dateList, c);  
	       dateList.get(dateList.size()-1);
	       String dateString = dateFormat.format(dateList.get(dateList.size()-1));
		   return dateString;
	     //  System.out.print(getStringDateShort(dateList.get(dateList.size()-1)));
	    }
		
		public static class ComparatorDate implements Comparator {  
		    public int compare(Object obj1, Object obj2) {  
		        Date begin = (Date) obj1;  
		        Date end = (Date) obj2;  
		        if (begin.after(end)) {  
		            return 1;  
		        } else {  
		            return -1;  
		        }  
		    }  
		}

		@Override
		public void batchTongGuo(PageData pd) throws Exception {
			if("1".equals(pd.getString("status"))){
				String[] pkids = pd.getString("pkids").split(",");//学生附表PKIDS
				PageData pd_item_list = null;
				PageData pd_stubm = null;
				PageData pdRule = null;
				//根据附表信息生成缴费名单
				for(String stuBmpkid : pkids){
					pd_stubm = new PageData();
					pd_stubm.put("STUDENT_BM_PKID",stuBmpkid);
					//根据BM获取对应的入学年份和班型以及要生成的缴费项目
					List<PageData> list_item = (List<PageData>)dao.findForList("StuInfoMapper.getItemListbyBM", pd_stubm);
					for(PageData item_pd : list_item){
						pd_item_list = new PageData();
						pd_item_list.put("STUDENT_BM_PKID", stuBmpkid);
						pd_item_list.put("T_PAY_ITEM_PKID", item_pd.getString("PKID"));
						//查询对应的名单是否已经添加
						PageData item_list = (PageData)dao.findForObject("StuInfoMapper.getItemList", pd_item_list);
						if(item_list!=null){
							continue;
						}
						pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pd_item_list);
						BigDecimal bgCost = null;
						BigDecimal bgZJJinE = new BigDecimal(0);
						if(pdRule!=null){
							bgCost = new BigDecimal(pdRule.getString("COST"));//应收费用
							bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
							//优惠类型 0表示直减 1表示增加
							String zenJianType = "";//优惠类型
							BigDecimal bgResult = new BigDecimal(0);
							zenJianType = pdRule.getString("ZENGJIAN_TYPE");
							if("0".equals(zenJianType)){//0则相减	
								bgResult = bgCost.subtract(bgZJJinE);
								pd_item_list.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
								pd_item_list.put("DISCOUNT_MODE", 2);//优惠方式
							}else{//1则相加
								bgResult =  bgCost.add(bgZJJinE);
								pd_item_list.put("DISCOUNT", "增加"+bgZJJinE);//优惠
								pd_item_list.put("DISCOUNT_MODE", 3);//优惠方式
							}
							//优惠金额如果时0则不优惠
							if(bgZJJinE.equals(new BigDecimal(0))){
								pd_item_list.put("DISCOUNT", "不优惠");//优惠
								pd_item_list.put("DISCOUNT_MODE", 0);//优惠方式
							}
							//根据入学年份和班型和身份证号查询该学生是否有对应项目的预交，如果有预交，对应的学费减去预交项目的应收
							if("学费".equals(item_pd.getString("PAY_STYLE_NAME"))){
								item_pd.put("STUDENT_BM_PKID", pd_stubm.getString("STUDENT_BM_PKID"));
								PageData pd_stu_yj = (PageData)dao.findForObject("StuInfoMapper.getStuYJInfo", item_pd);
								if(pd_stu_yj!=null && pd_stu_yj.getString("GRADE_PKID").equals(item_pd.getString("GRADE_PKID"))
										&& pd_stu_yj.getString("CLASSTYPE_PKID").equals(item_pd.getString("CLASSTYPE_PKID"))){//表示该学生存在预交
									BigDecimal yingShouyj = new BigDecimal(pd_stu_yj.getString("AMOUNTRECEIVABLE"));//预交的应收
									bgResult = bgResult.subtract(yingShouyj);
								}
							}
							pd_item_list.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
							pd_item_list.put("AMOUNTRECEIVABLE", bgResult);//应收金额
							pd_item_list.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
							pd_item_list.put("COST", pdRule.getString("COST"));//费用
							dao.save("PayManageMapper.saveAddItemList", pd_item_list);
						}else{//表示查询不到优惠
							//根据项目PKID获取规则COST
							pdRule  = (PageData)dao.findForObject("PayManageMapper.getOnlyRuleCost", pd_item_list);
							if(pdRule!=null){
								//优惠金额如果时0则不优惠
								if(bgZJJinE.equals(new BigDecimal(0))){
									pd_item_list.put("DISCOUNT", "不优惠");//优惠
									pd_item_list.put("DISCOUNT_MODE", 0);//优惠方式
								}
								BigDecimal bgResult = new BigDecimal(pdRule.getString("COST"));
								//根据入学年份和班型和身份证号查询该学生是否有对应项目的预交，如果有预交，对应的学费减去预交项目的应收
								if("学费".equals(item_pd.getString("PAY_STYLE_NAME"))){
									item_pd.put("STUDENT_BM_PKID", pd_stubm.getString("STUDENT_BM_PKID"));
									PageData pd_stu_yj = (PageData)dao.findForObject("StuInfoMapper.getStuYJInfo", item_pd);
									if(pd_stu_yj!=null && pd_stu_yj.getString("GRADE_PKID").equals(item_pd.getString("GRADE_PKID"))
											&& pd_stu_yj.getString("CLASSTYPE_PKID").equals(item_pd.getString("CLASSTYPE_PKID"))){//表示该学生存在预交
										BigDecimal yingShouyj = new BigDecimal(pd_stu_yj.getString("AMOUNTRECEIVABLE"));//预交的应收
										bgResult = bgResult.subtract(yingShouyj);
									}
								}
								pd_item_list.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
								pd_item_list.put("AMOUNTRECEIVABLE", bgResult);//应收金额
								pd_item_list.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
								pd_item_list.put("COST", pdRule.getString("COST"));//费用
								dao.save("PayManageMapper.saveAddItemList", pd_item_list);
							}
						}
					}
				}
			}
			dao.update("StuInfoMapper.batchTongGuo", pd);			
		}

		@Override
		public void batchByOrTuiXue(PageData pd) throws Exception {
			dao.update("StuInfoMapper.batchByOrTuiXue", pd);
			String pkids = pd.getString("pkids");//学生副表pkid
			String rxnfpkids = pd.getString("rxnfpkids");//入学年份pkid
			String bxpkids = pd.getString("bxpkids");//班型pkid
			String stuPkid = pd.getString("stuPkids");//学生pkid
			String [] pkidArr = pkids.split(",");
			String [] rxnfArr = rxnfpkids.split(",");
			String [] bxpArr = bxpkids.split(",");
			String [] stuPkidArr = stuPkid.split(",");
			PageData pdParam = new PageData();
			PageData pdResult = null;
			for(int i = 0 ;i<pkidArr.length;i++){
				pdParam.put("T_STUDENT_PKID", stuPkidArr[0]);
				pdParam.put("SYS_DICTIONARIES_PKID", rxnfArr[0]);
				pdParam.put("CLASSTYPE_ID", bxpArr[0]);
				pdParam.put("T_STUDENT_BM_PKID", pkidArr[0]);
				//根据学生pkid 入学年份pkid 班型pkid 查询学生宿舍
				pdResult =  (PageData) dao.findForObject("StuInfoMapper.getDormByBmPkid", pdParam);
				if(pdResult!=null){
					dao.update("StuInfoMapper.updateDormByPkid", pdResult);
				}
			}
			
		} 
		@Override
		public void batchDelete(PageData pd,String [] pkids) throws Exception {
			dao.update("StuInfoMapper.deleteAll", pkids);
			dao.delete("StuInfoMapper.batchdeleteStuPayItemLists", pkids);
			String pkidst = pd.getString("pkids");//学生副表pkid
			String rxnfpkids = pd.getString("rxnfpkids");//入学年份pkid
			String bxpkids = pd.getString("bxpkids");//班型pkid
			String stuPkid = pd.getString("stuPkids");//学生pkid
			String [] pkidArr = pkidst.split(",");
			String [] rxnfArr = rxnfpkids.split(",");
			String [] bxpArr = bxpkids.split(",");
			String [] stuPkidArr = stuPkid.split(",");
			PageData pdParam = new PageData();
			PageData pdResult = null;
			for(int i = 0 ;i<pkidArr.length;i++){
				pdParam.put("YJ_STUBM_PKID", pkidArr[0]);
				dao.update("StuInfoMapper.updateStuYJByBMpkid", pdParam);
				pdParam.put("T_STUDENT_PKID", stuPkidArr[0]);
				pdParam.put("SYS_DICTIONARIES_PKID", rxnfArr[0]);
				pdParam.put("CLASSTYPE_ID", bxpArr[0]);
				//根据学生pkid 入学年份pkid 班型pkid 查询学生宿舍
				pdResult =  (PageData) dao.findForObject("StuInfoMapper.getDormByXRB", pdParam);
				if(pdResult!=null){
					dao.update("StuInfoMapper.updateDormByPkid", pdResult);
				}
			}
		}

		@Override
		public PageData getDepListById(String PKID) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getDepListById", PKID);
		}

		@Override
		public List<PageData> getDepList(PageData pd) throws Exception {
			return (List<PageData>) dao.findForList("StuInfoMapper.getDepList", pd);
		}

		@Override
		public void updateStu(PageData pd) throws Exception {
			dao.update("StuInfoMapper.updateStuInfo", pd);
			dao.update("StuInfoMapper.updateStuBmInfo", pd);
			if("1".equals(pd.getString("TONGGUO"))){
				String yjPkid = "";//用来记录如果已经有预交的项目pkid
				PageData yjInfo = null;
				pd.put("STUPKID", pd.getString("PKID"));
				//学生副表pkid
				pd.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
				
				//根据学生副表pkid查询缴费项目对应的缴费类型
				pd.put("PAY_STYLE_NAMES", "PAY_STYLE_NAME");
				List<PageData> pdOldList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeListByBmPkid", pd);
				pd.put("GRADE_PKID",pd.getString("RXNIANFEN"));//入学年份
				pd.put("CLASSTYPE_PKID", pd.getString("BANJI_TYPE"));//班型
				//学生新的入学年份和班型对应的缴费类型
				pd.put("PAY_STYLE_NAMES", "");
				pd.put("PAY_STYLE_NAMESS", "PAY_STYLE_NAMESS");
				List<PageData> pdNewList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeList", pd);
				//定义原名单表缴费类型map(1)
				Map<String,String> typeOldMap = new HashMap<String,String>();
				//定义原名单表缴费类型map(1)
				Map<String,String> typeOldMap1 = new HashMap<String,String>();
				//定义新入学年份和班型对应的名单表缴费类型map(2)
				Map<String,String> typeNewMap = new HashMap<String,String>();
				//定义1和2能匹配上的缴费类型map(用来修改)记录新的项目pkid
				Map<String,String> typeMateMap = new HashMap<String,String>();
				//定义1和2能匹配上的缴费类型map(用来修改)记录旧的的项目pkid
				Map<String,String> typeOldMateMap = new HashMap<String,String>();
				//typeOldMap赋值
				if(pdOldList!=null&&pdOldList.size()>0){
					for(PageData pdOld : pdOldList){
						//键为缴费类型pkid 值为缴费项目pkid
						typeOldMap.put(pdOld.getString("PAY_TYPE_PKID"),pdOld.getString("PKID"));
						typeOldMap1.put(pdOld.getString("PAY_TYPE_PKID"),pdOld.getString("PKID"));
					}
				}
				//typeNewMap赋值
				if(pdNewList!=null&&pdNewList.size()>0){
					for(PageData pdOld : pdNewList){
						//键为缴费类型pkid 值为缴费项目pkid
						typeNewMap.put(pdOld.getString("PAY_TYPE_PKID"),pdOld.getString("PKID"));
					}
				}
				//比较typeOldMap和typeNewMap相同的值
			    for (String key : typeOldMap1.keySet()) {
			    	//匹配两个map中相同的键值
			    	boolean contain  = typeNewMap.containsKey(key);
			        //如果能匹配上 typeMateMap赋值  typeNewMap和typeOldMap删除此值
			        if (contain) {
			        	typeMateMap.put(key, typeNewMap.get(key));
			        	typeOldMateMap.put(key, typeOldMap.get(key));
			        	typeOldMap.remove(key);
			        	typeNewMap.remove(key);
			        }
			    }
			    //如果typeOldMap不为空则关闭缴费名单
			    if(!typeOldMap.isEmpty()&&typeOldMap.size()>0){
			    	//循环遍历typeOldMap 根据value值（缴费项目T_PAY_ITEM的PKID）和学生副表pkid查询缴费项目状态跟新为'已关闭'
			    	pd.put("STATUS", 3);
			    	for (String key : typeOldMap.keySet()) {
			    		pd.put("T_PAY_ITEM_PKID", typeOldMap.get(key));
			    		dao.update("StuInfoMapper.updateItemListStatus", pd);
			    	}
			    }
			    //如果typeNewMap不为空则新增缴费名单
			    if(!typeNewMap.isEmpty()&&typeNewMap.size()>0){
			    	PageData pdRule = null;//缴费规则结果
			    	PageData pdInsert = new PageData();//缴费名单结果实体
					pdInsert.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
					pdInsert.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
					pdInsert.put("STATUS", 0);//状态默认欠费
					String zenJianType = "";//优惠类型
					BigDecimal bgResult = new BigDecimal(0);
					PageData pdType = null;//缴费类型结果
					//用来查询条件的实体
					PageData pdQuery = new PageData();
					//循环遍历typeNewMap 根据value值（缴费项目T_PAY_ITEM的PKID）查询缴费规则生成对应的缴费名单
					for(String key : typeNewMap.keySet()){
						pdQuery.put("T_PAY_ITEM_PKID", typeNewMap.get(key));
						pdQuery.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
						pdInsert.put("T_PAY_ITEM_PKID", typeNewMap.get(key));//缴费项目pkid
						String isFlag = "";
						//根据pkid查询缴费类型表
						pdType = (PageData) dao.findForObject("StuInfoMapper.getItemTypeName", pdQuery);
						//查询对应的缴费规则
						pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pdQuery);
						if(pdRule==null){
							pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pdQuery);
							isFlag = "Y";
						}
						if(pdRule!=null){
							BigDecimal bgCost = new BigDecimal(pdRule.getString("COST"));//标准费用
							BigDecimal bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
							if("Y".equals(isFlag)){
								bgZJJinE = new BigDecimal(0);
							}
							//优惠类型 0表示直减 1表示增加
							zenJianType = pdRule.getString("ZENGJIAN_TYPE");
							if("0".equals(zenJianType)){//0则相减	
								bgResult = bgCost.subtract(bgZJJinE);
								pdInsert.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 2);//优惠方式
							}else{//1则相加
								bgResult =  bgCost.add(bgZJJinE);
								pdInsert.put("DISCOUNT", "增加"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 3);//优惠方式
							}
							//优惠金额如果时0则不优惠
							if(bgZJJinE.equals(new BigDecimal(0))){
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
							}
							//如果对应的入学年份和班型有预交计算费用时需要把预交金额减掉
							if(pdType!=null&&"学费".equals(pdType.getString("PAY_STYLE_NAME"))){
								if(pd.getString("yjnianfenPkid")!=null&&!"".equals(pd.getString("yjnianfenPkid"))){
									if(pd.getString("yjnianfenPkid").equals(pd.getString("RXNIANFEN"))&&
											pd.getString("yjbanxingPkid").equals(pd.getString("BANJI_TYPE"))){
										pd.put("flag", "");
									}else{
										pd.put("flag", "flag");
									}
								}
								yjInfo = (PageData) dao.findForObject("StuInfoMapper.getYuJiaoPay", pd);
								if(yjInfo != null){
									BigDecimal bgYjJe = new BigDecimal(yjInfo.getString("AMOUNTRECEIVABLE"));//优惠金额
									bgResult =  bgResult.subtract(bgYjJe);
									if("Y".equals(isFlag)){
										bgCost = bgCost.subtract(bgYjJe);
									}
								}
							}
							pdInsert.put("COST", bgResult);//费用
							pdInsert.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
							pdInsert.put("AMOUNTRECEIVABLE", bgResult);//应收金额
							pdInsert.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
							if("Y".equals(isFlag)){
								pdInsert.put("COST", bgCost);//费用
								pdInsert.put("DISCOUNT_MONEY", 0);//优惠金额
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
								pdInsert.put("AMOUNTRECEIVABLE", bgCost);//应收金额
							}
							//新增缴费项
							dao.save("StuInfoMapper.insertItemList", pdInsert);
						}
						
					}
			    }
			    //如果typeMateMap不为空则更新缴费名单
			    if(!typeMateMap.isEmpty()&&typeMateMap.size()>0){
			    	PageData pdRule = null;//缴费规则结果
			    	PageData pdItem = null;//缴费规则结果
			    	PageData pdType = null;//缴费类型结果
			    	PageData pdInsert = new PageData();//缴费名单结果实体
					pdInsert.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
					pdInsert.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
					/*pdInsert.put("STUDENT_BM_PKID_NEW", pd.getString("TMPKID"));//学生副表pkid
	*/				pdInsert.put("STATUS", 0);//状态默认欠费
					String zenJianType = "";//优惠类型
					BigDecimal bgResult = new BigDecimal(0);
					//用来查询条件的实体
					PageData pdQuery = new PageData();
					//循环遍历typeNewMap 根据value值（缴费项目T_PAY_ITEM的PKID）查询缴费规则生成对应的缴费名单
					for(String key : typeMateMap.keySet()){
						pdQuery.put("T_PAY_ITEM_PKID", typeMateMap.get(key));
						pdQuery.put("PKID", key);
						pdQuery.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
						pdInsert.put("T_PAY_ITEM_PKID", typeMateMap.get(key));//心得缴费项目pkid
						pdInsert.put("T_PAY_ITEM_PKID_OLD", typeOldMateMap.get(key));//旧的缴费项目pkid
						String isFlag = "";
						//查询对应的缴费规则 
						pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pdQuery);
						//根据学生副表pkid和项目pkid查询一条缴费数据 
						pdItem = (PageData) dao.findForObject("StuInfoMapper.getItemTypeListByBmPkidItemPkid", pdQuery);
						//根据pkid查询缴费类型表
						pdType = (PageData) dao.findForObject("StuInfoMapper.getPayTypeByPkid", pdQuery);
						if(pdType!=null&&"预交".equals(pdType.getString("PAY_STYLE_NAME"))){
							yjPkid = typeMateMap.get(key);
						}
						if(pdRule==null){
							pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pdQuery);
							isFlag = "T";
						}
						if(pdRule!=null){
							BigDecimal bgCost = new BigDecimal(pdRule.getString("COST"));//标准费用
							BigDecimal bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
							if("T".equals(isFlag)){
								bgZJJinE = new BigDecimal(0);
							}
							//优惠类型 0表示直减 1表示增加
							zenJianType = pdRule.getString("ZENGJIAN_TYPE");
							if("0".equals(zenJianType)){//0则相减	
								bgResult = bgCost.subtract(bgZJJinE);
								pdInsert.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 2);//优惠方式
							}else{//1则相加
								bgResult =  bgCost.add(bgZJJinE);
								pdInsert.put("DISCOUNT", "增加"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 3);//优惠方式
							}
							//优惠金额如果时0则不优惠
							if(bgZJJinE.equals(new BigDecimal(0))){
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
							}
							//如果对应的入学年份和班型有预交计算费用时需要把预交金额减掉
							if(pdType!=null&&"学费".equals(pdType.getString("PAY_STYLE_NAME"))){
								if(pd.getString("yjnianfenPkid")!=null&&!"".equals(pd.getString("yjnianfenPkid"))){
									if(pd.getString("yjnianfenPkid").equals(pd.getString("RXNIANFEN"))&&
											pd.getString("yjbanxingPkid").equals(pd.getString("BANJI_TYPE"))){
										pd.put("flag", "");
									}else{
										pd.put("flag", "flag");
									}
								}
								yjInfo = (PageData) dao.findForObject("StuInfoMapper.getYuJiaoPay", pd);
								if(yjInfo != null){
									BigDecimal bgYjJe = new BigDecimal(yjInfo.getString("AMOUNTRECEIVABLE"));//优惠金额
									bgResult =  bgResult.subtract(bgYjJe);
									if("T".equals(isFlag)){
										bgCost = bgCost.subtract(bgYjJe);
									}
								}
							}
							
							pdInsert.put("COST", bgResult);//费用
							pdInsert.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
							pdInsert.put("AMOUNTRECEIVABLE", bgResult);//应收金额
							pdInsert.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
							if(pdItem!=null){
								//如果原来的实收金额大于现在的应收则状态为1 核验中 如果小于则为欠费
								BigDecimal bdAm = new BigDecimal(pdItem.getString("AMOUNTCOLLECTION"));
								//如果应交金额大于实收金额状态为欠费 相反则为核验中(如果应收金额和实收金额相等默认核验中 如果应收金额和实收金额不等默认欠费 )
								if(bgResult.compareTo(bdAm)!=0){
									pdInsert.put("STATUS", 0);//状态默认欠费
								}else{
									pdInsert.put("STATUS", 1);//状态默认核验中
								}
							}
							if("T".equals(isFlag)){
								pdInsert.put("COST", bgCost);//费用
								pdInsert.put("DISCOUNT_MONEY", 0);//优惠金额
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
								pdInsert.put("AMOUNTRECEIVABLE", bgCost);//应收金额
								if(pdItem!=null){
									//如果原来的实收金额大于现在的应收则状态为1 核验中 如果小于则为欠费
									BigDecimal bdAm = new BigDecimal(pdItem.getString("AMOUNTCOLLECTION"));
									//如果应交金额大于实收金额状态为欠费 相反则为核验中(如果应收金额和实收金额相等默认核验中 如果应收金额和实收金额不等默认欠费 )
									if(bgCost.compareTo(bdAm)!=0){
										pdInsert.put("STATUS", 0);//状态默认欠费
									}else{
										pdInsert.put("STATUS", 1);//状态默认核验中
									}
								}
							}
							//修改缴费项
							dao.update("StuInfoMapper.updateItemList", pdInsert);
						}else{
							//修改缴费项
							dao.update("StuInfoMapper.updateItemPkid", pdInsert);
						}
						
					}
			    }
			    String yjNianFen = pd.getString("YJ_NIANFEN");//预交年份
			    String yjBanjiType = pd.getString("YJ_BANJI_TYPE");//预交班型
			    //如果预交年份预交班型之前为空 现在有值 生成一条新的预交缴费项目
			    if(!"".equals(yjNianFen)&&yjNianFen!=null&&!"".equals(yjBanjiType)&&yjBanjiType!=null){		    	
			    		//根据入学年份和班型查询对应的缴费类型
						pd.put("GRADE_PKID", yjNianFen);
						pd.put("CLASSTYPE_PKID", yjBanjiType);
						pd.put("PAY_STYLE_NAMES", "PAY_STYLE_NAMES");
						pd.put("PAY_STYLE_NAMESS", "");
						dao.update("StuInfoMapper.updateItemListStaByStuPkid", pd);//
						//根据预交年份和预交班型查询缴费类型
						List<PageData> pdYjList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeList", pd);
						if(pdYjList!=null&&pdYjList.size()>0){
							PageData pdRule = null;//缴费规则结果
							PageData pdYjRe = null;//预交现在
							PageData pdYjReBe = null;//预交之前
							PageData pdType = null;//缴费类型
							PageData pdInsert = new PageData();//缴费名单结果实体
							PageData pdQueryT = new PageData();
							String zenJianType = "";//优惠类型
							BigDecimal bgResult = new BigDecimal(0);
							pdInsert.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
							pdInsert.put("STUDENT_BM_PKID_NEW", pd.getString("TMPKID"));//学生副表pkid
							pdInsert.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
							pdInsert.put("STATUS", 0);//状态默认欠费
							//循环遍历缴费类型 查询缴费类型是否是预交
							for(PageData pdYj : pdYjList){
								pdQueryT.put("PKID", pdYj.getString("PAY_TYPE_PKID"));
								pdQueryT.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
								//查询缴费类型
								pdType = (PageData) dao.findForObject("StuInfoMapper.getPayTypeByPkid", pdQueryT);
								if(pdType!=null&&"预交".equals(pdType.getString("PAY_STYLE_NAME"))){
									pdYj.put("T_PAY_ITEM_PKID", pdYj.getString("PKID"));//缴费项目pkid
									pdYj.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
									pdYj.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
									pdInsert.put("T_PAY_ITEM_PKID", pdYj.getString("PKID"));//新项目pkid
									pdInsert.put("T_PAY_ITEM_PKID_OLD", yjPkid);//旧项目pkid
									String isFlag = "";
									//查询对应的缴费规则 
									pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pdYj);
									if(pdRule==null){
										pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pdYj);
										isFlag = "X";
									}
									if(pdRule!=null){
										BigDecimal bgCost = new BigDecimal(pdRule.getString("COST"));//标准费用
										BigDecimal bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
										//优惠类型 0表示直减 1表示增加
										zenJianType = pdRule.getString("ZENGJIAN_TYPE");
										if("0".equals(zenJianType)){//0则相减	
											bgResult = bgCost.subtract(bgZJJinE);
											pdInsert.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
											pdInsert.put("DISCOUNT_MODE", 2);//优惠方式
										}else{//1则相加
											bgResult =  bgCost.add(bgZJJinE);
											pdInsert.put("DISCOUNT", "增加"+bgZJJinE);//优惠
											pdInsert.put("DISCOUNT_MODE", 3);//优惠方式
										}
										//优惠金额如果时0则不优惠
										if(bgZJJinE.equals(new BigDecimal(0))){
											pdInsert.put("DISCOUNT", "不优惠");//优惠
											pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
										}
										pdInsert.put("COST", bgResult);//费用
										pdInsert.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
										pdInsert.put("AMOUNTRECEIVABLE", bgResult);//应收金额
										pdInsert.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
										//如果是0则代表之前没有预交 生成新的缴费项list 如果是1说明之前有预交缴费项则更新之前的预交
										if("X".equals(isFlag)){
											pdInsert.put("COST", bgCost);//费用
											pdInsert.put("DISCOUNT_MONEY", 0);//优惠金额
											pdInsert.put("DISCOUNT", "不优惠");//优惠
											pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
											pdInsert.put("AMOUNTRECEIVABLE", bgCost);//应收金额
										}
										/*pdYj.put("yjnianfenPkid", pd.getString("yjnianfenPkid"));
										pdYj.put("yjbanxingPkid", pd.getString("yjbanxingPkid"));*/
										pdYj.put("yjnianfenPkid", yjNianFen);
										pdYj.put("yjbanxingPkid", yjBanjiType);
										//查询当前页面选择的预交年份和班型是否有未关闭的预交项目 如果有的话更新没有则新增
										pdYjRe =  (PageData) dao.findForObject("StuInfoMapper.getYjIsExist", pdYj);
										if(pdYjRe!=null){
											pdInsert.put("T_PAY_ITEM_PKID_OLD", pdYjRe.getString("T_PAY_ITEM_PKID"));//旧项目pkid
											//如果原来的实收金额大于现在的应收则状态为1 核验中 如果小于则为欠费
											BigDecimal bdAm = new BigDecimal(pdYjRe.getString("AMOUNTCOLLECTION"));
											//如果应交金额大于实收金额状态为欠费 相反则为核验中(如果应收金额和实收金额相等默认核验中 如果应收金额和实收金额不等默认欠费 )
											if(bgResult.compareTo(bdAm)!=0){
												pdInsert.put("STATUS", 0);//状态默认欠费
											}else{
												pdInsert.put("STATUS", 1);//状态默认核验中
											}
											pdInsert.put("STUDENT_BM_PKID", pdYjRe.getString("STUDENT_BM_PKID"));
											dao.update("StuInfoMapper.updateItemList", pdInsert);
											
										}else{
											dao.save("StuInfoMapper.insertItemList", pdInsert);
											pdInsert.put("YJ_STUBM_PKID", pd.getString("TMPKID"));
											pdInsert.put("PKID", pd.getString("PKID"));
											dao.update("StuInfoMapper.updateStuYjPkid", pdInsert);
											pdYj.put("yjnianfenPkid", pd.getString("yjnianfenPkid"));
											pdYj.put("yjbanxingPkid", pd.getString("yjbanxingPkid"));
											pdYjReBe =  (PageData) dao.findForObject("StuInfoMapper.getYjIsExist", pdYj);
											if(pdYjReBe!=null){
												dao.update("StuInfoMapper.updateStaByPkid", pdYjReBe);
											}
										}
										/*if("0".equals(pd.getString("isYj"))){
											//新增缴费项
											dao.save("StuInfoMapper.insertItemList", pdInsert);
										}else{
											//修改缴费项
											dao.update("StuInfoMapper.updateItemList", pdInsert);
										}*/
									}else{
										//修改缴费项
										dao.update("StuInfoMapper.updateItemPkid", pdInsert);
									}
								}
							}
						}
			    	}else{
			    		//用来查询条件的实体
						PageData pdQuery = new PageData();
						pdQuery.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
						PageData pdType = null;//缴费类型
						//根据学生副表pkid查询缴费项目对应的缴费类型
						List<PageData> pdItemList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeListByBmPkid", pdQuery);
						if(pdItemList!=null&&pdItemList.size()>0){
							for(PageData pdItem : pdItemList){
								pdQuery.put("PKID", pdItem.getString("PAY_TYPE_PKID"));
								//查询缴费类型
								pdType = (PageData) dao.findForObject("StuInfoMapper.getPayTypeByPkid", pdQuery);
								if(pdType!=null&&"预交".equals(pdType.getString("PAY_STYLE_NAME"))){
									pdQuery.put("T_PAY_ITEM_PKID", pdItem.getString("PKID"));
									pdQuery.put("STATUS", "3");//名单关闭
									//修改缴费项
									dao.update("StuInfoMapper.updateStatusByPkid", pdQuery);
								}
							}
						}
			    	}
			}
			
		}
		
		@Override
		public PageData getSysDictyionaries(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getSysDictyionaries", pd);
		}

		@Override
		public PageData getCengCiByName(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getCengCiByName", pd);
		}

		@Override
		public PageData getMeiByName(String XKMARK) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getMeiByName", XKMARK);
		}

		@Override
		public void batchSaveStu(List<PageData> list, String methodurl)
				throws Exception {
			List<PageData> listStuAdd = new ArrayList<PageData>();
			List<PageData> listStuUpdate = new ArrayList<PageData>();
			PageData pdIs = null;
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					pdIs = (PageData) dao.findForObject("StuInfoMapper.getStuinfoBySfz", list.get(i));
					if(pdIs!=null){
						listStuUpdate.add(list.get(i));
						list.get(i).put("PKID", pdIs.getString("PKID"));
					}else{
						listStuAdd.add(list.get(i));
					}
				}
			}
			//保存学生主表信息
			if(listStuAdd!=null&&listStuAdd.size()>0){
				dao.batchSave("StuInfoMapper.batchSaveStu", listStuAdd);
			}
			if(listStuUpdate!=null&&listStuUpdate.size()>0){
				//修改学生主表信息
				dao.batchUpdate("StuInfoMapper.updateStuBySfz", listStuUpdate);
			}
			
			//保存学生副表信息
			dao.batchSave("StuInfoMapper.batchSaveStuBM", list);
			//缴费类型list
			List<PageData> pdItemList = null;
			//循环学生名单生成对应的缴费项目
			for (PageData pd : list) {
				//根据入学年份和班型查询对应的缴费类型
				pd.put("GRADE_PKID",pd.getString("RXNIANFEN_PKID"));
				pd.put("CLASSTYPE_PKID", pd.getString("BANJI_TYPE_PKID"));
				pdItemList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeList", pd);
				//如果查询缴费类型结果不为空
				if(pdItemList!=null&&pdItemList.size()>0){
					PageData pdRule = null;//缴费规则结果
					PageData pdInsert = new PageData();//缴费名单结果实体
					pdInsert.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
					pdInsert.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
					pdInsert.put("STATUS", 0);//状态默认欠费
					
					
					String zenJianType = "";//优惠类型
					BigDecimal bgResult = new BigDecimal(0);
					//循环缴费类型去查询对应的缴费名单生成规则
					for(PageData pditemlist : pdItemList){
						pditemlist.put("T_PAY_ITEM_PKID", pditemlist.getString("PKID"));
						pditemlist.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
						pdInsert.put("T_PAY_ITEM_PKID", pditemlist.getString("PKID"));//优惠
						String isFlag = "";
						//查询对应的缴费规则
						//pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pditemlist);
						pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pditemlist);
						if(pdRule==null){
							pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pditemlist);
							isFlag = "X";
						}
						if(pdRule!=null){
							BigDecimal bgCost = new BigDecimal(pdRule.getString("COST"));//标准费用
							BigDecimal bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
							//优惠类型 0表示直减 1表示增加
							zenJianType = pdRule.getString("ZENGJIAN_TYPE");
							if("0".equals(zenJianType)){//0则相减	
								bgResult = bgCost.subtract(bgZJJinE);
								pdInsert.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 2);//优惠方式
							}else{//1则相加
								bgResult =  bgCost.add(bgZJJinE);
								pdInsert.put("DISCOUNT", "增加"+bgZJJinE);//优惠
								pdInsert.put("DISCOUNT_MODE", 3);//优惠方式
							}
							//优惠金额如果时0则不优惠
							if(bgZJJinE.equals(new BigDecimal(0))){
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
							}
							pdInsert.put("COST", bgResult);//费用
							pdInsert.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
							pdInsert.put("AMOUNTRECEIVABLE", bgResult);//应收金额
							pdInsert.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
							if("X".equals(isFlag)){
								pdInsert.put("COST", bgCost);//费用
								pdInsert.put("DISCOUNT_MONEY", 0);//优惠金额
								pdInsert.put("DISCOUNT", "不优惠");//优惠
								pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
								pdInsert.put("AMOUNTRECEIVABLE", bgCost);//应收金额
							}
							//新增缴费项
							dao.save("StuInfoMapper.insertItemList", pdInsert);
						}
						
						
					}
				}
				if(!Tools.isEmpty(pd.getString("YJ_BANJI_TYPE_PKID"))&&!Tools.isEmpty(pd.getString("YJ_NIANFEN"))){
					//根据入学年份和班型查询对应的缴费类型
					pd.put("GRADE_PKID",pd.getString("YJ_NIANFEN"));
					pd.put("CLASSTYPE_PKID", pd.getString("YJ_BANJI_TYPE_PKID"));
					pd.put("PAY_STYLE_NAMES", "PAY_STYLE_NAMES");
					pdItemList = (List<PageData>) dao.findForList("StuInfoMapper.getItemTypeList", pd);
					if(pdItemList!=null&&pdItemList.size()>0){
						PageData pdRule = null;//缴费规则结果
						PageData pdInsert = new PageData();//缴费名单结果实体
						pdInsert.put("STUDENT_PKID", pd.getString("PKID"));//学生pkid
						pdInsert.put("STUDENT_BM_PKID", pd.getString("TMPKID"));//学生副表pkid
						pdInsert.put("STATUS", 0);//状态默认欠费
						
						
						String zenJianType = "";//优惠类型
						BigDecimal bgResult = new BigDecimal(0);
						//循环缴费类型去查询对应的缴费名单生成规则
						for(PageData pditemlist : pdItemList){
							pditemlist.put("T_PAY_ITEM_PKID", pditemlist.getString("PKID"));
							pditemlist.put("STUDENT_BM_PKID", pd.getString("TMPKID"));
							pdInsert.put("T_PAY_ITEM_PKID", pditemlist.getString("PKID"));//优惠
							String isFlag = "";
							//查询对应的缴费规则
							//pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pditemlist);
							pdRule = (PageData) dao.findForObject("PayManageMapper.getRuleCost", pditemlist);
							if(pdRule==null){
								pdRule = (PageData) dao.findForObject("StuInfoMapper.getRuleCost", pditemlist);
								isFlag = "X";
							}
							if(pdRule!=null){
								BigDecimal bgCost = new BigDecimal(pdRule.getString("COST"));//标准费用
								BigDecimal bgZJJinE = new BigDecimal(pdRule.getString("ZENGJIAN_JINE"));//优惠金额
								//优惠类型 0表示直减 1表示增加
								zenJianType = pdRule.getString("ZENGJIAN_TYPE");
								if("0".equals(zenJianType)){//0则相减	
									bgResult = bgCost.subtract(bgZJJinE);
									pdInsert.put("DISCOUNT", "优惠"+bgZJJinE);//优惠
									pdInsert.put("DISCOUNT_MODE", 2);//优惠方式
								}else{//1则相加
									bgResult =  bgCost.add(bgZJJinE);
									pdInsert.put("DISCOUNT", "增加"+bgZJJinE);//优惠
									pdInsert.put("DISCOUNT_MODE", 3);//优惠方式
								}
								//优惠金额如果时0则不优惠
								if(bgZJJinE.equals(new BigDecimal(0))){
									pdInsert.put("DISCOUNT", "不优惠");//优惠
									pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
								}
								pdInsert.put("COST", bgResult);//费用
								pdInsert.put("DISCOUNT_MONEY", bgZJJinE);//优惠金额
								pdInsert.put("AMOUNTRECEIVABLE", bgResult);//应收金额
								pdInsert.put("PAY_ITEM_RULE", pdRule.getString("PKID"));//PAY_ITEM_RULE表PKID
								if("X".equals(isFlag)){
									pdInsert.put("COST", bgCost);//费用
									pdInsert.put("DISCOUNT_MONEY", 0);//优惠金额
									pdInsert.put("DISCOUNT", "不优惠");//优惠
									pdInsert.put("DISCOUNT_MODE", 0);//优惠方式
									pdInsert.put("AMOUNTRECEIVABLE", bgCost);//应收金额
								}
								//新增缴费项
								dao.save("StuInfoMapper.insertItemList", pdInsert);
							}
							
							
						}
					}
				}
			}
		}

		@Override
		public Boolean stuIsPaid(PageData pd) throws Exception {
			Integer counts=(Integer) dao.findForObject("StuInfoMapper.stuIsPaid", pd);
			return counts>0?true:false;
		}
		
		@Override
		public void saveTouxiangUrlToDB(PageData pd) throws Exception {
			dao.update("StuInfoMapper.saveTouxiangUrlToDB", pd);
		}
		
		@Override
		public void resetPwd(PageData pd) throws Exception {
			dao.update("StuInfoMapper.resetPwd", pd);
		}

		@Override
		public List<PageData> getLearnSubList(PageData pd) throws Exception {
			return (List<PageData>) dao.findForList("StuInfoMapper.getLearnSubList", pd);
		}

		@Override
		public PageData getSubById(PageData pd) throws Exception {
			// TODO Auto-generated method stub
			return (PageData) dao.findForObject("StuInfoMapper.getSubById", pd);
		}
		
		@Override
		public PageData getSubByName(PageData pd) throws Exception {
			// TODO Auto-generated method stub
			return (PageData) dao.findForObject("StuInfoMapper.getSubByName", pd);
		}

		@Override
		public PageData getStuBySfzRnBx(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getStuBySfzRnBx", pd);
		}
		
		@Override
		public PageData getStuBySfzNum(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getStuBySfzNum", pd);
		}

		@Override
		public PageData getStuinfoByXuehao(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getStuinfoByXuehao", pd);
		}

		@Override
		public PageData getStuinfoBySfz(PageData pd) throws Exception {
			return (PageData) dao.findForObject("StuInfoMapper.getStuinfoBySfz", pd);
		}

		@Override
		public void batchByOrFuXue(PageData pd) throws Exception {
			// TODO Auto-generated method stub
			dao.update("StuInfoMapper.batchByOrFuXue", pd);

		}

}
