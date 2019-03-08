package com.fh.entity.system;

import com.keman.zhgd.common.tree.VO;

/**
 * 
 * <p>标题:MenuButtonVo</p>
 * <p>描述:分配权限使用的Vo对象</p>
 * <p>组织:河北科曼信息技术有限公司</p>
 * @author Administrator 鲁震
 * @date 2017年8月12日 下午5:56:36
 */
public class MenuButtonVo extends VO{
	
	
	private String type;
	
	private String role_id;
	
	private String rights;
	
	private String checked;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRole_id() {
		return role_id;
	}

	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}

	public String getRights() {
		return rights;
	}

	public void setRights(String rights) {
		this.rights = rights;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	
	
	
}
