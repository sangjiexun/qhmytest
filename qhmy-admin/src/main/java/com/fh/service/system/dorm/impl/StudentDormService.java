package com.fh.service.system.dorm.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.dorm.StudentDormManager;
import com.fh.service.system.seq.SeqManager;
import com.fh.service.system.stuinfo.StuInfoManager;
import com.fh.util.PageData;
import com.keman.zhgd.common.Tools;
import com.keman.zhgd.common.tree.VO;

@Service("studentDormService")
public class StudentDormService implements StudentDormManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public List<VO> getStudentDormTree(PageData pd) throws Exception {
		return (List<VO>) dao.findForList(
				"StudentDormMapper.getStudentDormTree", pd);
	}

	@Override
	public List<PageData> getStudentDormList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormList", pd);
	}

	@Override
	public List<VO> getSchoolsListTree(PageData pd) throws Exception {
		return (List<VO>) dao.findForList(
				"StudentDormMapper.getSchoolsListTree", pd);
	}

	@Override
	public PageData getStudentDormLevel(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormLevel", pd);
	}

	@Override
	public PageData getStudentDorm(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject("StudentDormMapper.getStudentDorm",
				pd);
	}

	@Override
	public void insertStudentDorm(PageData pd) throws Exception {
		if (Tools.notEmpty(pd.getString("ADD_DORM_TYPE"))
				&& "ADD_LOUCENG".equals(pd.getString("ADD_DORM_TYPE"))) {// 添加楼层
			Integer SD_NAME_LOUCENG_FROM = Integer.valueOf(pd
					.getString("SD_NAME_LOUCENG_FROM"));
			Integer SD_NAME_LOUCENG_TO = 0;
			// 需要添加楼层的数量
			Integer cengCount = 1;
			if (Tools.notEmpty(pd.getString("ADD_TYPE"))
					&& "1".equals(pd.getString("ADD_TYPE"))) {// 批量添加楼层，单个添加cengCount=1
				SD_NAME_LOUCENG_TO = Integer.valueOf(pd
						.getString("SD_NAME_LOUCENG_TO"));
				cengCount = SD_NAME_LOUCENG_TO - SD_NAME_LOUCENG_FROM + 1;
			}
			for (int i = 0; i < cengCount; i++) {
				if (SD_NAME_LOUCENG_FROM + i == 0) {
					continue;
				}
				String SD_NAME = (SD_NAME_LOUCENG_FROM + i) + "层";
				// 查询该层名称是否重复
				pd.put("SD_NAME", SD_NAME);
				PageData studentDormDB = (PageData) dao.findForObject(
						"StudentDormMapper.getStudentDormBySdName", pd);
				if (studentDormDB != null) {// 该父节点存在重复楼层
					throw new Exception("保存失败，添加了重复楼层！");
				} else {
					// 插入楼层
					PageData seqPd = (PageData) dao.findForObject(
							"SeqMapper.getNextSeqStudentDormBySeqName",
							SeqManager.SEQ_STUDENT_DORM);
					pd.put("PKID", "D" + String.valueOf(seqPd.get("RS")));
					dao.save("StudentDormMapper.insertStudentDorm", pd);
				}

			}
		} else if (Tools.notEmpty(pd.getString("ADD_DORM_TYPE"))
				&& "ADD_FANGJIAN".equals(pd.getString("ADD_DORM_TYPE"))) {// 添加房间

			String schoolPKID = "";
			// 通过楼层查询最顶级父节点，查询出学校的PKID
			PageData studentDormTopFirstDB = (PageData) dao.findForObject(
					"StudentDormMapper.getStudentDormTopFirst", pd);
			if (studentDormTopFirstDB != null) {
				schoolPKID = studentDormTopFirstDB.getString("SCHOOL_PKID");
			} else {
				throw new Exception("保存失败！");
			}

			Integer SD_NAME_XULIEHAO_FROM = Integer.valueOf(pd
					.getString("SD_NAME_XULIEHAO_FROM"));
			Integer SD_NAME_XULIEHAO_TO = 0;
			// 需要添加房间的数量
			Integer fangCount = 1;
			if (Tools.notEmpty(pd.getString("ADD_TYPE"))
					&& "1".equals(pd.getString("ADD_TYPE"))) {// 批量添加房间，单个添加fangCount=1
				SD_NAME_XULIEHAO_TO = Integer.valueOf(pd
						.getString("SD_NAME_XULIEHAO_TO"));
				fangCount = SD_NAME_XULIEHAO_TO - SD_NAME_XULIEHAO_FROM + 1;
			}
			for (int i = 0; i < fangCount; i++) {
				String SD_NAME = (SD_NAME_XULIEHAO_FROM + i) + "";

				/*
				 * 生成对应的房间名 start...
				 */
				// 查询该房间上一级楼层名
				String CENGNAME = "";
				PageData pdTemp = new PageData();
				pdTemp.put("pkid", pd.getString("PARENT_PKID"));
				PageData studentDormCENG = (PageData) dao.findForObject(
						"StudentDormMapper.getStudentDorm", pdTemp);
				if (studentDormCENG != null
						&& Tools.notEmpty(studentDormCENG.getString("SD_NAME"))) {
					try {
						CENGNAME = studentDormCENG.getString("SD_NAME").split(
								"层")[0];
					} catch (Exception e) {
						// TODO: handle exception
						throw new Exception("保存失败！");
					}

				} else {
					throw new Exception("保存失败！");
				}
				// 拼接房间名称
				if (SD_NAME.length() <= 0) {
					throw new Exception("保存失败！");
				} else if (SD_NAME.length() == 1) {
					SD_NAME = CENGNAME + "0" + SD_NAME;
				} else if (SD_NAME.length() >= 2) {
					SD_NAME = CENGNAME + SD_NAME;
				}
				/*
				 * 生成对应的房间名 end...
				 */

				pd.put("SD_NAME", SD_NAME);
				// 查询房间名称是否重复
				PageData studentDormDB = (PageData) dao.findForObject(
						"StudentDormMapper.getStudentDormBySdName", pd);
				if (studentDormDB != null) {// 该父节点存在重复房间
					throw new Exception("保存失败，添加了重复房间！");
				} else {
					PageData seqPd = (PageData) dao.findForObject(
							"SeqMapper.getNextSeqStudentDormBySeqName",
							SeqManager.SEQ_STUDENT_DORM);
					String PKID = "D" + String.valueOf(seqPd.get("RS"));
					pd.put("PKID", PKID);

					// 增加房间
					dao.save("StudentDormMapper.insertStudentDorm", pd);

					if (Tools.notEmpty(pd.getString("CHUANGCOUNT"))) {
						// 最大床号
						Integer maxChuang = 1;
						PageData studentDormMaxCHUANG = (PageData) dao
								.findForObject(
										"StudentDormMapper.getStudentDormMaxCHUANG",
										pd);
						if (studentDormMaxCHUANG != null
								&& Tools.notEmpty(studentDormMaxCHUANG
										.getString("SD_NAME"))) {
							maxChuang = Integer.valueOf(studentDormMaxCHUANG
									.getString("SD_NAME"));
						}

						// 增加床
						Integer CHUANGCOUNT = Integer.valueOf(pd
								.getString("CHUANGCOUNT"));
						for (int j = 0; j < CHUANGCOUNT; j++) {
							/*
							 * 宿舍表添加床
							 */
							PageData seqPdd = (PageData) dao.findForObject(
									"SeqMapper.getNextSeqStudentDormBySeqName",
									SeqManager.SEQ_STUDENT_DORM);
							String chuangPKID = "D"
									+ String.valueOf(seqPdd.get("RS"));
							PageData chuangPd = new PageData();
							chuangPd.put("PKID", chuangPKID);
							chuangPd.put("PARENT_PKID", PKID);
							chuangPd.put("T_STUDENT_DORM_TYPE_PKID",
									pd.getString("T_STUDENT_DORM_TYPE_PKID"));
							chuangPd.put("SYS_DICTIONARIES_PKID",
									pd.getString("SYS_DICTIONARIES_PKID"));
							chuangPd.put("T_STUDENT_PKID",
									pd.getString("T_STUDENT_PKID"));
							chuangPd.put("SD_NAME", (maxChuang + j) + "");
							chuangPd.put("SD_LEVEL", "5");
							chuangPd.put("STATUS", "0");
							chuangPd.put("SD_SEX", pd.getString("SD_SEX"));
							dao.save("StudentDormMapper.insertStudentDorm",
									chuangPd);
							/*
							 * 2018-06-06 解决性能问题 ： 插入床的所属树结构，逗号分隔
							 */
							PageData pd_chuang = (PageData) dao.findForObject(
									"StudentDormMapper.getDormTreeNodes",
									chuangPd);
							pd_chuang.put("PKID", chuangPKID);
							dao.update("StudentDormMapper.updateDormTreeNodes",
									pd_chuang);
							/*
							 * end
							 */

							/*
							 * 宿舍与院校专业关系表添加床
							 */
							/*
							 * PageData dormDepartment = new PageData();
							 * PageData seqPd_DD = (PageData)dao.findForObject(
							 * "SeqMapper.getNextSeqDormDepartmentBySeqName",
							 * SeqManager.SEQ_DORM_DEPARTMENT);
							 * dormDepartment.put("PKID",
							 * String.valueOf(seqPd_DD.get("RS")));
							 * dormDepartment.put("DORM_ID", chuangPKID);
							 * dormDepartment.put("DEPARTMENT_ID", schoolPKID);
							 * dormDepartment.put("ALLOT_TYPE", "0");
							 * dao.save("StudentDormMapper.insertDormDepartment"
							 * , dormDepartment);
							 */

							/*
							 * 更新宿舍资源表
							 */

							/*
							 * if(Tools.notEmpty(pd.getString("SD_SEX"))){
							 * PageData pdRR = new PageData();
							 * pdRR.put("T_STUDENT_DORM_TYPE_PKID",
							 * pd.getString("T_STUDENT_DORM_TYPE_PKID"));
							 * if("0".equals(pd.getString("SD_SEX"))){
							 * pdRR.put("SEX_TYPE", "W"); }else
							 * if("1".equals(pd.getString("SD_SEX"))){
							 * pdRR.put("SEX_TYPE", "M"); }
							 * 
							 * //系统配置中，宿舍资源分配到哪个节点（宿舍限制范围（学校、学院、系、专业）） PageData
							 * dormRestrictedareaPd =
							 * (PageData)dao.findForObject
							 * ("StudentDormMapper.getDormRestrictedarea", pd);
							 * if("专业".equals(dormRestrictedareaPd.getString(
							 * "AREATYPE"))){ pdRR.put("DEPARTMENT_PKID",
							 * schoolPKID); }else{ pdRR.put("DEPARTMENT_PKID",
							 * ""); }
							 * 
							 * PageData roomResource =
							 * (PageData)dao.findForObject
							 * ("StudentDormMapper.getRoomResourceLock", pdRR);
							 * if(roomResource != null){//更新,总床位+1
							 * roomResource.put("ROOM_SUM_NUM",
							 * (Integer.valueOf(
							 * roomResource.getString("ROOM_SUM_NUM"
							 * )).intValue() + 1) + "");
							 * dao.update("StudentDormMapper.updateRoomResourceSum"
							 * , roomResource); }else{//插入
							 * pdRR.put("ROOM_SUM_NUM", "1");
							 * pdRR.put("ROOM_ALREADY_USED", "0");
							 * pdRR.put("ROOM_ALREADY_ORDER", "0");
							 * dao.save("StudentDormMapper.insertRoomResource",
							 * pdRR); } }else{ throw new Exception("保存失败！"); }
							 */

						}

					}

				}
			}
		} else {
			// 替换掉英文“-”，避免排序报错
			if (Tools.notEmpty(pd.getString("SD_NAME"))
					&& pd.getString("SD_NAME").contains("-")) {
				pd.put("SD_NAME", pd.getString("SD_NAME").replaceAll("-", "－"));
			}
			dao.save("StudentDormMapper.insertStudentDorm", pd);
		}

	}

	@Override
	public PageData getStudentDormBySdName(PageData pd) throws Exception {
		return (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormBySdName", pd);
	}

	@Override
	public List<PageData> getStudentDormTypeList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormTypeList", pd);
	}

	@Override
	public void updateStudentDorm(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		// 查询名称是否重复
		PageData studentDormDB = (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormBySdName", pd);
		if (studentDormDB != null
				&& !studentDormDB.getString("PKID")
						.equals(pd.getString("PKID"))) {
			throw new Exception("该名称已存在！");
		} else {
			// {SD_NAME=, ADD_DORM_TYPE=ADD_FANGJIAN, PARENT_PKID=D793,
			// SD_SEX=1, PKID=D913, SD_PREFIX=a, ACTION=edit}
			PageData sDormDB = (PageData) dao.findForObject(
					"StudentDormMapper.getStudentDormByPkid", pd);
			// 如果修改的是房间的话
			if ("4".equals(sDormDB.getString("SD_LEVEL"))) {
				// 如果修改性别的话
				if (!sDormDB.getString("SD_SEX").equals(pd.getString("SD_SEX"))) {
					String schoolPKID = "";
					// 通过楼层查询最顶级父节点，查询出学校的PKID
					PageData studentDormTopFirstDB = (PageData) dao
							.findForObject(
									"StudentDormMapper.getStudentDormTopFirst",
									pd);
					if (studentDormTopFirstDB != null) {
						schoolPKID = studentDormTopFirstDB
								.getString("SCHOOL_PKID");
					} else {
						throw new Exception("修改失败！");
					}

					// 查询该房间下所有的床
					PageData pdTemp = new PageData();
					pdTemp.put("pkid", pd.getString("PKID"));
					List<PageData> chuangList = (List<PageData>) dao
							.findForList(
									"StudentDormMapper.getChuangListByParentPkid",
									pdTemp);
					if (chuangList != null && chuangList.size() > 0) {
						/*
						 * 是否有床被占用
						 */
						boolean isUse = false;
						for (PageData chuang : chuangList) {
							if ("1".equals(chuang.getString("STATUS"))) {
								isUse = true;
								break;
							}
						}
						if (isUse) {
							throw new Exception("该房间下已有房间被占用，禁止更换性别！");
						}

						/*
						 * 修改房间下的床的性别
						 */
						PageData pdTemp2 = new PageData();
						pdTemp2.put("PARENT_PKID", pd.getString("PKID"));
						pdTemp2.put("SD_SEX", pd.getString("SD_SEX"));
						dao.update(
								"StudentDormMapper.updateStudentDormSexByParentPkid",
								pdTemp2);

						/*
						 * ======================================================
						 * ==
						 * ====================================================
						 * ================= 以下为修改宿舍资源数量 start...
						 * ================
						 * ======================================
						 * ================
						 * ======================================
						 * =================
						 */
						/*
						 * PageData resourcePd = new PageData(); //更新宿舍资源表，床数减少
						 * int count = chuangList.size();
						 * //每个房间的床的性别宿舍类型和性别都一样，所以chuangList.get(0)
						 * resourcePd.put("T_STUDENT_DORM_TYPE_PKID",
						 * chuangList.
						 * get(0).getString("T_STUDENT_DORM_TYPE_PKID"));
						 * 
						 * 减少原有性别宿舍资源
						 * 
						 * //系统配置中，宿舍资源分配到哪个节点（宿舍限制范围（学校、学院、系、专业）） PageData
						 * dormRestrictedareaPd = (PageData)dao.findForObject(
						 * "StudentDormMapper.getDormRestrictedarea", pd); {
						 * 
						 * if("0".equals(chuangList.get(0).getString("SD_SEX"))){
						 * resourcePd.put("SEX_TYPE", "W"); }else
						 * if("1".equals(chuangList
						 * .get(0).getString("SD_SEX"))){
						 * resourcePd.put("SEX_TYPE", "M"); } //学校PKID
						 * 
						 * if("专业".equals(dormRestrictedareaPd.getString("AREATYPE"
						 * ))){ resourcePd.put("DEPARTMENT_PKID", schoolPKID);
						 * }else{ resourcePd.put("DEPARTMENT_PKID", ""); }
						 * //查询宿舍资源 PageData roomResource =
						 * (PageData)dao.findForObject
						 * ("StudentDormMapper.getRoomResourceLock",
						 * resourcePd); if(roomResource != null){
						 * //如果修改后宿舍资源床总数小于预定数
						 * if(Integer.valueOf(roomResource.getString
						 * ("ROOM_SUM_NUM")).intValue() - count >=
						 * Integer.valueOf
						 * (roomResource.getString("ROOM_ALREADY_ORDER"))){
						 * roomResource.put("ROOM_SUM_NUM",
						 * (Integer.valueOf(roomResource
						 * .getString("ROOM_SUM_NUM")).intValue() - count) +
						 * "");
						 * dao.update("StudentDormMapper.updateRoomResourceSum",
						 * roomResource); }else{ throw new
						 * Exception("已存在已预定的床位，禁止更换性别！"); }
						 * 
						 * }else{ throw new Exception("修改失败！"); }
						 * 
						 * }
						 */

						/*
						 * 增加修改后的性别的宿舍资源
						 */
						/*
						 * { if("0".equals(pd.getString("SD_SEX"))){
						 * pd.put("SEX_TYPE", "W"); }else
						 * if("1".equals(pd.getString("SD_SEX"))){
						 * pd.put("SEX_TYPE", "M"); } //学校PKID
						 * if("专业".equals(dormRestrictedareaPd
						 * .getString("AREATYPE"))){
						 * resourcePd.put("DEPARTMENT_PKID", schoolPKID); }else{
						 * resourcePd.put("DEPARTMENT_PKID", ""); } //查询宿舍资源
						 * PageData roomResource = (PageData)dao.findForObject(
						 * "StudentDormMapper.getRoomResourceLock", resourcePd);
						 * if(roomResource != null){
						 * roomResource.put("ROOM_SUM_NUM",
						 * (Integer.valueOf(roomResource
						 * .getString("ROOM_SUM_NUM")).intValue() + count) +
						 * "");
						 * dao.update("StudentDormMapper.updateRoomResourceSum",
						 * roomResource); }else{ throw new Exception("修改失败！"); }
						 * }
						 */

						/*
						 * ======================================================
						 * ==
						 * ====================================================
						 * ================= 以上为修改宿舍资源数量 end...
						 * ==================
						 * ====================================
						 * ==================
						 * =====================================================
						 */
					}

				}
			} else {
				// 替换掉英文“-”，避免排序报错
				if (Tools.notEmpty(pd.getString("SD_NAME"))
						&& pd.getString("SD_NAME").contains("-")) {
					pd.put("SD_NAME",
							pd.getString("SD_NAME").replaceAll("-", "－"));
				}
			}
			dao.update("StudentDormMapper.updateStudentDorm", pd);
		}

	}

	@Override
	public List<PageData> getInUseChuangListByParentPkid(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getInUseChuangListByParentPkid", pd);
	}

	@Override
	public void deleteStudentDorm(PageData pd) throws Exception {
		// TODO Auto-generated method stub

		/*
		 * 方案2
		 * 级联删除==============================================================
		 * ====
		 * ==================================================================
		 * ========
		 */
		PageData inUseCount = (PageData) dao.findForObject(
				"StudentDormMapper.getInUseStudentDormCountCascade", pd);
		if ("0".equals(inUseCount.getString("CCOUNT"))) {
			List<PageData> studentDormList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getStudentDormListCascade", pd);
			if (studentDormList != null && studentDormList.size() > 0) {
				String schoolPKID = "";
				// 通过楼层查询最顶级父节点，查询出学校的PKID
				pd.put("PARENT_PKID", pd.getString("pkid"));
				PageData studentDormTopFirstDB = (PageData) dao.findForObject(
						"StudentDormMapper.getStudentDormTopFirst", pd);
				if (studentDormTopFirstDB != null) {
					schoolPKID = studentDormTopFirstDB.getString("SCHOOL_PKID");
				} else {
					throw new Exception("删除失败！");
				}
				/*
				 * 循环删除宿舍资源
				 */
				for (PageData studentDorm : studentDormList) {
					// 床
					/*
					 * if("5".equals(studentDorm.getString("SD_LEVEL"))){
					 * studentDorm.put("DORM_ID",
					 * studentDorm.getString("PKID"));
					 * 
					 * 
					 * 更新宿舍资源表，床数减少
					 * 
					 * int count = 1; //每个房间的床的性别宿舍类型和性别都一样
					 * studentDorm.put("T_STUDENT_DORM_TYPE_PKID",
					 * studentDorm.getString("T_STUDENT_DORM_TYPE_PKID"));
					 * if("0".equals(studentDorm.getString("SD_SEX"))){
					 * studentDorm.put("SEX_TYPE", "W"); }else
					 * if("1".equals(studentDorm.getString("SD_SEX"))){
					 * studentDorm.put("SEX_TYPE", "M"); }
					 * 
					 * 
					 * 更新宿舍资源
					 * 
					 * PageData studentDormDepartment =
					 * (PageData)dao.findForObject
					 * ("StudentDormMapper.getDormDepartment", studentDorm);
					 * if(studentDormDepartment != null){
					 * //系统配置中，宿舍资源分配到哪个节点（宿舍限制范围（学校、学院、系、专业）） PageData
					 * dormRestrictedareaPd = (PageData)dao.findForObject(
					 * "StudentDormMapper.getDormRestrictedarea", pd);
					 * if("专业".equals
					 * (dormRestrictedareaPd.getString("AREATYPE"))){
					 * studentDorm.put("DEPARTMENT_PKID",
					 * studentDormDepartment.getString("DEPARTMENT_ID")); }else{
					 * studentDorm.put("DEPARTMENT_PKID", ""); }
					 * 
					 * //查询宿舍资源，这样循环锁表不好，那又如何 ,哇咔咔^-^ PageData roomResource =
					 * (PageData
					 * )dao.findForObject("StudentDormMapper.getRoomResourceLock"
					 * , studentDorm); if(roomResource != null){
					 * //如果删除后宿舍资源床总数小于预定数
					 * if(Integer.valueOf(roomResource.getString
					 * ("ROOM_SUM_NUM")).intValue() - count >=
					 * Integer.valueOf(roomResource
					 * .getString("ROOM_ALREADY_ORDER"))){
					 * roomResource.put("ROOM_SUM_NUM",
					 * (Integer.valueOf(roomResource
					 * .getString("ROOM_SUM_NUM")).intValue() - count) + "");
					 * dao.update("StudentDormMapper.updateRoomResourceSum",
					 * roomResource); }else
					 * if(!"0".equals(roomResource.getString("ROOM_SUM_NUM"))){
					 * throw new Exception("已存在已预定的床位，禁止删除！"); }
					 * 
					 * }else{ throw new Exception("删除失败！"); }
					 * 
					 * }else{ throw new Exception("删除失败！"); }
					 * 
					 * 
					 * 删除宿舍与院校专业表床
					 * 
					 * dao.delete("StudentDormMapper.deleteDormDepartment",
					 * studentDorm);//DORM_ID
					 * 
					 * }
					 */
					studentDorm.put("pkid", studentDorm.getString("PKID"));
					dao.delete("StudentDormMapper.deleteStudentDorm",
							studentDorm);
				}
			} else {
				throw new Exception("该宿舍资源已被删除，请重新刷新页面！");
			}

		} else {
			throw new Exception("存在已占用的床位，禁止删除！");
		}

		/*
		 * 方案2
		 * 级联删除==============================================================
		 * ====
		 * ==================================================================
		 * ========
		 */

		/*
		 * 方案1
		 * 不级联删除==============================================================
		 * ====
		 * ==================================================================
		 * ========
		 * 
		 * //删除房间的时候删除床 if("4".equals(pd.getString("SD_LEVEL"))){
		 * 
		 * //查询该房间下所有的床 List<PageData> chuangList =
		 * (List<PageData>)dao.findForList
		 * ("StudentDormMapper.getChuangListByParentPkid", pd); if(chuangList !=
		 * null && chuangList.size() > 0){ for (int i = 0; i <
		 * chuangList.size(); i++) { PageData chuangPd = chuangList.get(i);
		 * //删除宿舍与院校专业表床 pd.put("DORM_ID", chuangPd.getString("PKID"));
		 * dao.delete("StudentDormMapper.deleteDormDepartment", pd);//DORM_ID }
		 * 
		 * //更新宿舍资源表，床数减少 int count = chuangList.size();
		 * //每个房间的床的性别宿舍类型和性别都一样，所以chuangList.get(0)
		 * pd.put("T_STUDENT_DORM_TYPE_PKID",
		 * chuangList.get(0).getString("T_STUDENT_DORM_TYPE_PKID"));
		 * if("0".equals(chuangList.get(0).getString("SD_SEX"))){
		 * pd.put("SEX_TYPE", "W"); }else
		 * if("1".equals(chuangList.get(0).getString("SD_SEX"))){
		 * pd.put("SEX_TYPE", "M"); } //查询宿舍资源 PageData roomResource =
		 * (PageData)dao.findForObject("StudentDormMapper.getRoomResourceLock",
		 * pd); if(roomResource != null){ //如果删除后宿舍资源床总数小于预定数
		 * if(Integer.valueOf(roomResource.getString("ROOM_SUM_NUM")).intValue()
		 * - count >=
		 * Integer.valueOf(roomResource.getString("ROOM_ALREADY_ORDER"))){
		 * roomResource.put("ROOM_SUM_NUM",
		 * (Integer.valueOf(roomResource.getString("ROOM_SUM_NUM")).intValue() -
		 * count) + ""); dao.update("StudentDormMapper.updateRoomResourceSum",
		 * roomResource); }else{ throw new Exception("已存在已预定的床位，禁止删除！"); }
		 * 
		 * }else{ throw new Exception("删除失败！"); }
		 * 
		 * }
		 * 
		 * //删除宿舍表床 dao.delete("StudentDormMapper.deleteStudentDormChild", pd);
		 * }
		 * 
		 * // dao.delete("StudentDormMapper.deleteStudentDorm", pd);
		 * 
		 * 
		 * 方案1
		 * 不级联删除==============================================================
		 * ====
		 * ==================================================================
		 * ========
		 */
	}

	@Override
	public PageData getLastStudentDormByPkid(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject(
				"StudentDormMapper.getLastStudentDormByPkid", pd);
	}

	@Override
	public List<VO> getStudentDormPlanTree(PageData pd) throws Exception {
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanTree3", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			if (pda != null) {
				String[] ss = pda.getString("TREE_NODES").split(",");
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
			}

		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		if (list_nodes.size() > 0) {
			return (List<VO>) dao.findForList(
					"StudentDormMapper.getStudentDormPlanTree", list_nodes);
		} else {
			return null;
		}

	}

	@Override
	public List<VO> getSchoolsListPlanTree(PageData pd) throws Exception {
		return (List<VO>) dao.findForList(
				"StudentDormMapper.getSchoolsListPlanTree", pd);
	}

	@Override
	public List<PageData> getRuxuenianfenList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getRuxuenianfenList", pd);
	}

	@Override
	public List<VO> getDepartmentPlanTree(PageData pd) throws Exception {
		return (List<VO>) dao.findForList(
				"StudentDormMapper.getDepartmentPlanTree", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStudentDormPlanList(PageData pd) throws Exception {
		List<PageData> studentDormListResult = null;
		if (Tools.notEmpty(pd.getString("dormLevel"))
				&& "4".equals(pd.getString("dormLevel"))) {// 点击房间，显示床
			pd.put("pkid2", pd.getString("pkid"));
			studentDormListResult = (List<PageData>) dao.findForList(
					"StudentDormMapper.getStudentDormPlanChuangList", pd);
		} else {

			studentDormListResult = new ArrayList<>();
			/*
			 * 查询被点击节点下拥有权限的所有的床
			 */
			pd.put("pkid2", pd.getString("pkid"));
			List<PageData> studentDormList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getStudentDormPlanChuangList", pd);

			List<VO> studentDormVOList = (List<VO>) dao.findForList(
					"StudentDormMapper.getStudentDormPlanTree2", null);
			for (VO dormVo : studentDormVOList) {// 点击某一节点下的卡片集合
				if (dormVo.getParentId().equals(pd.getString("pkid"))) {

					int chuangCount = 0;
					int chuangCount_ruzhu = 0;
					int nanCount = 0;
					int nvCount = 0;
					int nanCount_ruzhu = 0;
					int nvCount_ruzhu = 0;

					Set<String> louSet = new HashSet<String>();
					Set<String> cengSet = new HashSet<String>();
					Set<String> fangSet = new HashSet<String>();

					Map<String, Integer> nanDormTypeMap = new HashMap();

					Map<String, String> nanDormTypeFangMap = new HashMap();
					Map<String, String> nvDormTypeFangMap = new HashMap();

					Map<String, Integer> nvDormTypeMap = new HashMap();
					Map<String, Integer> nanDormTypeRuzhuMap = new HashMap();
					Map<String, Integer> nvDormTypeRuzhuMap = new HashMap();

					for (PageData dorm : studentDormList) {
						// TREE_NODES一维数组，0学校，1校区，2楼，3楼层，4房间，5床
						String[] TREE_NODES = dorm.getString("TREE_NODES")
								.split(",");
						String dormPkid = dormVo.getId();
						if ("3".equals(dormVo.getIsXM())) {// 如果前端选择的是房
							dormPkid = dormVo.getId();
						}
						if (dorm.getString("TREE_NODES").indexOf(dormPkid) > 0) {
							louSet.add(TREE_NODES[2]);
							cengSet.add(TREE_NODES[3]);
							fangSet.add(TREE_NODES[4]);

							String nanDormType = "男" + "-"
									+ dorm.getString("DT_NAME") + "-";
							String nvDormType = "女" + "-"
									+ dorm.getString("DT_NAME") + "-";

							if ("1".equals(dorm.getString("STATUS"))) {// 入住
								chuangCount_ruzhu++;
								if ("1".equals(dorm.getString("SD_SEX"))) {// 男
									nanCount_ruzhu++;

									// 获得男-四人间床数
									if (!nanDormTypeRuzhuMap
											.containsKey(nanDormType)) {
										nanDormTypeRuzhuMap.put(nanDormType, 1);
									} else {
										int nanDormTypeChuangRuzhuCount = ((Integer) nanDormTypeRuzhuMap
												.get(nanDormType)).intValue() + 1;
										nanDormTypeRuzhuMap.put(nanDormType,
												nanDormTypeChuangRuzhuCount);
									}

								} else {// 女
									nvCount_ruzhu++;

									// 获得女-四人间床数
									if (!nvDormTypeRuzhuMap
											.containsKey(nvDormType)) {
										nvDormTypeRuzhuMap.put(nvDormType, 1);
									} else {
										int nvDormTypeChuangRuzhuCount = ((Integer) nvDormTypeRuzhuMap
												.get(nvDormType)).intValue() + 1;
										nvDormTypeRuzhuMap.put(nvDormType,
												nvDormTypeChuangRuzhuCount);
									}

								}
							}
							chuangCount++;

							// 统计男女
							if ("1".equals(dorm.getString("SD_SEX"))) {// 男
								nanCount++;

								// 获得男-四人间房间数
								if (!nanDormTypeFangMap
										.containsKey(nanDormType)) {
									nanDormTypeFangMap.put(nanDormType,
											TREE_NODES[4] + ",");
								} else {
									String nanDormTypeFangRuzhuStrs = (nanDormTypeFangMap
											.get(nanDormType).contains(
													TREE_NODES[4] + ",") ? nanDormTypeFangMap
											.get(nanDormType)
											: nanDormTypeFangMap
													.get(nanDormType)
													+ TREE_NODES[4] + ",");
									nanDormTypeFangMap.put(nanDormType,
											nanDormTypeFangRuzhuStrs);
								}

								// 统计男女房间类型床总数
								if (!nanDormTypeMap.containsKey(nanDormType)) {
									nanDormTypeMap.put(nanDormType, 1);
								} else {
									int nanDormTypeChuangCount = ((Integer) nanDormTypeMap
											.get(nanDormType)).intValue() + 1;
									nanDormTypeMap.put(nanDormType,
											nanDormTypeChuangCount);
								}
							} else {// 女
								nvCount++;

								// 获得女-四人间房间数
								if (!nvDormTypeFangMap.containsKey(nvDormType)) {
									nvDormTypeFangMap.put(nvDormType,
											TREE_NODES[4] + ",");
								} else {
									String nvDormTypeFangRuzhuStrs = (nvDormTypeFangMap
											.get(nvDormType).contains(
													TREE_NODES[4] + ",") ? nvDormTypeFangMap
											.get(nvDormType)
											: nvDormTypeFangMap.get(nvDormType)
													+ TREE_NODES[4] + ",");
									nvDormTypeFangMap.put(nvDormType,
											nvDormTypeFangRuzhuStrs);
								}

								// 统计男女房间类型床总数
								if (!nvDormTypeMap.containsKey(nvDormType)) {
									nvDormTypeMap.put(nvDormType, 1);
								} else {
									int nvDormTypeChuangCount = ((Integer) nvDormTypeMap
											.get(nvDormType)).intValue() + 1;
									nvDormTypeMap.put(nvDormType,
											nvDormTypeChuangCount);
								}
							}

						}

					}
					if (chuangCount > 0) {
						PageData sdorm = new PageData();
						sdorm.put("PKID", dormVo.getId());
						sdorm.put("PARENT_PKID", dormVo.getParentId());
						sdorm.put("SD_NAME", dormVo.getName());
						sdorm.put("SD_LEVEL", dormVo.getIsXM());

						sdorm.put("LOUCOUNT", louSet.size());
						sdorm.put("CENGCOUNT", cengSet.size());
						sdorm.put("FANGCOUNT", fangSet.size());
						sdorm.put("CHUANGCOUNT", chuangCount);
						sdorm.put("CHUANGCOUNT_RUZHU", chuangCount_ruzhu);

						sdorm.put("NANCOUNT", nanCount);
						sdorm.put("NVCOUNT", nvCount);
						sdorm.put("NANCOUNT_RUZHU", nanCount_ruzhu);
						sdorm.put("NVCOUNT_RUZHU", nvCount_ruzhu);

						// 男的hover框
						StringBuffer nanHoverStrSB = new StringBuffer();
						if (!nanDormTypeMap.isEmpty()) {
							for (String key : nanDormTypeMap.keySet()) {
								int nanDormTypeCount = nanDormTypeMap.get(key)
										.intValue();
								// 男-四人间房间数
								int nanTypeFangCount = 0;
								if (Tools.notEmpty(nanDormTypeFangMap.get(key))) {
									String nanTypeFangValue = nanDormTypeFangMap
											.get(key).toString();
									nanTypeFangCount = nanTypeFangValue
											.split(",").length;
								}

								int nanTypeFangRuzhuCount = nanDormTypeRuzhuMap
										.get(key) != null ? nanDormTypeRuzhuMap
										.get(key).intValue() : 0;
								nanHoverStrSB.append(key + nanTypeFangCount
										+ "间-" + nanTypeFangRuzhuCount + "/"
										+ nanDormTypeCount + "&#10;");
							}
						}
						sdorm.put("NANHOVERSTR", nanHoverStrSB.toString());

						// 女的hover框
						StringBuffer nvHoverStrSB = new StringBuffer();
						if (!nvDormTypeMap.isEmpty()) {
							for (String key : nvDormTypeMap.keySet()) {
								int nvDormTypeCount = nvDormTypeMap.get(key)
										.intValue();
								// 男-四人间房间数
								int nvTypeFangCount = 0;
								if (Tools.notEmpty(nvDormTypeFangMap.get(key))) {
									String nvTypeFangValue = nvDormTypeFangMap
											.get(key).toString();
									nvTypeFangCount = nvTypeFangValue
											.split(",").length;
								}

								int nvTypeFangRuzhuCount = nvDormTypeRuzhuMap
										.get(key) != null ? nvDormTypeRuzhuMap
										.get(key).intValue() : 0;
								nvHoverStrSB.append(key + nvTypeFangCount
										+ "间-" + nvTypeFangRuzhuCount + "/"
										+ nvDormTypeCount + "&#10;");
							}
						}
						sdorm.put("NVHOVERSTR", nvHoverStrSB.toString());

						studentDormListResult.add(sdorm);
					}

				}

			}

		}
		return studentDormListResult;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStudentDormPlanlistPage(Page page)
			throws Exception {
		List<PageData> studentDormListResult = new ArrayList<>();
		PageData pd = page.getPd();
		/*
		 * 查询被点击节点下拥有权限的所有的床
		 */
		pd.put("pkid2", pd.getString("pkid"));
		List<PageData> studentDormList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanChuangList", pd);

		// 查询该用户拥有的宿舍信息
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanTree3", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			String[] ss = pda.getString("TREE_NODES").split(",");
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		// 过滤查询该用户拥有的宿舍信息
		pd.put("list_nodes", list_nodes);
		if (list_nodes.size() > 0) {
			List<VO> studentDormVOList = (List<VO>) dao.findForList(
					"StudentDormMapper.getStudentDormPlanTree2listPage", page);
			for (VO dormVo : studentDormVOList) {// 点击某一节点下的卡片集合
				if (dormVo.getParentId().equals(pd.getString("pkid"))) {

					int chuangCount = 0;
					int chuangCount_ruzhu = 0;
					int nanCount = 0;
					int nvCount = 0;
					int nanCount_ruzhu = 0;
					int nvCount_ruzhu = 0;

					Set<String> louSet = new HashSet<String>();
					Set<String> cengSet = new HashSet<String>();
					Set<String> fangSet = new HashSet<String>();

					Map<String, Integer> nanDormTypeMap = new HashMap();

					Map<String, String> nanDormTypeFangMap = new HashMap();
					Map<String, String> nvDormTypeFangMap = new HashMap();

					Map<String, Integer> nvDormTypeMap = new HashMap();
					Map<String, Integer> nanDormTypeRuzhuMap = new HashMap();
					Map<String, Integer> nvDormTypeRuzhuMap = new HashMap();

					for (PageData dorm : studentDormList) {
						// TREE_NODES一维数组，0学校，1校区，2楼，3楼层，4房间，5床
						String[] TREE_NODES = dorm.getString("TREE_NODES")
								.split(",");
						String dormPkid = dormVo.getId();
						if ("3".equals(dormVo.getIsXM())) {// 如果前端选择的是房
							dormPkid = dormVo.getId();
						}
						if (dorm.getString("TREE_NODES").indexOf(dormPkid) > 0) {
							louSet.add(TREE_NODES[2]);
							cengSet.add(TREE_NODES[3]);
							fangSet.add(TREE_NODES[4]);

							String nanDormType = "男" + "-"
									+ dorm.getString("DT_NAME") + "-";
							String nvDormType = "女" + "-"
									+ dorm.getString("DT_NAME") + "-";

							if ("1".equals(dorm.getString("STATUS"))) {// 入住
								chuangCount_ruzhu++;
								if ("1".equals(dorm.getString("SD_SEX"))) {// 男
									nanCount_ruzhu++;

									// 获得男-四人间床数
									if (!nanDormTypeRuzhuMap
											.containsKey(nanDormType)) {
										nanDormTypeRuzhuMap.put(nanDormType, 1);
									} else {
										int nanDormTypeChuangRuzhuCount = ((Integer) nanDormTypeRuzhuMap
												.get(nanDormType)).intValue() + 1;
										nanDormTypeRuzhuMap.put(nanDormType,
												nanDormTypeChuangRuzhuCount);
									}

								} else {// 女
									nvCount_ruzhu++;

									// 获得女-四人间床数
									if (!nvDormTypeRuzhuMap
											.containsKey(nvDormType)) {
										nvDormTypeRuzhuMap.put(nvDormType, 1);
									} else {
										int nvDormTypeChuangRuzhuCount = ((Integer) nvDormTypeRuzhuMap
												.get(nvDormType)).intValue() + 1;
										nvDormTypeRuzhuMap.put(nvDormType,
												nvDormTypeChuangRuzhuCount);
									}

								}
							}
							chuangCount++;

							// 统计男女
							if ("1".equals(dorm.getString("SD_SEX"))) {// 男
								nanCount++;

								// 获得男-四人间房间数
								if (!nanDormTypeFangMap
										.containsKey(nanDormType)) {
									nanDormTypeFangMap.put(nanDormType,
											TREE_NODES[4] + ",");
								} else {
									String nanDormTypeFangRuzhuStrs = (nanDormTypeFangMap
											.get(nanDormType).contains(
													TREE_NODES[4] + ",") ? nanDormTypeFangMap
											.get(nanDormType)
											: nanDormTypeFangMap
													.get(nanDormType)
													+ TREE_NODES[4] + ",");
									nanDormTypeFangMap.put(nanDormType,
											nanDormTypeFangRuzhuStrs);
								}

								// 统计男女房间类型床总数
								if (!nanDormTypeMap.containsKey(nanDormType)) {
									nanDormTypeMap.put(nanDormType, 1);
								} else {
									int nanDormTypeChuangCount = ((Integer) nanDormTypeMap
											.get(nanDormType)).intValue() + 1;
									nanDormTypeMap.put(nanDormType,
											nanDormTypeChuangCount);
								}
							} else {// 女
								nvCount++;

								// 获得女-四人间房间数
								if (!nvDormTypeFangMap.containsKey(nvDormType)) {
									nvDormTypeFangMap.put(nvDormType,
											TREE_NODES[4] + ",");
								} else {
									String nvDormTypeFangRuzhuStrs = (nvDormTypeFangMap
											.get(nvDormType).contains(
													TREE_NODES[4] + ",") ? nvDormTypeFangMap
											.get(nvDormType)
											: nvDormTypeFangMap.get(nvDormType)
													+ TREE_NODES[4] + ",");
									nvDormTypeFangMap.put(nvDormType,
											nvDormTypeFangRuzhuStrs);
								}

								// 统计男女房间类型床总数
								if (!nvDormTypeMap.containsKey(nvDormType)) {
									nvDormTypeMap.put(nvDormType, 1);
								} else {
									int nvDormTypeChuangCount = ((Integer) nvDormTypeMap
											.get(nvDormType)).intValue() + 1;
									nvDormTypeMap.put(nvDormType,
											nvDormTypeChuangCount);
								}
							}

						}

					}
					if (chuangCount > 0) {
						PageData sdorm = new PageData();
						sdorm.put("PKID", dormVo.getId());
						sdorm.put("PARENT_PKID", dormVo.getParentId());
						sdorm.put("SD_NAME", dormVo.getName());
						sdorm.put("SD_LEVEL", dormVo.getIsXM());

						sdorm.put("LOUCOUNT", louSet.size());
						sdorm.put("CENGCOUNT", cengSet.size());
						sdorm.put("FANGCOUNT", fangSet.size());
						sdorm.put("CHUANGCOUNT", chuangCount);
						sdorm.put("CHUANGCOUNT_RUZHU", chuangCount_ruzhu);

						sdorm.put("NANCOUNT", nanCount);
						sdorm.put("NVCOUNT", nvCount);
						sdorm.put("NANCOUNT_RUZHU", nanCount_ruzhu);
						sdorm.put("NVCOUNT_RUZHU", nvCount_ruzhu);

						// 男的hover框
						StringBuffer nanHoverStrSB = new StringBuffer();
						if (!nanDormTypeMap.isEmpty()) {
							for (String key : nanDormTypeMap.keySet()) {
								int nanDormTypeCount = nanDormTypeMap.get(key)
										.intValue();
								// 男-四人间房间数
								int nanTypeFangCount = 0;
								if (Tools.notEmpty(nanDormTypeFangMap.get(key))) {
									String nanTypeFangValue = nanDormTypeFangMap
											.get(key).toString();
									nanTypeFangCount = nanTypeFangValue
											.split(",").length;
								}

								int nanTypeFangRuzhuCount = nanDormTypeRuzhuMap
										.get(key) != null ? nanDormTypeRuzhuMap
										.get(key).intValue() : 0;
								nanHoverStrSB.append(key + nanTypeFangCount
										+ "间-" + nanTypeFangRuzhuCount + "/"
										+ nanDormTypeCount + "<br/>");
							}
						}
						sdorm.put("NANHOVERSTR", nanHoverStrSB.toString());

						// 女的hover框
						StringBuffer nvHoverStrSB = new StringBuffer();
						if (!nvDormTypeMap.isEmpty()) {
							for (String key : nvDormTypeMap.keySet()) {
								int nvDormTypeCount = nvDormTypeMap.get(key)
										.intValue();
								// 男-四人间房间数
								int nvTypeFangCount = 0;
								if (Tools.notEmpty(nvDormTypeFangMap.get(key))) {
									String nvTypeFangValue = nvDormTypeFangMap
											.get(key).toString();
									nvTypeFangCount = nvTypeFangValue
											.split(",").length;
								}

								int nvTypeFangRuzhuCount = nvDormTypeRuzhuMap
										.get(key) != null ? nvDormTypeRuzhuMap
										.get(key).intValue() : 0;
								nvHoverStrSB.append(key + nvTypeFangCount
										+ "间-" + nvTypeFangRuzhuCount + "/"
										+ nvDormTypeCount + "<br/>");
							}
						}
						sdorm.put("NVHOVERSTR", nvHoverStrSB.toString());

						studentDormListResult.add(sdorm);
					}

				}

			}
		}

		return studentDormListResult;
	}

	private int getLouCount(PageData pd, String queryStatus) throws Exception {
		int count = 0;
		List<PageData> louList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getLouList", pd);
		node: for (PageData lou : louList) {
			List<PageData> cengList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getCengList", lou);
			for (PageData ceng : cengList) {
				List<PageData> fangList = (List<PageData>) dao.findForList(
						"StudentDormMapper.getFangList", ceng);
				for (PageData fang : fangList) {
					String PKID = fang.getString("PKID");
					fang.putAll(pd);
					fang.put("PKID", PKID);
					fang.put("SD_LEVEL", "5");
					if (Tools.notEmpty(queryStatus)) {
						fang.put("STATUS", queryStatus);
					} else {
						fang.put("STATUS", "");
					}
					PageData chuang = (PageData) dao.findForObject(
							"StudentDormMapper.getChuangList", fang);
					if (!"0".equals(chuang.getString("COUNTT"))) {
						count++;
						continue node;
					}
				}
			}
		}
		return count;
	}

	private int getCengCount(PageData pd, String queryStatus) throws Exception {
		int count = 0;
		List<PageData> louList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getLouList", pd);
		for (PageData lou : louList) {
			List<PageData> cengList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getCengList", lou);
			node: for (PageData ceng : cengList) {
				List<PageData> fangList = (List<PageData>) dao.findForList(
						"StudentDormMapper.getFangList", ceng);
				for (PageData fang : fangList) {
					String PKID = fang.getString("PKID");
					fang.putAll(pd);
					fang.put("PKID", PKID);
					fang.put("SD_LEVEL", "5");
					if (Tools.notEmpty(queryStatus)) {
						fang.put("STATUS", queryStatus);
					} else {
						fang.put("STATUS", "");
					}
					PageData chuang = (PageData) dao.findForObject(
							"StudentDormMapper.getChuangList", fang);
					if (!"0".equals(chuang.getString("COUNTT"))) {
						count++;
						continue node;
					}
				}
			}
		}
		return count;
	}

	private int getFangCount(PageData pd, String queryStatus) throws Exception {
		int count = 0;
		List<PageData> louList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getLouList", pd);
		for (PageData lou : louList) {
			List<PageData> cengList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getCengList", lou);

			for (PageData ceng : cengList) {
				List<PageData> fangList = (List<PageData>) dao.findForList(
						"StudentDormMapper.getFangList", ceng);
				node: for (PageData fang : fangList) {
					String PKID = fang.getString("PKID");
					fang.putAll(pd);
					fang.put("PKID", PKID);
					fang.put("SD_LEVEL", "5");
					if (Tools.notEmpty(queryStatus)) {
						fang.put("STATUS", queryStatus);
					} else {
						fang.put("STATUS", "");
					}
					PageData chuang = (PageData) dao.findForObject(
							"StudentDormMapper.getChuangList", fang);
					if (!"0".equals(chuang.getString("COUNTT"))) {
						count++;
						continue node;
					}
				}
			}
		}
		return count;
	}

	private int getFangCount2(PageData pd, String queryStatus) throws Exception {
		int count = 0;
		List<PageData> cengList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getCengList", pd);

		for (PageData ceng : cengList) {
			List<PageData> fangList = (List<PageData>) dao.findForList(
					"StudentDormMapper.getFangList", ceng);
			node: for (PageData fang : fangList) {
				String PKID = fang.getString("PKID");
				fang.putAll(pd);
				fang.put("PKID", PKID);
				fang.put("SD_LEVEL", "5");
				if (Tools.notEmpty(queryStatus)) {
					fang.put("STATUS", queryStatus);
				} else {
					fang.put("STATUS", "");
				}
				PageData chuang = (PageData) dao.findForObject(
						"StudentDormMapper.getChuangList", fang);
				if (!"0".equals(chuang.getString("COUNTT"))) {
					count++;
					continue node;
				}
			}
		}
		return count;
	}

	@Override
	public PageData getStudentDormChuangCountByParentPkid(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormChuangCountByParentPkid", pd);
	}

	@Override
	public List<VO> getStudentDormPlanTreeForAllot(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		// 查询宿舍树
		// 查询该用户拥有的宿舍信息
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanTreeForAllot", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			if (pda != null) {
				String[] ss = pda.getString("TREE_NODES").split(",");
				String[] nodes = pda.getString("DORM_ID").split(",");
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);

			}
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		if (list_nodes.size() > 0) {
			return (List<VO>) dao.findForList(
					"StudentDormMapper.getStuDormTree", list_nodes);
		} else {
			return null;
		}

	}

	@Override
	public List<VO> gettiaosuStudentDormTree(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		// 查询宿舍树
		// 查询该用户拥有的宿舍信息
		List<PageData> newlist=new ArrayList();
		List<PageData> list = (List<PageData>) dao.findForList("StudentDormMapper.gettiaosuStudentDorm", pd);
		if(list.size()>0){
			String[] strOne= pd.getString("HZXX").split(",");
		   // String[] strTwo= {"a","b","d","g"};
			for (int i = 0; i < list.size(); i++) {
				String[] strTwo= list.get(i).getString("PARTSCHOOL_ID").split(",");
				for (int k = 0; k < strOne.length; k++) {
				            if (ArrayUtils.contains(strTwo, strOne[k])) {
				            	newlist.add(list.get(i));
				             }
							}
				          }
			}
		 
		
		
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : newlist) {//
			if (pda != null) {
				String[] ss = pda.getString("TREE_NODES").split(",");
				String[] nodes = pda.getString("DORM_ID").split(",");
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);

			}
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		if (list_nodes.size() > 0) {
			return (List<VO>) dao.findForList(
					"StudentDormMapper.getStuDormTree", list_nodes);
		} else {
			return null;
		}

	}

	@Override
	public List<VO> getStudentDormPlanTreeForRecovery(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		// 查询该用户拥有的宿舍信息
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanTreeForRecovery", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();

		for (PageData pda : list) {//
			if (pda != null) {
				String[] ss = pda.getString("TREE_NODES").split(",");
				String[] nodes = pda.getString("DORM_ID").split(",");
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
				arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);
			}
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		if (list_nodes.size() > 0) {
			return (List<VO>) dao.findForList(
					"StudentDormMapper.getStuDormTree", list_nodes);
		} else {
			return null;
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VO> getStudentDormPlanTreeForAllotBySex(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		// 查询宿舍树: 查询给该用户分配的宿舍
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanTreeForAllotBySex", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			String[] ss = pda.getString("TREE_NODES").split(",");
			String[] nodes = pda.getString("DORM_ID").split(",");
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		List<VO> dormList = null;
		if (list_nodes.size() > 0) {
			if (pd.getString("ZT").equals("Y")) {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree_summery", list_nodes);

			} else {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree", list_nodes);
			}

		} else {
			dormList = null;
		}
		return dormList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<VO> getStudentDorminfotree(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		// 查询宿舍树: 查询给该用户分配的宿舍
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDorminfotree", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			String[] ss = pda.getString("TREE_NODES").split(",");
			String[] nodes = pda.getString("DORM_ID").split(",");
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		List<VO> dormList = null;
		if (list_nodes.size() > 0) {
			if (pd.getString("ZT").equals("Y")) {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree_summery", list_nodes);

			} else {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree", list_nodes);
			}

		} else {
			dormList = null;
		}
		return dormList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public List<VO> getStudentDorminfotreestu(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		// 查询宿舍树: 查询给该用户分配的宿舍
		List<PageData> list = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDorminfotreestu", pd);
		String[] arrayNodes = {};
		List<String> list_nodes = new ArrayList<String>();
		for (PageData pda : list) {//
			String[] ss = pda.getString("TREE_NODES").split(",");
			String[] nodes = pda.getString("DORM_ID").split(",");
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, ss);
			arrayNodes = (String[]) ArrayUtils.addAll(arrayNodes, nodes);
		}
		for (String s : arrayNodes) {
			if (!list_nodes.contains(s)) {
				list_nodes.add(s);
			}
		}
		List<VO> dormList = null;
		if (list_nodes.size() > 0) {
			if (pd.getString("ZT").equals("Y")) {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree_summery", list_nodes);

			} else {
				dormList = (List<VO>) dao.findForList(
						"StudentDormMapper.getStuDormTree", list_nodes);
			}

		} else {
			dormList = null;
		}
		return dormList;
	}
	
	/**
	 * 分配宿舍
	 */
	@Override
	public void insertStudentDormDepartment(PageData pd,
			StuInfoManager stuInfoService) throws Exception {
		// TODO Auto-generated method stub
		// 前台勾选的一个班型PKID，前台是单选
		String BANXING = pd.getString("BANXING");
		String WENHUAKEXUEXIAO = pd.getString("WENHUAKEXUEXIAO");

		// 前台勾选的所有床PKID
		String STUDENT_DORM_PKIDS = pd.getString("STUDENT_DORM_PKIDS");
		List<String> STUDENT_DORM_PKIDList = new ArrayList<>();
		STUDENT_DORM_PKIDList = Arrays.asList(STUDENT_DORM_PKIDS.split(","));
		pd.put("STUDENT_DORM_PKIDList", STUDENT_DORM_PKIDList);

		// 获得前端勾选宿舍对应的床
		List<PageData> chuangList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getDormDepartmentsList", pd);

		if (chuangList != null && chuangList.size() > 0) {

			for (int i = 0; i < chuangList.size(); i++) {

				// 更新床和班型学校绑定关系

				PageData seqPd = (PageData) dao.findForObject(
						"SeqMapper.getNextSeqDormDepartmentBySeqName",
						SeqManager.SEQ_DORM_DEPARTMENT);
				pd.put("PKID", String.valueOf(seqPd.get("RS")));
				pd.put("DORM_ID", chuangList.get(i).getString("PKID"));
				pd.put("CLASSTYPE_ID", BANXING);
				pd.put("PARTSCHOOL_ID", WENHUAKEXUEXIAO);
				dao.save("StudentDormMapper.insertDormRelation", pd);

				// 更新床为未分配
				pd.put("PKID", pd.getString("DORM_ID"));
				pd.put("IS_ALLOT", 1);
				dao.update("StudentDormMapper.updateStudentDormIsallot", pd);

			}

		} else {
			throw new Exception("选择的宿舍信息不存在！");
		}

	}

	/**
	 * 回收宿舍
	 */
	@Override
	public void updateStudentDormDepartment(PageData pd,
			StuInfoManager stuInfoService) throws Exception {
		// TODO Auto-generated method stub
		// 前台勾选的所有床PKID
		String STUDENT_DORM_PKIDS = pd.getString("STUDENT_DORM_PKIDS");
		List<String> STUDENT_DORM_PKIDList = new ArrayList<>();
		STUDENT_DORM_PKIDList = Arrays.asList(STUDENT_DORM_PKIDS.split(","));
		pd.put("STUDENT_DORM_PKIDList", STUDENT_DORM_PKIDList);
		// 获得前端勾选宿舍对应的床(DEPARTMENT_ID - DORM_ID)
		List<PageData> chuangList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getDormDepartmentsList", pd);

		if (chuangList != null && chuangList.size() > 0) {
			for (int i = 0; i < chuangList.size(); i++) {
				// 删除床和班型学校绑定关系
				pd.put("DORM_ID", chuangList.get(i).getString("PKID"));
				dao.delete("StudentDormMapper.deleteDormRelation", pd);
				// 更新床为未分配
				pd.put("PKID", pd.getString("DORM_ID"));
				pd.put("IS_ALLOT", 0);
				dao.update("StudentDormMapper.updateStudentDormIsallot", pd);
			}

		}

	}

	@Override
	public PageData getStudentDormTop(PageData pd) throws Exception {
		return (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormTop", pd);
	}

	@Override
	public List<PageData> getStudentDormByPkidList(PageData pd)
			throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormByPkidList", pd);
	}

	@Override
	public PageData getStudentDormChildCount(PageData pd) throws Exception {
		return (PageData) dao.findForObject(
				"StudentDormMapper.getStudentDormChildCount", pd);
	}

	@Override
	public List<PageData> getdepList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList("StudentDormMapper.getdepList",
				pd);
	}

	@Override
	public List<PageData> getdateList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getdateList", pd);
	}

	@Override
	public List<PageData> getStudentDormTypeListUsed(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormTypeListUsed", pd);
	}

	@Override
	public VO getCollege(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (VO) dao.findForObject("StudentDormMapper.getCollege", pd);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PageData> getStudentDormPlanTablelistPage(Page page)
			throws Exception {
		PageData pd = page.getPd();
		List<PageData> studentDormListResult = new ArrayList<>();
		/*
		 * 查询被点击节点下拥有权限的所有的床
		 */
		String status = pd.getString("STATUS");
		if (Tools.notEmpty(status) && "0".equals(status)) {
			pd.put("STATUS", "");
		}
		pd.put("pkid2", pd.getString("pkid"));
		List<PageData> studentDormList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormPlanChuangList", pd);

		// 获得所有的房间
		List<PageData> fangList = (List<PageData>) dao.findForList(
				"StudentDormMapper.getStudentDormTableList", pd);

		// 设置总记录条数
		page.setTotalResult(fangList.size());

		if (fangList != null && fangList.size() > 0) {
			for (PageData fangPd : fangList) {
				// 拼接房间名称
				StringBuffer ROOM_NAMESB = new StringBuffer();
				// 房间所属部门
				StringBuffer DEPARTMENT_NAMESB = new StringBuffer();

				String ROOM_NAME = fangPd.getString("ROOM_NAME");
				if (Tools.notEmpty(ROOM_NAME)) {
					// 西校区-号楼－是是是-1层-101
					String[] ROOM_NAMEArray = ROOM_NAME.split("-");
					ROOM_NAMESB.append(ROOM_NAMEArray[1]);
					ROOM_NAMESB.append("-");
					try {
						ROOM_NAMESB.append(ROOM_NAMEArray[3]);
					} catch (Exception e) {
						ROOM_NAMESB.append("");
					}
					ROOM_NAMESB.append("-");
					ROOM_NAMESB.append(fangPd.getString("SD_SEX_NAME"));
					ROOM_NAMESB.append("-");
				}

				int chuangCount = 0;// 床总数
				int chuangCount_ruzhu = 0;// 已入住床总数

				int flag = 0;
				for (PageData chuangPd : studentDormList) {
					if (fangPd.getString("PKID").equals(
							chuangPd.getString("PARENT_PKID"))) {

						if ("1".equals(chuangPd.getString("STATUS"))) {// 已入住
							chuangCount_ruzhu++;
						}

						flag++;
						chuangCount++;
						if (!ROOM_NAMESB.toString().contains(
								chuangPd.getString("DEPARTMENT_NAME") + ",")) {
							if (Tools.notEmpty(chuangPd
									.getString("DEPARTMENT_NAME"))) {
								ROOM_NAMESB.append(chuangPd
										.getString("DEPARTMENT_NAME"));
								ROOM_NAMESB.append(",");
							} else {
								ROOM_NAMESB.append("");
							}

						}

					}
				}
				if (chuangCount == 0) {
					// 总记录-1
					page.setTotalResult(page.getTotalResult() - 1);
					continue;
				}
				if (chuangCount_ruzhu == 0) {
					fangPd.put("FANGSTATUS", "空");
				} else if (chuangCount_ruzhu == chuangCount) {
					fangPd.put("FANGSTATUS", "满");
					if (Tools.notEmpty(status) && "0".equals(status)) {
						// 总记录-1
						page.setTotalResult(page.getTotalResult() - 1);
						continue;
					}
				} else {
					fangPd.put("FANGSTATUS", "未满");
				}
				fangPd.put("CHUANGCOUNT", chuangCount);
				fangPd.put("CHUANGCOUNT_RUZHU", chuangCount_ruzhu);

				String ROOM_NAMES = ROOM_NAMESB.toString();
				fangPd.put(
						"ROOM_NAMES",
						Tools.notEmpty(ROOM_NAMES) ? ROOM_NAMES.substring(0,
								ROOM_NAMES.length() - 1) : "");
				fangPd.put("ROOM_NAME", ROOM_NAME);

				studentDormListResult.add(fangPd);
			}
		}

		if (Tools.notEmpty(pd.getString("listPage"))
				&& "Y".equals(pd.getString("listPage"))) {
			/*
			 * 防止SQL查询速度慢，程序计算分页
			 */
			if (studentDormListResult.size() > 0) {
				if (page.getCurrentPage() * page.getShowCount() > studentDormListResult
						.size()) {
					studentDormListResult = studentDormListResult.subList(
							(page.getCurrentPage() - 1) * page.getShowCount(),
							studentDormListResult.size());
				} else {
					studentDormListResult = studentDormListResult.subList(
							(page.getCurrentPage() - 1) * page.getShowCount(),
							page.getCurrentPage() * page.getShowCount());
				}
			} else {
				page.setTotalResult(0);
			}
		}

		return studentDormListResult;
	}

	@Override
	public List<PageData> getApartmentStatisticslist(PageData pd)
			throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getApartmentStatisticslist", pd);
	}

	@Override
	public List<PageData> getDormList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getDormList", pd);
	}

	@Override
	public List<PageData> getDormtree(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getDormtree", pd);
	}

	@Override
	public List<PageData> getBanxingList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getBanxingList", pd);
	}

	@Override
	public List<PageData> getWenhuakexuexiaoList(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getWenhuakexuexiaoList", pd);
	}

	@Override
	public List<PageData> getDormrztjlistPage(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.getDormrztjlistPage", page);
	}

	@Override
	public List<PageData> printDormrztj(PageData pd) throws Exception {
		return (List<PageData>) dao.findForList(
				"StudentDormMapper.printDormrztj", pd);
	}

}
