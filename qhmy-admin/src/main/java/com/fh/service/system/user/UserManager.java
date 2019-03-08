package com.fh.service.system.user;

import java.util.List;

import com.fh.entity.Page;
import com.fh.entity.system.Department;
import com.fh.entity.system.User;
import com.fh.util.PageData;


/** 用户接口类
 * @author zhoudibo
 * 修改时间：2015.11.2
 */
public interface UserManager {
	
	/**登录判断
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getUserByNameAndPwd(PageData pd)throws Exception;
	
	/**更新登录时间
	 * @param pd
	 * @throws Exception
	 */
	public void updateLastLogin(PageData pd)throws Exception;
	
	/**通过用户ID获取用户信息和角色信息
	 * @param USER_ID
	 * @return
	 * @throws Exception
	 */
	public User getUserAndRoleById(String USER_ID) throws Exception;
	
	/**通过USERNAEME获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUsername(PageData pd)throws Exception;
	
	/**列出某角色下的所有用户
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUserByRoldId(PageData pd) throws Exception;
	
	/**保存用户IP
	 * @param pd
	 * @throws Exception
	 */
	public void saveIP(PageData pd)throws Exception;
	
	/**用户列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listUsers(Page page)throws Exception;
	
	/**通过邮箱获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUE(PageData pd)throws Exception;
	
	/**通过编号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByUN(PageData pd)throws Exception;
	
	
	/**通过身份证号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findByUSER_IDCARD(PageData pd)throws Exception;
	
	/**通过劳务人员身份证号获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findByLW_IDCARD(PageData pd)throws Exception;
	
	
	/**通过id获取数据
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findById(PageData pd)throws Exception;
	
	/**修改用户
	 * @param pd
	 * @throws Exception
	 */
	public void editU(PageData pd)throws Exception;
	
	/**保存用户
	 * @param pd
	 * @throws Exception
	 */
	public void saveU(PageData pd)throws Exception;
	
	/**删除用户
	 * @param pd
	 * @throws Exception
	 */
	public void deleteU(PageData pd)throws Exception;
	
	/**批量删除用户
	 * @param USER_IDS
	 * @throws Exception
	 */
	public void deleteAllU(String[] USER_IDS)throws Exception;
	
	/**用户列表(全部)
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public List<PageData> listAllUser(PageData pd)throws Exception;
	
	/**获取总数
	 * @param pd
	 * @throws Exception
	 */
	public PageData getUserCount(String value)throws Exception;
	
	
	public List<User> findAll1()throws Exception;
	
	
	/**劳务列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> findAllLabour1(PageData pd)throws Exception;
	
	public List<PageData> findAllLabour(Page page)throws Exception;
	
	/**出勤率列表
	 * @param page
	 * @return
	 * @throws Exception
	 */
	
	public List<PageData> findAttendance(PageData pd)throws Exception;

	/**
	 * 加载组织和项目节点
	 * @param pd
	 * @return
	 */
	public List<PageData> queryUserOrgAndProject(PageData pd) throws Exception;

	public List<PageData> queryUserOrgAndProjectGly(PageData pd) throws Exception;
	
	public List<PageData> getUser(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:分页查询用户列表</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午3:37:32
	 * @param page
	 * @return
	 * @throws Exception
	 */
	public List<PageData> userlistPage(Page page) throws Exception;

	/**
	 * 
	 * <p>描述:根据组织ID查询组织对象</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午4:43:31
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData getDepartmentSchool(PageData pd)throws Exception;

	/**
	 * 
	 * <p>描述:更新用户</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午5:38:23
	 * @param pd
	 * @throws Exception
	 */
	public void updateU(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:根据user_id查询用户</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午5:50:32
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData findUserById(PageData pd) throws Exception;

	/**
	 * 
	 * <p>描述:更新 用户 状态</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午7:25:49
	 * @param pageData
	 */
	public void updateUserStatus(PageData pageData) throws Exception;

	/**
	 * <p>描述:用户 更新 密码</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午7:38:34
	 * @param pageData
	 * @throws Exception
	 */
	public void updatePwd(PageData pageData) throws Exception;

	/**
	 * 
	 * <p>描述:更新 用户 角色</p>
	 * @author Administrator 鲁震
	 * @date 2017年8月13日 下午8:42:28
	 * @param pageData
	 * @throws Exception
	 */
	public void saveUserRole(PageData pageData) throws Exception;
	
	void saveNewPwd(PageData pd) throws Exception;
	/**
	 * 
	 * <p>描述:用户名校验</p>
	 * @author Administrator 王宁
	 * @date 2018年1月8日 下午6:30:27
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public PageData usernameIdentify(PageData pd)throws Exception;
	public void delUser(PageData pageData) throws Exception;
}
