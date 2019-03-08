package com.fh.service.departmentMei.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.entity.Page;
import com.fh.service.departmentMei.DepartmentMeiManager;
import com.fh.util.PageData;

@Service("DepartmentMeiService")
public class DepartmentMeiService implements DepartmentMeiManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;

	@Override
	public void save(PageData pd) throws Exception {

		dao.save("DepartmentMeiMapper.save", pd);
	}

	@Override
	public Map<String, Object> delete(PageData pd) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		PageData pds = (PageData) dao.findForObject(
				"DepartmentMeiMapper.delete_yanzheng", pd);

		if ("0".equals(pds.getString("COUN"))) {
			dao.delete("DepartmentMeiMapper.del", pd);
			map.put("result", "SUCCESS");
		} else {
			map.put("result", "CHONGFU");

		}

		return map;
	}

	@Override
	public PageData depListById(PageData pd) throws Exception {

		return (PageData) dao.findForObject("DepartmentMeiMapper.depListById",
				pd);
	}

	@Override
	public void update(PageData pd) throws Exception {
		dao.update("DepartmentMeiMapper.update", pd);
	}

	@Override
	public List<PageData> depList(Page page) throws Exception {
		return (List<PageData>) dao.findForList(
				"DepartmentMeiMapper.depMeilistPage", page);
	}

	@Override
	public void update_isqy(PageData pd) throws Exception {
		dao.update("DepartmentMeiMapper.update_isqy", pd);
	}

	@Override
	public boolean getMeiDepByName(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData pds = (PageData) dao.findForObject(
				"DepartmentMeiMapper.getMeiDepByName", pd);

		int count = Integer.parseInt(pds.getString("COUNT"));
		if (count == 0) {
			return true;
		}
		return false;
	}

	@Override
	public String getname(PageData pd) throws Exception {
		// TODO Auto-generated method stub
		PageData pds = (PageData) dao.findForObject(
				"DepartmentMeiMapper.getName", pd);
		return pds.getString("NAME");
	}
}
