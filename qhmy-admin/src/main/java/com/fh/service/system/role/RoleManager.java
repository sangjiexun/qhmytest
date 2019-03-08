package com.fh.service.system.role;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.MenuButtonVo;
import com.fh.entity.system.Role;
import com.fh.util.PageData;
import com.keman.zhgd.common.tree.VO;

/**	角色接口类
 * @author FHadmin zhoudibo
 * 修改日期：2015.11.6
 */
public interface RoleManager {
	public List<Department> listSubDepartmentByParentId(String parentId) throws Exception;
	public List<Department> listAllDepartment(String parentId) throws Exception;
	/**列出此组下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	
	
	public List<Role> listAllRolesByPId(PageData pd) throws Exception;
	public List<Role> listAllRolesByPId1(PageData pd) throws Exception;
	public List<Role> listAllRolesByPId11(PageData pd) throws Exception;
	
	/**通过id查找
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findObjectById(PageData pd) throws Exception;
	public PageData setAllRights111(PageData pd) throws Exception;
	
	/**添加
	 * @param pd
	 * @throws Exception
	 */
	public void add(PageData pd) throws Exception;
	
	/**保存修改
	 * @param pd
	 * @throws Exception
	 */
	public void edit(PageData pd) throws Exception;
	
	/**删除角色
	 * @param ROLE_ID
	 * @throws Exception
	 */
	public void deleteRoleById(String ROLE_ID) throws Exception;
	
	/**给当前角色附加菜单权限
	 * @param role
	 * @throws Exception
	 */
	public void updateRoleRights(Role role) throws Exception;
	
	/**通过id查找
	 * @param roleId
	 * @return
	 * @throws Exception
	 */
	public Role getRoleById(String ROLE_ID) throws Exception;
	
	/**给全部子角色加菜单权限
	 * @param pd
	 * @throws Exception
	 */
	public void setAllRights(PageData pd) throws Exception;
	
	/**权限(增删改查)
	 * @param msg 区分增删改查
	 * @param pd
	 * @throws Exception
	 */
	public void saveB4Button(String msg,PageData pd) throws Exception;

	
	/**列出此组下级角色
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<Role> listAllRolesBydepid(PageData pd) throws Exception;
	
	
	/**
	 * <p>描述:根据PKID查询角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午1:31:20
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByRoleId(PageData pd)throws Exception;
	
	public List<PageData> findObjectByUserid(PageData pd)throws Exception;
	
	public void addrd(PageData pd) throws Exception;
	public void deleterd(String a) throws Exception;
	public List<PageData> getzu(String a)throws Exception;
	
	/**
	 * <p>描述:角色列表查询 分页查询</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 上午9:34:21
	 * @param page
	 * @return
	 */
	public List<PageData> rolelistPage(Page page) throws Exception;
	
	/**
	 * 
	 * <p>描述:更新角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午2:29:38
	 * @param pd
	 */
	public void update(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:保存角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午2:30:33
	 * @param pd
	 * @throws Exception
	 */
	public void save(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查询角色是否被用户引用</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午4:01:04
	 * @param pkids
	 * @return
	 * @throws Exception
	 */
	public List<PageData> queryUserhasRoleCount(PageData pageData) throws Exception;
	
	/**
	 * 
	 * <p>描述:删除角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午4:28:05
	 * @param pd
	 */
	public void deleteRoleList(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:查看权限</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午5:39:28
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<MenuButtonVo> queryMenuAndButtonRole(PageData pd) throws Exception;
	
	/**
	 * 
	 * <p>描述:保存权限</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月12日 下午10:58:36
	 * @param pd
	 */
	public void saveJurisdiction(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:验证角色名称唯一性</p>
	 * @author Administrator 王宁
	 * @date 2018年1月8日 下午7:11:21
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData rolenameIdentify(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:数据权限</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月13日 上午9:50:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<VO> queryqxRole(PageData pd) throws Exception;
	
	public List<VO> queryyhqxRole(PageData pd) throws Exception;
	
	
	/**
	 * 
	 * <p>描述:数据权限新增</p>
	 * @author Administrator 胡文浩
	 * @date 2018年3月13日 上午9:50:17
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public void savesjqxJur(PageData pd) throws Exception;
	public void saveyhsjqxJur(PageData pd) throws Exception;
	
	
	public List<PageData> getqxlist(PageData pd) throws Exception;
	public List<PageData> getyhqxlist(PageData pd) throws Exception;
	
	
	public void updatesjqx(PageData pd) throws Exception;
	public void updateyhsjqx(PageData pd) throws Exception;
}
