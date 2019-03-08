package com.fh.service.system.edituser.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.fh.dao.DaoSupport;
import com.fh.service.system.edituser.EditUserManager;
import com.fh.util.PageData;

@Service("editUserService")
public class EditUserService implements EditUserManager {

	@Resource(name = "daoSupport")
	private DaoSupport dao;
	@Override
	public void updatePwd(PageData pd) throws Exception {
		dao.update("EditUserMapper.updatePwd", pd);
	}

	
}
