package com.fh.service.system.seq;


public interface SeqManager {
	
	/**
	 * 总公司编码
	 */
	public static final String SEQ_ZONGGONGSI = "SEQ_ZONGGONGSI";
	
	/**
	 * 分公司编码
	 */
	public static final String SEQ_FENGONGSI = "SEQ_FENGONGSI";
	
	/**
	 * 项目编码
	 */
	public static final String SEQ_XIANGMU = "SEQ_XIANGMU";
	
	/**
	 * 院系序列
	 */
	public static final String SEQ_SYS_DEPARTMENT = "SEQ_SYS_DEPARTMENT";
	/**
	 * 宿舍序列
	 */
	public static final String SEQ_STUDENT_DORM = "SEQ_STUDENT_DORM";
	/**
	 * 宿舍序列
	 */
	public static final String SEQ_DORM_DEPARTMENT = "SEQ_DORM_DEPARTMENT";
	
	/**新增
	 * @param pd
	 * @throws Exception
	 */
	public String getNextSeqBySeqName(String seqName)throws Exception;
	public String getNextSeqBySeqName_ID(String seqName,String UUID)throws Exception;//总公司或分公司ID
	public String getNextSeqBySeqName_BIANMA(String seqName,String BIANMA)throws Exception;//总公司或分公司编码
	
}
