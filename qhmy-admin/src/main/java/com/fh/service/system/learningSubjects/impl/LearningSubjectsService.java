package com.fh.service.system.learningSubjects.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.system.learningSubjects.LearningSubjectsManager;
import com.fh.util.PageData;

@Service("LearningSubjectsService")
public class LearningSubjectsService implements LearningSubjectsManager {
	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void save(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.save("LearningSubjectsMapper.save", pd);
	}

	@Override
	public Map<String, Object> del(PageData pd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pds = (PageData) dao.findForObject(
				"LearningSubjectsMapper.del_yanzheng", pd);

		if ("0".equals(pds.getString("COUN"))) {
			dao.delete("LearningSubjectsMapper.del", pd);
			map.put("result", "SUCCESS");
		} else {
			map.put("result", "CHONGFU");

		}
		return map;
	}

	@Override
	public PageData getSubById(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		return (PageData) dao.findForObject(
				"LearningSubjectsMapper.getSubById", pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("LearningSubjectsMapper.update", pd);
	}

	@Override
	public List<PageData> sublist(Page page) throws Exception {
		// TODO Auto-generated method stub
		return (List<PageData>) dao.findForList(
				"LearningSubjectsMapper.sublistPage", page);
	}

	@Override
	public void updateForIsqy(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		dao.update("LearningSubjectsMapper.updateForIsqy", pd);
	}

	@Override
	public boolean getSubByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		Integer count = (Integer) dao.findForObject(
				"LearningSubjectsMapper.getSubByName", pd);
		if (count == 0) {
			return true;
		}

		return false;
	}

}
