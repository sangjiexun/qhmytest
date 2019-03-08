package com.fh.service.system.seq.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fh.dao.DaoSupport;
import com.fh.service.system.seq.SeqManager;
import com.fh.util.PageData;

@Service("seqService")
public class SeqService implements SeqManager{
	
	@Resource(name = "daoSupport")
	private DaoSupport dao;
	PageData pd = null;
	Date d=new Date();//获取时间
	SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
	
	@Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
	public String getNextSeqBySeqName(String seqName) throws Exception {
		if (SeqManager.SEQ_ZONGGONGSI.equals(seqName)) {
			 pd = (PageData)dao.findForObject("SeqMapper.getNextSeqZGSBySeqName", seqName);
		}else if (SeqManager.SEQ_SYS_DEPARTMENT.equals(seqName)) {
			pd = (PageData)dao.findForObject("SeqMapper.getNextSeqDepartmentBySeqName", seqName);
			return String.valueOf(pd.get("RS"));
		}else if (SeqManager.SEQ_STUDENT_DORM.equals(seqName)) {
			pd = (PageData)dao.findForObject("SeqMapper.getNextSeqStudentDormBySeqName", seqName);
			return "D"+String.valueOf(pd.get("RS"));
		}
		return String.format("E%05d", Integer.parseInt(String.valueOf(pd.get("RS"))));
	}
	@Override
	public String getNextSeqBySeqName_ID(String seqName, String UUID) throws Exception {
		if (SeqManager.SEQ_FENGONGSI.equals(seqName)) {
			 pd = (PageData)dao.findForObject("SeqMapper.getNextSeqFGSBySeqName", UUID);
			 return String.format(pd.getString("BIANMA").substring(0, 6)+sdf.format(d)+"E%04d", Integer.parseInt(String.valueOf(pd.get("RS"))));
		}else if (SeqManager.SEQ_XIANGMU.equals(seqName)){
			 pd = (PageData)dao.findForObject("SeqMapper.getNextSeqXMBySeqName", UUID);
			 return String.format("P"+sdf.format(d)+"%04d"+pd.getString("BIANMA").substring(0, 6), Integer.parseInt(String.valueOf(pd.get("RS"))));
		}
		return String.format("E%05d", Integer.parseInt(String.valueOf(pd.get("RS"))));
	}
	@Override
	public String getNextSeqBySeqName_BIANMA(String seqName, String BIANMA) throws Exception {
		if (SeqManager.SEQ_FENGONGSI.equals(seqName)) {
			 pd = (PageData)dao.findForObject("SeqMapper.getNextSeqFGSBySeqNameBM", BIANMA);
			 return String.format(BIANMA.substring(0, 6)+sdf.format(d)+"E%04d", Integer.parseInt(String.valueOf(pd.get("RS"))));
		}else if (SeqManager.SEQ_XIANGMU.equals(seqName)) {
			 pd = (PageData)dao.findForObject("SeqMapper.getNextSeqXMBySeqNameBM", BIANMA);
		}
		return String.format("P"+sdf.format(d)+"%04d"+BIANMA.substring(0, 6), Integer.parseInt(String.valueOf(pd.get("RS"))));
	}
	public static void main(String[] args) {
		Date d=new Date();//获取时间
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
		System.out.println(String.format("E%012d"+sdf.format(d).substring(0, 6), 10));
	}
}
